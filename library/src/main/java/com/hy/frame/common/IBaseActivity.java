package com.hy.frame.common;

/**
 * title IBaseActivity
 * author heyan
 * time 19-7-10 上午11:31
 * desc 无
 */
public interface IBaseActivity {
    /**
     * 屏幕方向，可以用Activity.setRequestedOrientation替代
     */
    int getScreenOrientation();

    /**
     * 判断是否拥有进入权限
     */
    boolean isPermissionDenied();

    /**
     * 获取上一级页面名称
     */
    String getLastSkipAct();
}
