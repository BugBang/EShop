package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnLoginListener;

/**
 * Created by user on 2016-09-19.
 */
public interface MyCenterMode {
    void login(String userName,String passWord, OnLoginListener listener);
}
