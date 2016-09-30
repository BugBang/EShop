package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnCompanyListListener;

/**
 * Created by user on 2016-09-29.
 */
public interface CompanyListMode {
    void getCompanyList(int id,OnCompanyListListener onCompanyListListener);
}
