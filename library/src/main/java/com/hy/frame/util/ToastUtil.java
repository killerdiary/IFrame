package com.hy.frame.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * title 无
 * author heyan
 * time 19-7-10 上午11:09
 * desc 无
 */
public final class ToastUtil {
    private static Toast toast = null;

    public static void show(Context cxt, int strId) {
        show(cxt, cxt.getResources().getString(strId));
    }

    public static void show(Context cxt, CharSequence msg) {
        show(cxt, msg, Toast.LENGTH_SHORT);
    }

    @SuppressLint("ShowToast")
    public static void show(Context cxt, CharSequence msg, int duration) {
        try {
            //去掉应用名显示
            Toast toast = Toast.makeText(cxt.getApplicationContext(), null, duration);
            toast.setText(msg);
            toast.show();
        } catch (Exception ignored) {

        }
    }
}
