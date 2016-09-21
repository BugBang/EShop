package com.edianjucai.eshop.presenter.usb;

/**
 * Created by user on 2016-09-21.
 */
public interface OnChangePasswordListener {
    void startChangePassword();
    void finishChangePassword();
    void successChangePassword();
    void failChangePassword(String showErr);
}
