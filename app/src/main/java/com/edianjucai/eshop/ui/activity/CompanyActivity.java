package com.edianjucai.eshop.ui.activity;

import android.content.Context;
import android.os.Bundle;

import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseActivity;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.ui.fragment.CompanyListFragment;

/**
 * Created by user on 2016-09-29.
 */
public class CompanyActivity extends BaseActivity{

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_company;
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    protected int getFragmentContentId() {
        return R.id.content;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        mSendData = 1; //1:带参数启动  2:不带参数
        return CompanyListFragment.newInstance();
    }
}
