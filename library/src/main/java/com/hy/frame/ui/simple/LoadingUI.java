package com.hy.frame.ui.simple;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.frame.common.ILoadingUI;
import com.hy.frame.util.MyLog;
import com.hy.iframe.R;

/**
 * title 无
 * author heyan
 * time 19-7-12 上午11:55
 * desc 无
 */
public class LoadingUI implements ILoadingUI {
    private ViewGroup parent = null;
    private View cLoad = null;
    private TextView txtMessage = null;
    private ImageView imgMessage = null;
    private View vLoading = null;

    @Override
    public boolean initLoading(ViewGroup parent) {
        this.parent = parent;
        if (!(parent instanceof FrameLayout)) {
            MyLog.e(getClass(), "Your layout must include 'FrameLayout',the ID must be 'base_cMain'!");
            return false;
        }
        if (cLoad != null) return true;
        cLoad = parent.findViewById(R.id.base_cLoad);
        //You need to add the layout
        if (cLoad == null) {
            cLoad = View.inflate(parent.getContext(), R.layout.in_loading, null);
            this.parent.addView(cLoad, 0);
        }
        this.txtMessage = cLoad.findViewById(R.id.base_txtMessage);
        this.imgMessage = cLoad.findViewById(R.id.base_imgMessage);
        this.vLoading = cLoad.findViewById(R.id.base_vLoading);
        return true;
    }

    @Override
    public void showLoading(int strId) {
        showLoading(parent.getContext().getString(strId));
    }

    @Override
    public void showLoading(CharSequence msg) {
        show();
        this.imgMessage.setVisibility(View.GONE);
        this.vLoading.setVisibility(View.VISIBLE);
        this.txtMessage.setText(msg);
    }

    @Override
    public void showNoData(int strId, int drawId) {
        showNoData(parent.getContext().getString(strId), drawId);
    }

    @Override
    public void showNoData(CharSequence msg, int drawId) {
        show();
        this.imgMessage.setVisibility(View.VISIBLE);
        this.vLoading.setVisibility(View.GONE);
        this.txtMessage.setText(msg);
        this.imgMessage.setImageResource(drawId);
    }

    private void show() {
        for (int i = 0; i < this.parent.getChildCount(); i++) {
            this.parent.getChildAt(i).setVisibility(View.GONE);
        }
        cLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        for (int i = 0; i < this.parent.getChildCount(); i++) {
            this.parent.getChildAt(i).setVisibility(View.VISIBLE);
        }
        cLoad.setVisibility(View.GONE);
    }
}
