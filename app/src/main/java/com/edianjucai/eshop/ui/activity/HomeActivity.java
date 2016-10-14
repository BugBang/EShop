package com.edianjucai.eshop.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;

import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseActivity;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.ui.fragment.MoreSettingFragment;
import com.edianjucai.eshop.ui.fragment.MyCentreFragment;
import com.edianjucai.eshop.ui.fragment.ProjectListFragment;

import butterknife.BindView;

/**
 * Created by user on 2016-09-12.
 */
public class HomeActivity extends BaseActivity {

    public static final String NEED_LOGIN = "need_login";
    public static final String WHICH_START = "which_start";
    private boolean mIsNeedLogin;

    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabhost;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void doBusiness(Context mContext) {
        addTab();
        Intent intent = getIntent();
        if (intent!=null){
            mIsNeedLogin = intent.getBooleanExtra(NEED_LOGIN, false);
            if (mIsNeedLogin){
                setCurrentTab(1);
            }
        }
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    private void addTab() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View project_view = layoutInflater.inflate(R.layout.tab_project, null);
        View me_view = layoutInflater.inflate(R.layout.tab_me, null);
        View more_view = layoutInflater.inflate(R.layout.tab_more, null);
        mTabhost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabhost.getTabWidget().setDividerDrawable(null);

        TabHost.TabSpec all = mTabhost.newTabSpec("all");
        all.setIndicator(project_view);
        mTabhost.addTab(all, ProjectListFragment.class, null);

        TabHost.TabSpec me = mTabhost.newTabSpec("me");
        me.setIndicator(me_view);
        mTabhost.addTab(me, MyCentreFragment.class, null);

        TabHost.TabSpec more = mTabhost.newTabSpec("more");
        more.setIndicator(more_view);
        mTabhost.addTab(more, MoreSettingFragment.class, null);

    }

    public void setCurrentTab(int index){
        mTabhost.setCurrentTab(index);
    }

}
