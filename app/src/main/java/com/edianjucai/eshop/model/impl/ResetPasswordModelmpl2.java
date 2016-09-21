package com.edianjucai.eshop.model.impl;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.model.entity.BaseActModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.ResetPasswordMode2;
import com.edianjucai.eshop.presenter.usb.OnResetPasswordListener2;
import com.edianjucai.eshop.server.InterfaceServer;
import com.edianjucai.eshop.util.ModelUtil;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016-09-21.
 */
public class ResetPasswordModelmpl2 implements ResetPasswordMode2 {
    @Override
    public void requestResePassword(String userPhone, String smsCode, String pwd, final OnResetPasswordListener2 onResetPasswordListener2) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "save_reset_pwd");
        mapData.put("mobile", userPhone);
        mapData.put("mobile_code", smsCode);
        mapData.put("user_pwd", pwd);
        mapData.put("user_pwd_confirm", pwd);
        RequestModel model = new RequestModel(mapData);
        SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {
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
                onResetPasswordListener2.startResetPassword();
            }
            @Override
            public void onFinishInMainThread(Object result) {
                onResetPasswordListener2.finishResetPassword();
            }
            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                BaseActModel actModel = (BaseActModel) result;
                if (!ModelUtil.isActModelNull(actModel)) {
                    switch (actModel.getResponse_code()) {
                        case 0:
                            onResetPasswordListener2.failResetPassword(actModel.getShow_err());
                            break;
                        case 1:
                            onResetPasswordListener2.successResetPassword();
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

