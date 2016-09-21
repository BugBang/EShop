package com.edianjucai.eshop.model.impl;

import android.app.Dialog;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.event.EventMsg;
import com.edianjucai.eshop.event.EventTag;
import com.edianjucai.eshop.model.entity.LocalUser;
import com.edianjucai.eshop.model.entity.RegisterModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.RegisterMode2;
import com.edianjucai.eshop.presenter.usb.OnRegisterListener2;
import com.edianjucai.eshop.server.InterfaceServer;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 2016-09-20.
 */
public class RegisterModelmpl2 implements RegisterMode2 {

    @Override
    public void requestRegister(String phoneNumber, final String password, String groom, String smsCode, final OnRegisterListener2 onRegisterListener2) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "register");
        mapData.put("user_name", phoneNumber);
        mapData.put("user_pwd", password);
        mapData.put("user_pwd_confirm", password);
        mapData.put("tuiguang","Android");
        mapData.put("referer",groom);
        mapData.put("mobile", phoneNumber);
        mapData.put("mobile_code", smsCode);
        RequestModel model = new RequestModel(mapData);

        SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {
            private Dialog nDialog = null;

            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                try {
                    RegisterModel actModel = JSON.parseObject(content, RegisterModel.class);
                    return actModel;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void onStartInMainThread(Object result) {
                onRegisterListener2.startRegister();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                onRegisterListener2.finishRegister();
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                RegisterModel actModel = (RegisterModel) result;
                if (actModel!=null) {
                    switch (actModel.getUser_login_status()) {
                        case 0:
                            onRegisterListener2.registerFail(actModel.getShow_err());
                            break;
                        case 1:
                            dealRegisteSuccess(actModel,password);
                            onRegisterListener2.registerSuccess();
                            break;
                        default:
                            break;
                    }
                }
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler, true);
    }

    private void dealRegisteSuccess(RegisterModel actModel,String mPassword) {
        LocalUser user = new LocalUser();
        user.setId(actModel.getId());
        user.setUserName(actModel.getUser_name());
        user.setUserPassword(mPassword);
        user.setUserMoneyFormat(actModel.getUser_money_format());
        user.setUserMoney(actModel.getUser_money());
        user.setMobile(actModel.getMobile());
        App.getApplication().setmLocalUser(user);
        EventBus.getDefault().post(new EventMsg(null, EventTag.EVENT_REGISTER_AND_LOGIN_SUCCESS));
    }
}
