package com.edianjucai.eshop.util;

import android.text.TextUtils;

import com.edianjucai.eshop.model.entity.BaseActModel;

/**
 * Created by user on 2016-09-19.
 */
public class ModelUtil {
    public static boolean isActModelNull(BaseActModel actModel) {
        if (actModel != null) {
            if (!TextUtils.isEmpty(actModel.getShow_err())) {
                ToastUtils.showToast(actModel.getShow_err());
            }
            return false;
        } else {
            ToastUtils.showToast("接口访问失败或者json解析出错!");
            return true;
        }
    }
}
