package com.hy.frame.common;

import android.view.View;

/**
 * title 无
 * author heyan
 * time 19-7-10 上午11:35
 * desc 无
 */
public interface IBaseFragment<F> {

    /**
     * 页面恢复显示 第二季展示调用
     */
    void onRestart();

    /**
     * 是否已经初始化布局
     */
    boolean isInit();

    /**
     * 获取 控件
     *
     * @param id 行布局中某个组件的id
     */
    <T extends View> T findViewById(int id);

    /**
     * 获取对应的Fragment
     */
    F getCurFragment();
}
