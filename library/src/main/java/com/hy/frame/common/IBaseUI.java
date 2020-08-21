package com.hy.frame.common;

import android.view.View;

/**
 * title IBaseUI
 * author heyan
 * time 19-7-10 上午11:25
 * desc 无
 */
public interface IBaseUI {

    /**
     * 初始化基本布局
     */
    void initLayout();

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


}
