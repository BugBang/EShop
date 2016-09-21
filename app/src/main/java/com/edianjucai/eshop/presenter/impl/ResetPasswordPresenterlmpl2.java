package com.edianjucai.eshop.presenter.impl;

import com.edianjucai.eshop.model.impl.ResetPasswordModelmpl2;
import com.edianjucai.eshop.model.mode.ResetPasswordMode2;
import com.edianjucai.eshop.presenter.usb.OnResetPasswordListener2;
import com.edianjucai.eshop.presenter.usb.ResetPasswordPresenter2;
import com.edianjucai.eshop.ui.view.ResetPasswordView2;

/**
 * Created by user on 2016-09-20.
 */
public class ResetPasswordPresenterlmpl2 implements ResetPasswordPresenter2,OnResetPasswordListener2 {

    private ResetPasswordView2 mResetPasswordView2;
    private ResetPasswordMode2 mResetPasswordMode2;
    public ResetPasswordPresenterlmpl2(ResetPasswordView2 resetPasswordView2){
        this.mResetPasswordView2 = resetPasswordView2;
        mResetPasswordMode2 = new ResetPasswordModelmpl2();
    }

    @Override
    public void startResetPassword() {
        mResetPasswordView2.startResetPassword();
    }

    @Override
    public void finishResetPassword() {
        mResetPasswordView2.finishResetPassword();
    }

    @Override
    public void successResetPassword() {
        mResetPasswordView2.successResetPassword();
    }

    @Override
    public void failResetPassword(String showErr) {
        mResetPasswordView2.failResetPassword(showErr);
    }


    @Override
    public void requestResetPassword(String userPhone, String smsCode, String pwd) {
        mResetPasswordMode2.requestResePassword(userPhone,smsCode,pwd,this);
    }
}
