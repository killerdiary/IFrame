package com.hy.frame.common;

import android.content.Context;
import android.view.View;

/**
 * title IBaseUI
 * author heyan
 * time 19-7-10 上午11:25
 * desc 无
 */
public interface IBaseUI {
    Context getCurContext();

    /**
     * LayoutId 默认值为0
     */
    int getLayoutId();

    /**
     * Layout View 不为空时优先使用
     */
    View getLayoutView();

    /**
     * 初始化基本布局
     */
    View initLayout();

    /**
     * 初始化布局
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 控件点击事件
     */
    void onViewClick(View v);

    /**
     * 获取 控件
     *
     * @param id     行布局中某个组件的id
     * @param parent parent
     */
    <T extends View> T findViewById(int id, View parent);

    /**
     * 获取并绑定点击
     *
     * @param id     行布局中某个组件的id
     * @param parent parent
     */
    <T extends View> T setOnClickListener(int id, View parent);

    /**
     * 是否是快速点击
     */
    boolean isFastClick();
}
