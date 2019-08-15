package com.hy.frame.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * title 无
 * author heyan
 * time 19-8-13 下午3:14
 * desc 无
 */
public class BaseHolder {

    private final View mLayout;
    private final SparseArray<View> views;

    public BaseHolder(View mLayout) {
        this.mLayout = mLayout;
        this.views = new SparseArray<>();
    }


    /**
     * 获取 控件
     *
     * @param id 布局中某个组件的id
     */
    public <V extends View> V findViewById(int id) {
        View v = this.views.get(id);
        if (v == null) {
            v = mLayout.findViewById(id);
            views.put(id, v);
        }
        return mLayout.findViewById(id);
    }


    /**
     * 获取 控件
     *
     * @param id     布局中某个组件的id
     * @param parent parent
     */
    public <V extends View> V findViewById(int id, View parent) {
        if (parent != null) return parent.findViewById(id);
        return mLayout.findViewById(id);
    }
}
