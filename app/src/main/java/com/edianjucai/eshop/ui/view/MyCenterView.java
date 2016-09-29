package com.edianjucai.eshop.ui.view;

import com.edianjucai.eshop.model.entity.LoginModel;

/**
 * Created by user on 2016-09-19.
 */
public interface MyCenterView {
    void showLoading();

    void hideLoading();

    void onError();

    void loginSuccess(LoginModel actModel);
}
