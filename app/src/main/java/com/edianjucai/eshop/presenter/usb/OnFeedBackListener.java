package com.edianjucai.eshop.presenter.usb;

/**
 * Created by user on 2016-10-10.
 */
public interface OnFeedBackListener {
    void startFeedBack();
    void finishFeedBack();
    void successFeedBack();
    void failFeedBack(String err);
}
