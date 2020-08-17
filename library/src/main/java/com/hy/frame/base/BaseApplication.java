package com.hy.frame.base;

import android.app.Application;

import com.hy.frame.common.IApplication;
import com.hy.frame.util.ActManager;
import com.hy.frame.util.DataManager;
import com.hy.frame.util.DateUtil;
import com.hy.frame.util.LogUtil;
import com.hy.frame.util.PmUtil;

/**
 * title Base Application
 * author heyan
 * time 19-7-8 下午4:00
 * desc 无
 */
public abstract class BaseApplication extends Application implements IApplication {

    /**
     * Activity栈
     */
    private IActivityCache activityCache = null;
    /**
     * 全局数据
     */
    private IDataCache dataCache = null;

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = PmUtil.getProcessName(this, android.os.Process.myPid());
        if (processName != null) {
            if (!processName.equals(getPackageName())) {
                initAppForOtherProcess(processName);
                return;
            }
        } else {
            processName = "?";
        }
        //主进程逻辑
        INSTANCE = this;
        activityCache = new ActManager();
        dataCache = new DataManager();
        LogUtil.enableLog(isLoggable());
        LogUtil.d(this.getClass(), String.format("onCreate process:%s,time=%s", processName, DateUtil.getDateTime(System.currentTimeMillis(), null)));
        initAppForMainProcess();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.d(this.getClass(), "onTerminate");
    }

    @Override
    public boolean isLoggable() {
        return true;
    }

    @Override
    public void initAppForMainProcess() {

    }

    @Override
    public void initAppForOtherProcess(String process) {

    }

    @Override
    public void exit() {
        LogUtil.d(this.getClass(), "Application exit! ");
        if (activityCache != null)
            activityCache.clear(null);
        if (dataCache != null)
            dataCache.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    public Application getApplication() {
        return this;
    }

    @Override
    public IActivityCache getActivityCache() {
        return activityCache;
    }

    @Override
    public IDataCache getDataCache() {
        return dataCache;
    }


    //    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//        if (isMultiDex())
//            androidx.multidex.MultiDex.install(this)
//    }
    private static IApplication INSTANCE = null;

    public static IApplication getInstance() {
        return INSTANCE;
    }
}
