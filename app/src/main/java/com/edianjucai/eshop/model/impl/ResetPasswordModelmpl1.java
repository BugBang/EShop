package com.edianjucai.eshop.model.impl;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.model.entity.BaseActModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.ResetPasswordMode1;
import com.edianjucai.eshop.presenter.usb.OnResetPasswordListener1;
import com.edianjucai.eshop.server.InterfaceServer;
import com.edianjucai.eshop.util.ModelUtil;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016-09-20.
 */
public class ResetPasswordModelmpl1 implements ResetPasswordMode1 {
    @Override
    public void requestResePasswordCode(String userPhone, final OnResetPasswordListener1 onResetPasswordListener1) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "send_reset_pwd_code");
        mapData.put("mobile", userPhone);
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
                onResetPasswordListener1.startResetPassword();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                onResetPasswordListener1.finishResetPassword();
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                BaseActModel actModel = (BaseActModel) result;
                if (!ModelUtil.isActModelNull(actModel)) {
                    if (actModel.getResponse_code() == 1) {
                        onResetPasswordListener1.successResetPassword();
                    }else {
                        onResetPasswordListener1.failResetPassword(actModel.getShow_err());
                    }
                }
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler, true);
    }
}
