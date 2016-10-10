package com.edianjucai.eshop.model.impl;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.model.entity.FeedBackModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.model.mode.FeedBackMode;
import com.edianjucai.eshop.presenter.usb.OnFeedBackListener;
import com.edianjucai.eshop.server.InterfaceServer;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2016-10-10.
 */
public class FeedBackModelImpl implements FeedBackMode {
    @Override
    public void requesetFeedBack(String content, final OnFeedBackListener onFeedBackListener) {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "yjfk_add");
        mapData.put("is_effect", "5");
        mapData.put("content", content);

        RequestModel model = new RequestModel(mapData);
        SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {
            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                FeedBackModel model = JSON.parseObject(content, FeedBackModel.class);
                return model;
            }

            @Override
            public void onStartInMainThread(Object result) {
                onFeedBackListener.startFeedBack();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                onFeedBackListener.finishFeedBack();
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                FeedBackModel feedBackModel = (FeedBackModel) result;
                if (feedBackModel!=null){
                    if (feedBackModel.getStatus()==1){
                        onFeedBackListener.successFeedBack();
                    }else {
                        onFeedBackListener.failFeedBack(feedBackModel.getShow_err());
                    }
                }
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler, true);
    }
}
