package com.edianjucai.eshop.util;

import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by user on 2016-09-20.
 */
public class ViewUtil {
    /**
     * 利用反射获取View 设置Gone之后的高度
     * @param v
     * @return 传入View的高度
     */
    public static int getTargetHeight(View v) {
        try {
            Method m = v.getClass().getDeclaredMethod("onMeasure", int.class,int.class);
            m.setAccessible(true);
            m.invoke(v, View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getMeasuredWidth(),
                    View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
        } catch (Exception e) {

        }
        return v.getMeasuredHeight();
    }
}
