package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnRegisterListener1;

/**
 * Created by user on 2016-09-20.
 */
public interface RegisterMode1 {
    void requestCode(String phoneNumber, OnRegisterListener1 onRegisterListener1);
}
