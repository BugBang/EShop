package com.edianjucai.eshop.model.impl;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.event.EventMsg;
import com.edianjucai.eshop.event.EventTag;
import com.edianjucai.eshop.model.entity.LocalUser;
import com.edianjucai.eshop.model.entity.LoginModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.MyCenterMode;
import com.edianjucai.eshop.presenter.usb.OnLoginListener;
import com.edianjucai.eshop.server.InterfaceServer;
import com.edianjucai.eshop.util.ModelUtil;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 2016-09-19.
 */
public class MyCenterModeImpl implements MyCenterMode {
    @Override
    public void login(String userName, final String passWord, final OnLoginListener listener) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "login");
        mapData.put("email", userName);
        mapData.put("pwd", passWord);
        RequestModel model = new RequestModel(mapData);

        SDAsyncHttpResponseHandler sdAsync = new SDAsyncHttpResponseHandler() {

            @Override
            public void onStartInMainThread(Object result) {
                listener.onStart();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                listener.onFinish();
            }

            @Override
            public void onFailureInMainThread(Throwable e, String responseBody, Object result) {
                listener.onError();
            }

            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                try {
                    LoginModel actModel = JSON.parseObject(content, LoginModel.class);
                    return actModel;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                LoginModel actModel = (LoginModel) result;
                if (!ModelUtil.isActModelNull(actModel)) {
                    switch (actModel.getResponse_code()) {
                        case 0:
                            break;
                        case 1:
                            listener.onSuccess(actModel);
                            dealLoginSuccess(actModel, passWord);
                            break;

                        default:
                            break;
                    }
                }
            }
        };
        InterfaceServer.getInstance().requestInterface(model, sdAsync, true);
    }



    private void dealLoginSuccess(LoginModel actModel, String password) {
        LocalUser user = new LocalUser();
        user.setId(actModel.getId());
        user.setUserName(actModel.getUser_name());
        user.setUserPassword(password);
        user.setUserMoneyFormat(actModel.getUser_money_format());
        user.setUserMoney(actModel.getUser_money());
        user.setMobile(actModel.getMobile());
        App.getApplication().setmLocalUser(user);
        EventBus.getDefault().post(new EventMsg(null,EventTag.EVENT_LOGIN_SUCCESS));
    }
}
