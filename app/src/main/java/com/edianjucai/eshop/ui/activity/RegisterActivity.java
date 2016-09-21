package com.edianjucai.eshop.ui.activity;

import android.content.Context;
import android.os.Bundle;

import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseActivity;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.ui.fragment.RegisterFragment1;

/**
 * Created by user on 2016-09-14.
 */
public class RegisterActivity extends BaseActivity {

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.content;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return RegisterFragment1.newInstance();
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
