package com.edianjucai.eshop.presenter.impl;

import com.edianjucai.eshop.model.impl.RegisterModelmpl1;
import com.edianjucai.eshop.model.mode.RegisterMode1;
import com.edianjucai.eshop.presenter.usb.OnRegisterListener1;
import com.edianjucai.eshop.presenter.usb.RegisterPresenter1;
import com.edianjucai.eshop.ui.view.RegisterView1;

/**
 * Created by user on 2016-09-20.
 */
public class RegisterPersenterlmpl1 implements RegisterPresenter1,OnRegisterListener1 {

    private RegisterView1 mRegisterView1;
    private RegisterMode1 mRegisterMode1;
    public RegisterPersenterlmpl1(RegisterView1 registerView1){
        this.mRegisterView1 = registerView1;
        mRegisterMode1 = new RegisterModelmpl1();
    }

    @Override
    public void requestPhoneCode(String phoneNumber) {
        mRegisterMode1.requestCode(phoneNumber,this);
    }

    @Override
    public void startRequestCode() {
        mRegisterView1.startRequestCode();
    }

    @Override
    public void finishRequestCode() {
        mRegisterView1.finishRequestCode();
    }

    @Override
    public void successRequestCode() {
        mRegisterView1.requsetCodeSuccess();
    }

    @Override
    public void errorRequestCode() {
        mRegisterView1.requestCodeFail();
    }
}
