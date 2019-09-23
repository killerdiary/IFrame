package com.hy.frame.common;

import android.view.ViewGroup;

/**
 * title ILoadingUI
 * author heyan
 * time 19-7-12 上午11:35
 * desc 无
 */
public interface ILoadingUI {
    /**
     * 初始化
     *
     * @param parent 父布局
     * @return 是否初始化成功
     */
    boolean initLoading(ViewGroup parent);

    /**
     * 显示加载中
     *
     * @param strId 文本资源ID
     */
    void showLoading(int strId);

    /**
     * 显示加载中
     *
     * @param msg 描述
     */
    void showLoading(CharSequence msg);

    /**
     * 显示没有数据或错误页
     *
     * @param strId  文本资源ID
     * @param drawId 图片资源ID
     */
    void showNoData(int strId, int drawId);

    /**
     * 显示没有数据或错误页
     *
     * @param msg    描述
     * @param drawId 图片资源ID
     */
    void showNoData(CharSequence msg, int drawId);

    /**
     * 隐藏
     */
    void hide();
}
