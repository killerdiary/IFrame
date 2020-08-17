package com.hy.frame.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

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

    public static boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;

            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContextExisted(Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    return true;
                }
            } else if (context instanceof Service) {
                if (isServiceExisted(context, context.getClass().getName())) {
                    return true;
                }
            } else if (context instanceof Application) {
                return true;
            }
        }
        return false;
    }

    public static String getAbiS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StringBuilder sb = new StringBuilder();
            String[] abiS = Build.SUPPORTED_ABIS;
            if (abiS != null) {
                int size = abiS.length;
                int i = 0;
                for (String item : abiS) {
                    i++;
                    sb.append(item);
                    if (i < size)
                        sb.append(",");
                }
            }
            return sb.toString();
        }
        return Build.CPU_ABI + "," + Build.CPU_ABI2;

    }
}
