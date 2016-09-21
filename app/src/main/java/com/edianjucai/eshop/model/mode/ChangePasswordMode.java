package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnChangePasswordListener;

/**
 * Created by user on 2016-09-21.
 */
public interface ChangePasswordMode {
    void requestChangePassword(String email, String oldPwd, String newPwd, String newPwdConfirm, OnChangePasswordListener onChangePasswordListener);
}
