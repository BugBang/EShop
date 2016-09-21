package com.edianjucai.eshop.common;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.event.EventMsg;
import com.edianjucai.eshop.event.EventTag;
import com.edianjucai.eshop.model.entity.LocalUser;
import com.edianjucai.eshop.model.entity.Refresh_UserActModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.server.InterfaceServer;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;


public class CommonInterface {

    public static void refreshLocalUser() {
        final LocalUser user = App.getApplication().getmLocalUser();
        if (user == null) {
            return;
        } else {
            Map<String, Object> mapData = new HashMap<String, Object>();
            mapData.put("act", "refresh_user");
            mapData.put("email", user.getUserName());
            mapData.put("pwd", user.getUserPassword());
            RequestModel model = new RequestModel(mapData);
            SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {

                @Override
                public void onFailureInMainThread(Throwable error, String content, Object result) {

                }

                @Override
                public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                    Refresh_UserActModel model = JSON.parseObject(content, Refresh_UserActModel.class);
                    if (model != null) {
                        if (model.getResponse_code() == 1) {
                            user.setUserMoney(model.getUser_money());
                            user.setUserMoneyFormat(model.getUser_money_format());
                            App.getApplication().setmLocalUser(user);
                            EventBus.getDefault().post(new EventMsg(EventTag.EVENT_REFRESH_USER_SUCCESS));
                        }
                    }
                    return model;
                }

                @Override
                public void onStartInMainThread(Object result) {

                }

                @Override
                public void onFinishInMainThread(Object result) {

                }

                @Override
                public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {

                }

            };
            InterfaceServer.getInstance().requestInterface(model, handler, true);
        }
    }


}
