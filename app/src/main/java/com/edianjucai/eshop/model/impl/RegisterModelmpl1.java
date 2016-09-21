package com.edianjucai.eshop.model.impl;

import android.app.Dialog;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.model.entity.BaseActModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.RegisterMode1;
import com.edianjucai.eshop.presenter.usb.OnRegisterListener1;
import com.edianjucai.eshop.server.InterfaceServer;
import com.edianjucai.eshop.util.ModelUtil;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016-09-20.
 */
public class RegisterModelmpl1 implements RegisterMode1 {
    @Override
    public void requestCode(String phoneNumber, final OnRegisterListener1 onRegisterListener1) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "send_register_code");
        mapData.put("mobile", phoneNumber);
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
                onRegisterListener1.startRequestCode();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                onRegisterListener1.finishRequestCode();
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                BaseActModel actModel = (BaseActModel) result;
                if (!ModelUtil.isActModelNull(actModel)) {
                    switch (actModel.getResponse_code()) {
                        case 1:
                            onRegisterListener1.successRequestCode();
                            break;
                        case 0:
                            onRegisterListener1.errorRequestCode();
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
