package com.hy.frame.adapter;

import android.view.View;

/**
 * title IAdapterLongListener
 * author heyan
 * time 19-7-10 下午3:51
 * desc 无
 */
public interface IAdapterLongListener<T> extends IAdapterListener<T> {
    /**
     * 点击事件
     *
     * @param v        控件
     * @param item     数据
     * @param position 下标
     */
    void onViewLongClick(View v, T item, int position);
}
