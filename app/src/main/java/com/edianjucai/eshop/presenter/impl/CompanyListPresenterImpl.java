package com.edianjucai.eshop.presenter.impl;

import com.edianjucai.eshop.model.entity.CompanyListModel;
import com.edianjucai.eshop.model.impl.CompanyListModeImpl;
import com.edianjucai.eshop.model.mode.CompanyListMode;
import com.edianjucai.eshop.presenter.usb.CompanyListPresenter;
import com.edianjucai.eshop.presenter.usb.OnCompanyListListener;
import com.edianjucai.eshop.ui.view.CompanyListView;

/**
 * Created by user on 2016-09-29.
 */
public class CompanyListPresenterImpl implements CompanyListPresenter,OnCompanyListListener {
    private CompanyListView mCompanyListView;
    private CompanyListMode mCompanyListMode;

    public CompanyListPresenterImpl(CompanyListView companyListView){
        this.mCompanyListView = companyListView;
        mCompanyListMode = new CompanyListModeImpl();
    }
    @Override
    public void getCompanyList(int id) {
        mCompanyListMode.getCompanyList(id,this);
    }

    @Override
    public void startRequest() {
        mCompanyListView.startRequest();
    }

    @Override
    public void finishRequest() {
        mCompanyListView.finishRequest();
    }

    @Override
    public void failRequest(String showErr) {
        mCompanyListView.failRequest(showErr);
    }

    @Override
    public void successRequest(CompanyListModel companyListModel) {
        mCompanyListView.successRequest(companyListModel);
    }
}
