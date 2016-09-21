package com.edianjucai.eshop.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.edianjucai.eshop.app.App;

/**
 * Created by user on 2016-09-13.
 */
public class ToastUtils {


    private static Toast toast;

    public static Handler mHandler = new Handler(Looper.getMainLooper());

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    public static void show(Context context, String showText, int showTime) {
        Toast.makeText(context, showText, showTime).show();
    }

    public static void show(Context context, String showText) {
        Toast.makeText(context, showText, Toast.LENGTH_SHORT).show();
    }

    public static void show(final Activity activity, final String showText) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, showText, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    public static void showToast(final String text, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(text, duration);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(text, duration);
                }
            });
        }
    }

    private static void show(String text, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(App.getApplication(), text, duration);
        toast.show();
    }
}
