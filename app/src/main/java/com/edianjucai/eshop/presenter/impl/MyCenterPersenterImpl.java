package com.edianjucai.eshop.presenter.impl;

import com.edianjucai.eshop.model.entity.LoginModel;
import com.edianjucai.eshop.model.impl.MyCenterModeImpl;
import com.edianjucai.eshop.model.mode.MyCenterMode;
import com.edianjucai.eshop.presenter.usb.LoginPresenter;
import com.edianjucai.eshop.presenter.usb.OnLoginListener;
import com.edianjucai.eshop.ui.view.MyCenterView;

/**
 * Created by user on 2016-09-19.
 */
public class MyCenterPersenterImpl implements LoginPresenter,OnLoginListener {

    private MyCenterView mMyCenterView;
    private MyCenterMode mMyCenterMode;

    public MyCenterPersenterImpl(MyCenterView myCenterView){
        this.mMyCenterView = myCenterView;
        mMyCenterMode = new MyCenterModeImpl();
    }
    @Override
    public void getLoginMsg(String userName, String passWord) {
        mMyCenterMode.login(userName,passWord,this);
    }


    @Override
    public void onStart() {
        mMyCenterView.showLoading();
    }

    @Override
    public void onFinish() {
        mMyCenterView.hideLoading();
    }

    @Override
    public void onSuccess(LoginModel actModel) {
        mMyCenterView.loginSuccess(actModel);
        mMyCenterView.hideLoading();
    }

    @Override
    public void onError() {
        mMyCenterView.onError();
        mMyCenterView.hideLoading();
    }
}
