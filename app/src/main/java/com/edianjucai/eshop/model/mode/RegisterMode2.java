package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnRegisterListener2;

/**
 * Created by user on 2016-09-20.
 */
public interface RegisterMode2 {
    void requestRegister(String phoneNumber, String password, String groom, String smsCode, OnRegisterListener2 onRegisterListener2);
}
