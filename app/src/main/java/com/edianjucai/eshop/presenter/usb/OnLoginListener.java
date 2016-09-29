package com.edianjucai.eshop.presenter.usb;

import com.edianjucai.eshop.model.entity.LoginModel;

/**
 * Created by user on 2016-09-19.
 */
public interface OnLoginListener {

    void onStart();

    void onFinish();
    /**
     * 成功时回调
     * @param actModel
     */
    void onSuccess(LoginModel actModel);
    /**
     * 失败时回调
     */
    void onError();
}
