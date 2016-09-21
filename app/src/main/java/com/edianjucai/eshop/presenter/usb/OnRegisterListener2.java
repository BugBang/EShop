package com.edianjucai.eshop.presenter.usb;

/**
 * Created by user on 2016-09-20.
 */
public interface OnRegisterListener2 {
    void startRegister();
    void finishRegister();
    void registerSuccess();
    void registerFail(String show_err);
}
