package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnFeedBackListener;

/**
 * Created by user on 2016-10-10.
 */
public interface FeedBackMode {
    void requesetFeedBack(String content, OnFeedBackListener onFeedBackListener);
}
