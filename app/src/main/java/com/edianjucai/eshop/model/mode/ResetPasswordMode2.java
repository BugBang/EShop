package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnResetPasswordListener2;

/**
 * Created by user on 2016-09-20.
 */
public interface ResetPasswordMode2 {
    void requestResePassword(String userPhone,String smsCode,String pwd,OnResetPasswordListener2 onResetPasswordListener2);
}
