package com.edianjucai.eshop.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseActivity;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.dao.InitModelDao;
import com.edianjucai.eshop.model.entity.InitModel;
import com.edianjucai.eshop.model.entity.RequestModel;
import com.edianjucai.eshop.server.InterfaceServer;
import com.edianjucai.eshop.service.AppUpgradeService;
import com.edianjucai.eshop.service.LocationService;
import com.edianjucai.eshop.util.HandlerUtil;
import com.edianjucai.eshop.util.ModelUtil;
import com.ta.sunday.http.impl.SDAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

public class InitActivity extends BaseActivity  {
    private SharedPreferences preferences;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_init;
    }

    @Override
    public void doBusiness(Context mContext) {
        startMapService();
        requestInitInterface();
        startUpgradeService();
    }

    private void startMapService() {
        Intent mapIntent = new Intent(InitActivity.this, LocationService.class);
        startService(mapIntent);
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }


    private void startUpgradeService() {
        Intent updateIntent = new Intent(InitActivity.this, AppUpgradeService.class);
        startService(updateIntent);
    }


    /**
     * 请求初始化接口
     */
    private void requestInitInterface() {
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("act", "init");
        RequestModel model = new RequestModel(mapData);

        SDAsyncHttpResponseHandler handler = new SDAsyncHttpResponseHandler() {

            @Override
            public void onStartInMainThread(Object result) {
                super.onStartInMainThread(result);
            }

            @Override
            public Object onSuccessInRequestThread(int statusCode, Header[] headers, String content) {
                try {
                    InitModel model = JSON.parseObject(content, InitModel.class);
                    return model;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            public void onSuccessInMainThread(int statusCode, Header[] headers, String content, Object result) {
                InitModel model = (InitModel) result;
                if (!ModelUtil.isActModelNull(model)) {
                    switch (model.getResponse_code()) {
                        case 1:
                            InitModelDao.saveInitModel(content);
                            break;

                        default:
                            break;
                    }
                }
            }

            @Override
            public void onFailureInMainThread(Throwable error, String content, Object result) {
                startMainActivity();
            }

            @Override
            public void onFinishInMainThread(Object result) {
                startMainActivity();
            }
        };
        InterfaceServer.getInstance().requestInterface(model, handler, true);
    }

    private void startMainActivity() {
        HandlerUtil.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                preferences = getSharedPreferences("count", MODE_PRIVATE);
                int count = preferences.getInt("count", 0);
                if (count == 0) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), GuideActivity.class);
                    startActivity(intent);
                    InitActivity.this.finish();
                } else {
                    Intent intent = new Intent(InitActivity.this, HomeActivity.class);
                    startActivity(intent);
                    InitActivity.this.finish();
                }
                SharedPreferences.Editor editor = preferences.edit();
                //存入数据
                editor.putInt("count", ++count);
                //提交修改
                editor.commit();
            }
        }, 1000 * 2);

    }
}
