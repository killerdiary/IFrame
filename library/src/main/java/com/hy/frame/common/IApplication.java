package com.hy.frame.common;

/**
 * title Application Interface
 * author heyan
 * time 19-7-8 下午3:43
 * desc 无
 */
public interface IApplication {
    /**
     * Log 开关
     */
    boolean isLoggable();

//    /**
//     * Vector 开关
//     */
//    fun isVector(): Boolean

//    /**
//     * 是否开启MultiDex，如需开启需要同时配置gradle defaultConfig {multiDexEnabled true}
//     */
//    boolean isMultiDex();
// 上面两个方法废弃,需要该功能 自己在子Application中实现

    /**
     * 主进程方法
     */
    void initAppForMainProcess();

    /**
     * 其它进程方法
     */
    void initAppForOtherProcess(String process);

    /**
     * 退出
     */
    void exit();

    /**
     * 获取当前Application
     */
    android.app.Application getApplication();

    /**
     * 获取当前IActivityCache
     */
    IActivityCache getActivityCache();

    /**
     * 获取当前IDataCache
     */
    IDataCache getDataCache();

    /**
     * Activity Cache
     */
    interface IActivityCache {
        /**
         * 添加Activity到容器中
         */
        void add(android.app.Activity activity);

        /**
         * remove activity栈
         */
        void remove(android.app.Activity activity);

        /**
         * 清理activity栈
         * finish and remove
         *
         * @param activity 忽略项,可以为空
         */
        void clear(android.app.Activity activity);

        /**
         * activity栈数量
         */
        int actSize();

        /**
         * 获取activity
         *
         * @param index 位置
         */
        android.app.Activity getAct(int index);
    }

    /**
     * Data Cache
     */
    interface IDataCache {
        /**
         * 存数据
         *
         * @param key   键
         * @param value 值,任何类型,为空时清空
         */
        void putValue(String key, Object value);

        /**
         * 取数据
         */
        Object getValue(String key);

        void clear();
    }
}
