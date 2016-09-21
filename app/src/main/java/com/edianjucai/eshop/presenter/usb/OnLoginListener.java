package com.edianjucai.eshop.presenter.usb;

/**
 * Created by user on 2016-09-19.
 */
public interface OnLoginListener {

    void onStart();

    void onFinish();
    /**
     * 成功时回调
     */
    void onSuccess();
    /**
     * 失败时回调
     */
    void onError();
}
