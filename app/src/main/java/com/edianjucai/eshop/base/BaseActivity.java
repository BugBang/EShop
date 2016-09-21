package com.edianjucai.eshop.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.event.EventMsg;
import com.edianjucai.eshop.util.DialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


/**
 * Created by user on 2016-09-12.
 */
public abstract class BaseActivity extends FragmentActivity {

    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 是否输出日志信息
     **/
    private boolean isDebug;
    private Unbinder bind;
    private String APP_NAME;
    protected final String TAG = this.getClass().getSimpleName();


    public DialogUtil mDialogUtil = null;
    public Dialog mBaseDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        isDebug = App.isDebug;
        APP_NAME = App.APP_NAME;

        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mContextView);
        bind = ButterKnife.bind(this);

        mDialogUtil = new DialogUtil(this);

        EventBus.getDefault().register(this);
        if (null != getIntent()) {
            handleIntent(getIntent());
        }

        if (null == getSupportFragmentManager().getFragments()) {
            BaseFragment firstFragment = getFirstFragment();
            if (null != firstFragment) {
                addFragment(firstFragment);
            }
        }
        doBusiness(this);
    }


    /**
     * [初始化Bundle参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);

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


    protected abstract int getFragmentContentId();


    protected abstract BaseFragment getFirstFragment();

    protected void handleIntent(Intent intent) {

    }


    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    protected void addFragment(BaseFragment fragment,String key,Object defaultObject) {
        Bundle bundle = null;
        if (defaultObject!=null){
            String type = defaultObject.getClass().getSimpleName();
            bundle = new Bundle();
            if ("String".equals(type)) {
                bundle.putString(key, (String) defaultObject);
            } else if ("Integer".equals(type)) {
                bundle.putInt(key, (Integer) defaultObject);
            } else if ("Boolean".equals(type)) {
                bundle.putBoolean(key, (Boolean) defaultObject);
            } else if ("Float".equals(type)) {
                bundle.putFloat(key, (Float) defaultObject);
            } else if ("Long".equals(type)) {
                bundle.putLong(key,(Long) defaultObject);
            }
        }

        if (fragment != null) {
            if (bundle!=null){
                fragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }


    protected void addFragment(BaseFragment fragment,Bundle bundle) {
        if (fragment != null) {
            if (bundle!=null){
                fragment.setArguments(bundle);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }


    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public void showLoadingDialog(String msg)
    {
        if (msg != null)
        {
            if (mBaseDialog != null && mBaseDialog.isShowing())
            {
                mBaseDialog.dismiss();
                mBaseDialog = null;
            }
            mBaseDialog = mDialogUtil.showLoading(msg);
        }
    }

    public void hideLoadingDialog()
    {
        if (mBaseDialog != null && mBaseDialog.isShowing())
        {
            mBaseDialog.dismiss();
        }
    }


    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

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

    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
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
