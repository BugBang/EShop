package com.edianjucai.eshop.model.impl;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.model.entity.InitModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.ProjectListMode;
import com.edianjucai.eshop.presenter.usb.OnProjectListListener;
import com.edianjucai.eshop.server.InterfaceServer;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016-09-23.
 */
public class ProjectListModelmpl implements ProjectListMode{
    @Override
    public void requestProjectListData(final OnProjectListListener onProjectListListener) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "shop_init");
        RequestModel model = new RequestModel(mapData);

        SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {
            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                try {
                    InitModel projectListModel = JSON.parseObject(content, InitModel.class);
                    return projectListModel;
                } catch (Exception e) {
                    return null;
                }
            }


            @Override
            public void onStartInMainThread(Object result) {
                onProjectListListener.startRequest();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                onProjectListListener.finishRequest();
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                InitModel projectListModel = (InitModel) result;
                if (projectListModel!=null) {
                    if (projectListModel.getResponse_code()==1){
                        onProjectListListener.successRequest();
                        onProjectListListener.setProjectListData(projectListModel);
                    }else {
                        onProjectListListener.failRequest();
                    }
                }
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler, true);
    }
}
