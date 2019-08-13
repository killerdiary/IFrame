package com.hy.frame.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hy.frame.common.IBaseDialog;
import com.hy.frame.common.IBaseUI;
import com.hy.iframe.R;

/**
 * title 无
 * author heyan
 * time 19-7-12 下午2:58
 * desc 无
 */
public abstract class BaseDialog extends android.app.Dialog implements IBaseUI, IBaseDialog, View.OnClickListener {
    private long mLastTime;
    protected OnClickListener listener;

    //    private DialogInterface.OnClickListener
    public BaseDialog(Context context) {
        this(context, R.style.Base_DialogTheme);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onClick(View v) {
        if (!isFastClick())
            onViewClick(v);
    }

//    @Override
//    public void initWindow() {
//
//    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Context getCurContext() {
        return getContext();
    }

//    @Override
//    public int getLayoutId() {
//        return 0;
//    }

    @Override
    public View getLayoutView() {
        return null;
    }

    @Override
    public View initLayout() {
        View customView = getLayoutView();
        if (customView != null)
            setContentView(customView);
        else
            setContentView(getLayoutId());
        return null;
    }

    @Override
    public void windowDeploy(float width, float height, int gravity) {
        Window window = getWindow();
        if (window == null) return;
        DisplayMetrics dm = getCurContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams params = getWindow().getAttributes(); // 获取对话框当前的参数值
        if (width <= 0F) {
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        } else if (width <= 1) {
            params.width = (int) (dm.widthPixels * width + 0.5F); // 宽度设置为屏幕的0.x
        } else {
            params.width = (int) (width + 0.5F);
        }
        if (height <= 0F) {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        } else if (height <= 1) {
            params.height = (int) (dm.heightPixels * height + 0.5F); // 宽度设置为屏幕的0.x
        } else {
            params.height = (int) (height + 0.5F);
        }
        //params.verticalMargin = -0.1f 纵向边距，容器与widget之间的距离，占容器高度的百分率。
        window.setAttributes(params); // 设置生效
        window.setGravity(gravity);
        // getWindow().setGravity(Gravity.LEFT | Gravity.TOP);
        setCanceledOnTouchOutside(false);// 设置触摸对话框意外的地方取消对话框
        setCancelable(false);
        // window.setWindowAnimations(R.style.winAnimFadeInFadeOut);
    }
//    @Override
//    public void initView() {
//
//    }
//
//    @Override
//    public void initData() {
//
//    }
//
//    @Override
//    public void onViewClick(View v) {
//
//    }

    @Override
    public <T extends View> T findViewById(int id, View parent) {
        if (parent != null) return parent.findViewById(id);
        return findViewById(id);
    }

    protected <T extends View> T setOnClickListener(int id) {
        return setOnClickListener(id, null);
    }

    @Override
    public <T extends View> T setOnClickListener(int id, View parent) {
        T v = findViewById(id, parent);
        if (v != null) v.setOnClickListener(this);
        return v;
    }

    @Override
    public boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - this.mLastTime < 500L) return true;
        this.mLastTime = time;
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initWindow();
        initView();
        initData();
    }
}
