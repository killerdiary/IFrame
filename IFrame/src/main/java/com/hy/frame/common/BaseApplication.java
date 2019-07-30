package com.hy.frame.common;

import android.app.Activity;
import android.app.Application;

import com.hy.frame.util.DateUtil;
import com.hy.frame.util.MyLog;
import com.hy.frame.util.PmUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * title Base Application
 * author heyan
 * time 19-7-8 下午4:00
 * desc 无
 */
public abstract class BaseApplication extends Application implements IBaseApplication, IBaseApplication.IActivityCache, IBaseApplication.IDataCache {

    /**
     * Activity栈
     */
    private List<Activity> acts = null;
    /**
     * 全局数据
     */
    private Map<String, Object> maps = null;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        MyLog.enableLog(isLoggable());
        String processName = PmUtil.getProcessName(this, android.os.Process.myPid());
        if (processName != null) {
            if (!processName.equals(getPackageName())) {
                initAppForOtherProcess(processName);
                return;
            }
        } else {
            processName = "?";
        }
        MyLog.d(this.getClass(), String.format("onCreate process:%s,time=%s", processName, DateUtil.getDateTime(System.currentTimeMillis(), null)));
        initAppForMainProcess();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MyLog.d(this.getClass(), "onTerminate");
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
        MyLog.d(this.getClass(), "Application exit! ");
        clear(null);
        System.exit(0);
    }

    @Override
    public Application getApplication() {
        return this;
    }

    @Override
    public IActivityCache getActivityCache() {
        return this;
    }

    @Override
    public IDataCache getDataCache() {
        return this;
    }

    @Override
    public void add(Activity activity) {
        if (acts == null)
            acts = new ArrayList<>();
        else
            remove(activity); //防止重复添加
        acts.add(activity);
    }

    @Override
    public void remove(Activity activity) {
        if (acts != null && acts.size() > 0) {
            acts.remove(activity);
        }
    }

    @Override
    public void clear(Activity ignore) {
        if (acts != null && acts.size() > 0) {
            for (Activity item : acts) {
                if (item == ignore) continue;
                item.finish();
            }
            acts.clear();
            if (ignore != null) acts.add(ignore);//忽略项重新加入栈顶
        }
    }

    @Override
    public int actSize() {
        return acts != null ? acts.size() : 0;
    }

    @Override
    public Activity getAct(int index) {
        if (acts != null && index < acts.size() && index >= 0) {
            return acts.get(index);
        }
        return null;
    }

    @Override
    public void putValue(String key, Object value) {
        if (maps == null) {
            maps = new HashMap<>();
        }
        if (value == null)
            maps.remove(key);
        else
            maps.put(key, value);
    }

    @Override
    public Object getValue(String key) {
        return maps != null ? maps.get(key) : null;
    }

    //    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
//        if (isMultiDex())
//            androidx.multidex.MultiDex.install(this)
//    }
    private static IBaseApplication INSTANCE = null;

    public static IBaseApplication getInstance() {
        return INSTANCE;
    }
}
