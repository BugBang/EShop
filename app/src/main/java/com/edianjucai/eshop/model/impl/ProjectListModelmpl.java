package com.edianjucai.eshop.model.impl;

import android.app.Dialog;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.model.entity.ProjectListModel;
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
        mapData.put("act", "");

        RequestModel model = new RequestModel(mapData);

        SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {
            private Dialog nDialog = null;

            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                try {
                    ProjectListModel projectListModel = JSON.parseObject(content, ProjectListModel.class);
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
                ProjectListModel projectListModel = (ProjectListModel) result;
                if (projectListModel!=null) {
                    //成功
                    onProjectListListener.successRequest();
                    onProjectListListener.setProjectListData(projectListModel);
                    //失败
//                    onProjectListListener.failRequest();


//                    switch (actModel.getUser_login_status()) {
//                        case 0:
//                            onRegisterListener2.registerFail(actModel.getShow_err());
//                            break;
//                        case 1:
//                            dealRegisteSuccess(actModel,password);
//                            onRegisterListener2.registerSuccess();
//                            break;
//                    }
                }
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler, true);
    }
}
