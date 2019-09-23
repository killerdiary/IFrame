package com.hy.frame.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * title packageManager 工具
 * author heyan
 * time 19-7-8 下午6:13
 * desc 无
 */
public final class PmUtil {
    /**
     * 获取PackageInfo
     */
    public static PackageInfo getPackageInfo(Context cxt, String packageName) {
        if (packageName == null || packageName.length() == 0)
            packageName = cxt.getPackageName();
        try {
            return cxt.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES); //0 获取所有信息
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 获取ApplicationInfo
     */
    public static ApplicationInfo getApplicationInfo(Context cxt, String packageName) {
        if (packageName == null || packageName.length() == 0)
            packageName = cxt.getPackageName();
        try {
            return cxt.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 检测该包名所对应的应用是否存在
     */
    public static boolean checkPackage(Context cxt, String packageName) {
        return getPackageInfo(cxt, packageName) != null;
    }


    /**
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName(Context cxt, int pid) {
        try {
            ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            if (runningApps != null) {
                for (ActivityManager.RunningAppProcessInfo item : runningApps) {
                    if (item.pid == pid) {
                        return item.processName;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
