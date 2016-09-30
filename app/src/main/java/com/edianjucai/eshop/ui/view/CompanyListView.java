package com.edianjucai.eshop.ui.view;

import com.edianjucai.eshop.model.entity.CompanyListModel;

/**
 * Created by user on 2016-09-29.
 */
public interface CompanyListView {
    void startRequest();
    void finishRequest();
    void failRequest(String showErr);
    void successRequest(CompanyListModel companyListModel);
}
