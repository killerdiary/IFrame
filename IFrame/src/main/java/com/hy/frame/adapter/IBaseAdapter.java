package com.hy.frame.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * title IBaseAdapter
 * author heyan
 * time 19-8-13 下午2:45
 * desc List 和 Array不能 一起用
 */
public interface IBaseAdapter<T> {
    Context getContext();
//    /**
//     * 设置数据源
//     *
//     * @param datas 数据源
//     */
//    void setDatas(List<T> datas);
//
//    /**
//     * 设置数据源
//     *
//     * @param datas 数据源
//     */
//    void setArray(T[] datas);
//

    /**
     * 获取数据源
     */
    List<T> getDatas();
//
//    /**
//     * 获取数据源
//     */
//    T[] getArray();

    /**
     * 获取数据条数
     */
    int getDataCount();

    /**
     * 获取某条数据对应View Id
     *
     * @param position 第几条
     */
    int getDataId(int position);

    /**
     * 获取某条数据
     *
     * @param position 第几条
     */
    T getDataItem(int position);

//    /**
//     * 设置监听器
//     *
//     * @param listener 监听器
//     */
//    void setListener(IAdapterListener listener);

    /**
     * 获取监听器
     */
    IAdapterListener<T> getListener();

    /**
     * 获取长按监听器
     */
    IAdapterLongListener<T> getLongListener();


    void setOnClickListener(View v, int position);

    void setOnLongClickListener(View v, int position);

    /**
     * 主动刷新
     */
    void refresh();

    /**
     * 刷新数据
     *
     * @param datas 数据源
     */
    void refresh(List<T> datas);

    View inflate(int resId);

    int getItemLayoutId();

    View getItemView();

    BaseHolder getItemHolder(View v);

    void bindItemData(BaseHolder holder, int position);

//    /**
//     * 刷新数据
//     *
//     * @param datas 数据源
//     */
//    void refreshArray(T[] datas);
}
