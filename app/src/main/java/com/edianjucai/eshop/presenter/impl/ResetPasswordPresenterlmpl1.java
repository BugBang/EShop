package com.edianjucai.eshop.presenter.impl;

import com.edianjucai.eshop.model.impl.ResetPasswordModelmpl1;
import com.edianjucai.eshop.model.mode.ResetPasswordMode1;
import com.edianjucai.eshop.presenter.usb.OnResetPasswordListener1;
import com.edianjucai.eshop.presenter.usb.ResetPasswordPresenter1;
import com.edianjucai.eshop.ui.view.ResetPasswordView1;

/**
 * Created by user on 2016-09-20.
 */
public class ResetPasswordPresenterlmpl1 implements ResetPasswordPresenter1,OnResetPasswordListener1 {

    private ResetPasswordView1 mResetPasswordView1;
    private ResetPasswordMode1 mResetPasswordMode1;
    public ResetPasswordPresenterlmpl1(ResetPasswordView1 resetPasswordView1){
        this.mResetPasswordView1 = resetPasswordView1;
        mResetPasswordMode1 = new ResetPasswordModelmpl1();
    }

    @Override
    public void startResetPassword() {
        mResetPasswordView1.startResetPassword();
    }

    @Override
    public void finishResetPassword() {
        mResetPasswordView1.finishResetPassword();
    }

    @Override
    public void successResetPassword() {
        mResetPasswordView1.successResetPassword();
    }

    @Override
    public void failResetPassword(String showErr) {
        mResetPasswordView1.failResetPassword(showErr);
    }

    @Override
    public void requestResetPasswordCode(String userPhone) {
        mResetPasswordMode1.requestResePasswordCode(userPhone,this);
    }
}
