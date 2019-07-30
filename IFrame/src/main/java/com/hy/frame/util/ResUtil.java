package com.hy.frame.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * title ResUtil
 * author heyan
 * time 19-7-10 下午4:34
 * desc  0.5F用于四舍五入 效率高
 */
public final class ResUtil {
    /**
     * 获取屏幕宽度
     *
     * @param cxt 上下文
     * @return int
     */
    public static int getScreenWidth(Context cxt) {
        DisplayMetrics dm = cxt.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param cxt 上下文
     * @return int
     */
    public static int getScreenHeight(Context cxt) {
        DisplayMetrics dm = cxt.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 计算设计比例
     *
     * @param cxt               上下文
     * @param designScreenWidth 设计尺寸
     * @return 比例 float
     */
    public static float calDesignScale(Context cxt, int designScreenWidth) {
        int screenWidth = cxt.getResources().getDisplayMetrics().widthPixels;
        if (designScreenWidth == 0 || designScreenWidth == screenWidth) return 1F;
        return ((float) screenWidth) / designScreenWidth;
    }

    /**
     * 计算设计比例
     *
     * @param width 设计尺寸
     * @param scale 需要缩放比例
     * @return 真实尺寸
     */
    public static int calDesignWidth(int width, float scale) {
        if (scale == 0 || scale == 1) return width;
        return (int) (width * scale + 0.5F);
    }

    /**
     * 顶部状态栏高度 电量条栏
     *
     * @param cxt 上下文
     * @return int
     */
    public static int getStatusBarHeight(Context cxt) {
        Resources resources = cxt.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        try {
            return resources.getDimensionPixelSize(resourceId);
        } catch (Exception ignored) {

        }
        return 0;
    }

    /**
     * 底部导航栏高度
     *
     * @param cxt 上下文
     * @return int
     */
    public static int getNavigationBarHeight(Context cxt) {
        Resources resources = cxt.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        try {
            return resources.getDimensionPixelSize(resourceId);
        } catch (Exception ignored) {

        }
        return 0;
    }

    /**
     * 改变状态栏颜色只适用于 sdk >= 19
     *
     * @param act   Activity
     * @param color Color
     */
    public static void changeStatusBarColor(Activity act, int color) {
        if (color < 0) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            act.getWindow().setStatusBarColor(color);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup contentView = act.findViewById(android.R.id.content);
            if (contentView == null) return;
            View child = null;
            if (contentView.getChildCount() > 0)
                child = contentView.getChildAt(0);
            //改变颜色时避免重复添加statusBarView
            int statusBarHeight = getStatusBarHeight(act);
            if (statusBarHeight == 0) return;
            if (child != null && child.getMeasuredHeight() == statusBarHeight) {
                child.setBackgroundColor(color);
                return;
            }
            child = new View(act);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            child.setBackgroundColor(color);
            contentView.addView(child, 0, lp);
        }
    }


    /**
     * px to dimension
     */
    public static float px2d(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value / metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value / metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value / (metrics.xdpi * (1.0f / 72));
            case TypedValue.COMPLEX_UNIT_IN:
                return value / metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value / (metrics.xdpi * (1.0f / 25.4f));
        }
        return 0;
    }

    /**
     * dimension to px
     */
    public static int d2px(int unit, float value, DisplayMetrics metrics) {
        return (int) (TypedValue.applyDimension(unit, value, metrics) + 0.5f);
    }

    /**
     * px to sp
     */
    public static float px2sp(Context cxt, float value) {
        return px2d(TypedValue.COMPLEX_UNIT_SP, value, cxt.getResources().getDisplayMetrics());
    }

    /**
     * px to dip
     */
    public static float px2dp(Context cxt, float value) {
        return px2d(TypedValue.COMPLEX_UNIT_DIP, value, cxt.getResources().getDisplayMetrics());
    }

    /**
     * sp to px
     */
    public static int sp2px(Context cxt, float value) {
        return d2px(TypedValue.COMPLEX_UNIT_SP, value, cxt.getResources().getDisplayMetrics());
    }

    /**
     * dip to px
     */
    public static int dip2px(Context cxt, float value) {
        return d2px(TypedValue.COMPLEX_UNIT_DIP, value, cxt.getResources().getDisplayMetrics());
    }

    /**
     * 获取颜色值
     *
     * @param cxt     Context
     * @param colorId resId
     * @return Color
     */
    public static int getColor(Context cxt, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return cxt.getResources().getColor(colorId, cxt.getTheme());
        } else {
            return cxt.getResources().getColor(colorId);
        }
    }

    public static <T extends View> T findViewById(int id, View parent) {
        if (parent != null) return parent.findViewById(id);
        return null;
    }
}
