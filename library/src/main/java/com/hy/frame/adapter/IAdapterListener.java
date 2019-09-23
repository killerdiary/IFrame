package com.hy.frame.adapter;

import android.view.View;

/**
 * title IAdapterListener
 * author heyan
 * time 19-7-9 上午11:26
 * desc 无
 */
public interface IAdapterListener<T> {
    /**
     * 点击事件
     *
     * @param v        控件
     * @param item     数据
     * @param position 下标
     */
    void onViewClick(View v, T item, int position);
}
