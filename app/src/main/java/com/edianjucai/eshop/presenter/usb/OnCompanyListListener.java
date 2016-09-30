package com.edianjucai.eshop.presenter.usb;

import com.edianjucai.eshop.model.entity.CompanyListModel;

/**
 * Created by user on 2016-09-29.
 */
public interface OnCompanyListListener {
    void startRequest();
    void finishRequest();
    void failRequest(String showErr);
    void successRequest(CompanyListModel companyListModel);
}
