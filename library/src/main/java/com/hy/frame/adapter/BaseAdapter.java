package com.hy.frame.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.hy.iframe.R;

import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter implements IBaseAdapter<T> {
    private Context mCxt;
    private List<T> mDatas;
    private IAdapterListener<T> mListener;
    private View.OnClickListener clickListener;
    private View.OnLongClickListener longClickListener;

    public BaseAdapter(Context mCxt) {
        this(mCxt, null);
    }

    public BaseAdapter(Context mCxt, List<T> mDatas) {
        this(mCxt, mDatas, null);
    }

    public BaseAdapter(Context cxt, List<T> datas, IAdapterListener<T> mListener) {
        this.mCxt = cxt;
        this.mDatas = datas;
        this.mListener = mListener;
    }

    @Override
    public Context getContext() {
        return this.mCxt;
    }

    @Override
    public int getCount() {
        return getDataCount();
    }

    @Override
    public Object getItem(int position) {
        return getDataItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDatas(List<T> datas) {
        this.mDatas = datas;
    }

    @Override
    public List<T> getDatas() {
        return this.mDatas;
    }

    @Override
    public boolean isBindDataId() {
        return true;
    }

    /**
     * 会自动设置为当前View的ID
     *
     * @param position 位置
     */
    @Override
    public int getDataId(int position) {
        return position;
    }

    @Override
    public int getDataCount() {
        return this.mDatas == null ? 0 : this.mDatas.size();
    }

    @Override
    public T getDataItem(int position) {
        if (position < getDataCount())
            return this.mDatas.get(position);
        return null;
    }

    @Override
    public IAdapterListener<T> getListener() {
        return this.mListener;
    }

    @Override
    public IAdapterLongListener<T> getLongListener() {
        if (getListener() != null && getListener() instanceof IAdapterLongListener<?>) {
            return (IAdapterLongListener<T>) getListener();
        }
        return null;
    }

    @Override
    public void setOnClickListener(View v, int position) {
        if (v == null) return;
        if (this.clickListener == null) {
            this.clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag(R.id.adapter_position);
                    getListener().onViewClick(v, getDataItem(position), position);
                }
            };
        }
        v.setTag(R.id.adapter_position, position);
        v.setOnClickListener(this.clickListener);
    }

    @Override
    public void setOnLongClickListener(View v, int position) {
        if (v == null) return;
        if (this.longClickListener == null) {
            this.longClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = (int) v.getTag(R.id.adapter_position);
                    getListener().onViewClick(v, getDataItem(position), position);
                    return true;//拦截点击事件
                }
            };
        }
        v.setTag(R.id.adapter_position, position);
        v.setOnLongClickListener(this.longClickListener);
    }

    @Override
    public void refresh() {
        this.notifyDataSetChanged();
    }

    @Override
    public void refresh(List<T> datas) {
        this.mDatas = datas;
        refresh();
    }

    @Override
    public View inflate(int resId) {
        return View.inflate(getContext(), resId, null);
    }

    @Override
    public View getItemView() {
        return inflate(getItemLayoutId());
    }

    @Override
    public BaseHolder getItemHolder(View v) {
        return new BaseHolder(v);
    }

    @Override
    public View getView(int position, View cacheView, ViewGroup parent) {
        BaseHolder holder;
        if (cacheView == null) {
            cacheView = getItemView();
            holder = getItemHolder(cacheView);
            cacheView.setTag(R.id.adapter_holder, holder);
        } else {
            holder = (BaseHolder) cacheView.getTag(R.id.adapter_holder);
        }
        if (isBindDataId())
            cacheView.setId(getDataId(position));
        bindItemData(holder, position);
        return cacheView;
    }
}
