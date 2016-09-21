package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnResetPasswordListener1;

/**
 * Created by user on 2016-09-20.
 */
public interface ResetPasswordMode1 {
    void requestResePasswordCode(String userPhone, OnResetPasswordListener1 onResetPasswordListener1);
}
