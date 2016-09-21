package com.edianjucai.eshop.presenter.impl;

import com.edianjucai.eshop.model.impl.ChangePasswordModelmpl;
import com.edianjucai.eshop.model.mode.ChangePasswordMode;
import com.edianjucai.eshop.presenter.usb.ChangePasswordPresenter;
import com.edianjucai.eshop.presenter.usb.OnChangePasswordListener;
import com.edianjucai.eshop.ui.view.ChangePasswordView;

/**
 * Created by user on 2016-09-21.
 */
public class ChangePasswordPresenterlmpl implements ChangePasswordPresenter,OnChangePasswordListener {

    private ChangePasswordView mChangePasswordView;
    private ChangePasswordMode mChangePasswordMode;

    public ChangePasswordPresenterlmpl(ChangePasswordView changePasswordView){
        this.mChangePasswordView = changePasswordView;
        mChangePasswordMode = new ChangePasswordModelmpl();
    }

    @Override
    public void requestChangePassword(String email, String oldPwd, String newPwd, String newPwdConfirm) {
        mChangePasswordMode.requestChangePassword(email,oldPwd,newPwd,newPwdConfirm,this);
    }

    @Override
    public void startChangePassword() {
        mChangePasswordView.startChangePassword();
    }

    @Override
    public void finishChangePassword() {
        mChangePasswordView.finishChangePassword();
    }

    @Override
    public void successChangePassword() {
        mChangePasswordView.successChangePassword();
    }

    @Override
    public void failChangePassword(String showErr) {
        mChangePasswordView.failChangePassword(showErr);
    }
}
