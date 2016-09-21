package com.edianjucai.eshop.presenter.usb;

/**
 * Created by user on 2016-09-21.
 */
public interface ChangePasswordPresenter {
    void requestChangePassword(String email, String oldPwd, String newPwd, String newPwdConfirm);
}
