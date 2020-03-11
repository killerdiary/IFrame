package com.hy.frame.adapter;

import android.content.Context;

import java.util.List;

/**
 * title 无
 * author heyan
 * time 19-8-13 下午4:06
 * desc 无
 */
public abstract class BaseArrayAdapter<T> extends BaseAdapter<T> {
    private T[] mDatas;

    public BaseArrayAdapter(Context mCxt) {
        this(mCxt, null);
    }

    public BaseArrayAdapter(Context mCxt, T[] mDatas) {
        this(mCxt, mDatas, null);
    }

    public BaseArrayAdapter(Context cxt, T[] datas, IAdapterListener<T> mListener) {
        super(cxt, null, mListener);
        this.mDatas = datas;
    }

    @Deprecated
    @Override
    public void setDatas(List<T> datas) {

    }

    @Deprecated
    @Override
    public List<T> getDatas() {
        return super.getDatas();
    }

    public void setArray(T[] datas) {
        this.mDatas = datas;
    }

    public T[] getArray() {
        return mDatas;
    }

    @Override
    public int getDataCount() {
        return this.mDatas == null ? 0 : this.mDatas.length;
    }

    @Override
    public T getDataItem(int position) {
        if (position < getDataCount())
            return this.mDatas[position];
        return null;
    }

    @Deprecated
    @Override
    public void refresh(List<T> datas) {
    }

    public void refresh(T[] datas) {
        this.mDatas = datas;
        refresh();
    }
}
