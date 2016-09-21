package com.edianjucai.eshop.presenter.impl;

import com.edianjucai.eshop.model.impl.RegisterModelmpl2;
import com.edianjucai.eshop.model.mode.RegisterMode2;
import com.edianjucai.eshop.presenter.usb.OnRegisterListener2;
import com.edianjucai.eshop.presenter.usb.RegisterPresenter2;
import com.edianjucai.eshop.ui.view.RegisterView2;

/**
 * Created by user on 2016-09-20.
 */
public class RegisterPersenterlmpl2 implements RegisterPresenter2,OnRegisterListener2 {

    private RegisterView2 mRegisterView2;
    private RegisterMode2 mRegisterMode2;

    public RegisterPersenterlmpl2(RegisterView2 registerView2){
        this.mRegisterView2 = registerView2;
        mRegisterMode2 = new RegisterModelmpl2();
    }
    @Override
    public void startRegister() {
        mRegisterView2.startRegister();
    }

    @Override
    public void finishRegister() {
        mRegisterView2.finishRegister();
    }

    @Override
    public void registerSuccess() {
        mRegisterView2.registerSuccess();
    }

    @Override
    public void registerFail(String show_err) {
        mRegisterView2.registerFail(show_err);
    }

    @Override
    public void requestRegister(String phoneNumber, String password, String groom, String smsCode) {
        mRegisterMode2.requestRegister(phoneNumber,password,groom,smsCode,this);
    }
}
