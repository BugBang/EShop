package com.edianjucai.eshop.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.event.EventMsg;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by user on 2016-09-12.
 */
public abstract class BaseFragment extends Fragment {

    private boolean isDebug;
    private String APP_NAME;
    protected final String TAG = this.getClass().getSimpleName();
    private View mContextView = null;
    protected BaseActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        isDebug = App.isDebug;
        APP_NAME = App.APP_NAME;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContextView = inflater.inflate(bindLayout(), container, false);
        ButterKnife.bind(this, mContextView);
        doBusiness(getActivity());
        return mContextView;
    }

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);


    /**
     * [日志输出]
     *
     * @param msg
     */
    protected void $Log(String msg) {
        if (isDebug) {
            Log.i(APP_NAME, msg);
        }
    }

    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment);
        }
    }

    protected void addFragment(BaseFragment fragment,String key,String value) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment,key,value);
        }
    }

    protected void addFragment(BaseFragment fragment,Bundle bundle) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment,bundle);
        }
    }


    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }

    public void showLoadingDialog(String msg)
    {
        if (mActivity != null)
        {
            mActivity.showLoadingDialog(msg);
        }
    }

    public void hideLoadingDialog()
    {
        if (mActivity != null)
        {
            mActivity.hideLoadingDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.PostThread)
    public void onMessageEventPostThread(EventMsg messageEvent) {
        Log.e("PostThread", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(EventMsg messageEvent) {
        Log.e("MainThread", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onMessageEventBackgroundThread(EventMsg messageEvent) {
        Log.e("BackgroundThread", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.Async)
    public void onMessageEventAsync(EventMsg messageEvent) {
        Log.e("Async", Thread.currentThread().getName());
    }
}
