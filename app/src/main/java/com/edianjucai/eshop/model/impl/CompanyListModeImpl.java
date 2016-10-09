package com.edianjucai.eshop.model.impl;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.model.entity.CompanyListModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.CompanyListMode;
import com.edianjucai.eshop.presenter.usb.OnCompanyListListener;
import com.edianjucai.eshop.server.InterfaceServer;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016-09-29.
 */
public class CompanyListModeImpl implements CompanyListMode {
    @Override
    public void getCompanyList(String id, final OnCompanyListListener onCompanyListListener) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "commodity_type");
        mapData.put("type", id);
        RequestModel model = new RequestModel(mapData);

        SDAsyncHttpResponseHandler sdAsync = new SDAsyncHttpResponseHandler() {

            @Override
            public void onStartInMainThread(Object result) {
                onCompanyListListener.startRequest();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                onCompanyListListener.startRequest();
            }

            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                try {
                    CompanyListModel model = JSON.parseObject(content, CompanyListModel.class);
                    return model;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                CompanyListModel model = (CompanyListModel) result;
                if (model != null) {
                    onCompanyListListener.successRequest(model);
                }
            }
        };
        InterfaceServer.getInstance().requestInterface(model, sdAsync, true);
    }
}
