package com.edianjucai.eshop.util;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by user on 2016-09-14.
 */
public class UiUtils {
    // 普通跳转
    public static void showNormal(Activity OldActivity, Class<?> cls, boolean isFinish) {
        Intent intent = new Intent(OldActivity, cls);
        OldActivity.startActivity(intent);
        if (isFinish)
            OldActivity.finish();
    }

    // 跳转返回
    public static void showForsult(Activity OldActivity, Class<?> cls, int RequestCode) {
        Intent intent = new Intent(OldActivity, cls);
        OldActivity.startActivityForResult(intent, RequestCode);
    }
}
