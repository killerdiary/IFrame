package com.hy.frame.util;

import android.app.Activity;

import com.hy.frame.common.IApplication;

import java.util.Stack;

public class ActManager implements IApplication.IActivityCache {

    private Stack<Activity> acts = null;

    /**
     * 添加Activity到容器中
     *
     * @param activity
     */
    @Override
    public void add(Activity activity) {
        if (activity == null) return;
        if (acts == null)
            acts = new Stack();
        else if (acts.contains(activity)) {
            //防止重复添加，防止异常不finish
            acts.remove(activity);
        }
        acts.add(activity);
    }

    /**
     * remove activity栈
     *
     * @param activity
     */
    @Override
    public void remove(Activity activity) {
        if (acts == null) return;
        if (activity == null) return;
        if (acts.contains(activity)) {
            acts.remove(activity);
            if (activity.isDestroyed() || activity.isFinishing()) return;
            activity.finish();
        }
    }

    /**
     * 清理activity栈
     * finish and remove
     *
     * @param activity 忽略项,可以为空
     */
    @Override
    public void clear(Activity activity) {
        if (actSize() == 0) return;
        while (true) {
            Activity act = curActivity();
            if (act == null) break;
            if (activity != null && activity == act) {
                //只移除不销毁
                acts.remove(act);
                continue;
            }
            remove(act);
        }
    }

    /**
     * activity栈数量
     */
    @Override
    public int actSize() {
        return acts != null ? acts.size() : 0;
    }

    /**
     * 获取activity
     *
     * @param index 位置
     */
    @Override
    public Activity getAct(int index) {
        if (index >= 0 && acts != null && index < actSize()) {
            return acts.get(index);
        } else return null;
    }

    Activity curActivity() {
        if (actSize() == 0) return null;
        try {
            return acts.lastElement();
        } catch (Exception ignored) {
        }
        return null;
    }
}
