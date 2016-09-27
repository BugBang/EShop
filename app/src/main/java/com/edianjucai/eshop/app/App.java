package com.edianjucai.eshop.app;

import android.app.Application;
import android.content.Context;

import com.edianjucai.eshop.common.CommonInterface;
import com.edianjucai.eshop.dao.LocalUserModelDao;
import com.edianjucai.eshop.event.EventMsg;
import com.edianjucai.eshop.event.EventTag;
import com.edianjucai.eshop.model.entity.LocalUser;
import com.edianjucai.eshop.model.entity.RuntimeConfigModel;
import com.ta.util.netstate.TANetChangeObserver;
import com.ta.util.netstate.TANetWorkUtil;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by user on 2016-09-12.
 */
public class App extends Application implements TANetChangeObserver{

    private static App mApp = null;
    private static Context mContext = null;
    public static boolean isDebug = true;
    public static final String APP_NAME = "EdianShop" ;

    private LocalUser mLocalUser = null;

    private RuntimeConfigModel mRuntimeConfig = new RuntimeConfigModel();
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mApp = this;
        mContext = getApplicationContext();
        EventBus.getDefault().register(this);
        initLoginState();
    }

    public static App getApplication() {
        return mApp;
    }

    public static Context getContext() {
        return mContext;
    }

    private void initLoginState() {
        this.mLocalUser = LocalUserModelDao.getInstance().getModel();
        CommonInterface.refreshLocalUser();
    }

    public void setmLocalUser(LocalUser localUser) {
        if (localUser != null) {
            this.mLocalUser = localUser;
            LocalUserModelDao.getInstance().saveModel(localUser);
        }
    }

    public LocalUser getmLocalUser() {
        return mLocalUser;
    }

    public void clearAppsLocalUserModel() {
        LocalUserModelDao.getInstance().deleteAllModel();
        this.mLocalUser = null;
    }

    @Override
    public void onConnect(TANetWorkUtil.netType netType) {

    }

    @Override
    public void onDisConnect() {

    }

    public RuntimeConfigModel getmRuntimeConfig() {
        return mRuntimeConfig;
    }


    @Subscribe(threadMode = ThreadMode.PostThread)
    public void onMessageEventPostThread(EventMsg messageEvent) {
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(EventMsg messageEvent) {
        switch (messageEvent.getImsg()) {
            case EventTag.EVENT_LOGOUT_SUCCESS:
                clearAppsLocalUserModel();
                break;

            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onMessageEventBackgroundThread(EventMsg messageEvent) {
    }

    @Subscribe(threadMode = ThreadMode.Async)
    public void onMessageEventAsync(EventMsg messageEvent) {
    }
}
