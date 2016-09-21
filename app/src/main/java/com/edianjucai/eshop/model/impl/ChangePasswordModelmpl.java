package com.edianjucai.eshop.model.impl;

import android.app.Dialog;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.model.entity.BaseActModel;
import com.edianjucai.eshop.model.entity.LocalUser;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.ChangePasswordMode;
import com.edianjucai.eshop.presenter.usb.OnChangePasswordListener;
import com.edianjucai.eshop.server.InterfaceServer;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016-09-21.
 */
public class ChangePasswordModelmpl implements ChangePasswordMode {
    @Override
    public void requestChangePassword(String email, String oldPwd, final String newPwd, String newPwdConfirm, final OnChangePasswordListener onChangePasswordListener) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "uc_save_pwd");
        mapData.put("email", App.getApplication().getmLocalUser().getUserName());
        mapData.put("pwd", oldPwd);
        mapData.put("user_pwd", newPwd);
        mapData.put("user_pwd_confirm", newPwdConfirm);
        RequestModel model = new RequestModel(mapData);

        SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {
            private Dialog nDialog = null;

            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                try {
                    BaseActModel actModel = JSON.parseObject(content, BaseActModel.class);
                    return actModel;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void onStartInMainThread(Object result) {
                onChangePasswordListener.startChangePassword();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                onChangePasswordListener.finishChangePassword();
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                BaseActModel actModel = (BaseActModel) result;
                if (actModel != null) {
                    switch (actModel.getResponse_code()) {
                        case 0:
                            onChangePasswordListener.failChangePassword(actModel.getShow_err());
                            break;
                        case 1:
                            // TODO:修改密码成功
                            LocalUser user = App.getApplication().getmLocalUser();
                            if (user != null) {
                                user.setUserPassword(newPwd);
                                App.getApplication().setmLocalUser(user);
                            }
                            onChangePasswordListener.successChangePassword();
                            break;

                        default:
                            break;
                    }
                }
            }
        };

        InterfaceServer.getInstance().requestInterface(model, handler, true);
    }
}
