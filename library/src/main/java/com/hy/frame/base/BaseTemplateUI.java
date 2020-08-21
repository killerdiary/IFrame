package com.hy.frame.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.frame.common.IAppUI;
import com.hy.frame.common.IBaseUI;
import com.hy.frame.common.ILoadingDialog;
import com.hy.frame.common.ILoadingUI;
import com.hy.frame.common.ITemplateUI;
import com.hy.frame.ui.LoadingDialog;
import com.hy.frame.ui.LoadingUI;
import com.hy.frame.util.ResUtil;
import com.hy.frame.util.ToastUtil;
import com.hy.iframe.R;

/**
 * title BaseTemplateUI
 * author HeYan
 * time 20-8-15 下午5:57
 * desc 无
 */
public abstract class BaseTemplateUI implements ITemplateUI, View.OnClickListener {

    protected IAppUI iUI;
    protected View mLayout = null;
    protected ViewGroup cToolbar = null;
    protected ViewGroup cMain = null;
    protected ILoadingUI iLoadingUI = null;
    protected ILoadingDialog iLoadingDialog = null;
    protected SparseArray<View> views;
    private long mLastTime;

    public BaseTemplateUI(IAppUI iUI) {
        this.iUI = iUI;
    }

    @Override
    public boolean isSingleLayout() {
        return false;
    }

    /**
     * LayoutId 默认值为0
     */
    @Override
    public int getBaseLayoutId() {
        return R.layout.v_base;
    }

//    /**
//     * LayoutId 默认值为0
//     */
//    @Override
//    public int getLayoutId() {
//        return 0;
//    }


    /**
     * Layout View 不为空时优先使用
     */
    @Override
    public View getLayoutView() {
        return null;
    }

    @Override
    public View getRootLayout() {
        return this.mLayout;
    }

    @Override
    public View inflate(int resource, ViewGroup root) {
        return View.inflate(getCurContext(), resource, root);
    }

    @Override
    public boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - this.mLastTime < 500L) return true;
        this.mLastTime = time;
        return false;
    }

    @Override
    public ITemplateUI build() {
        initLayout();
        initToolbar();
        return this;
    }

    @Override
    public void initLayout() {
        if (this.mLayout != null) return;
        View cLayout = null;
        View customView = getLayoutView();
        if (isSingleLayout()) {
            if (customView != null) {
                cLayout = customView;
            } else if (getLayoutId() != 0) {
                cLayout = View.inflate(getCurContext(), getLayoutId(), null);
            }
        } else if (getBaseLayoutId() != 0) {
            cLayout = View.inflate(getCurContext(), getBaseLayoutId(), null);
        }
        if (cLayout == null) return;
        ViewGroup cToolbar = findViewById(R.id.base_cToolBar, cLayout);
        ViewGroup cMain = findViewById(R.id.base_cMain, cLayout);
        if (!isSingleLayout()) {
            if (cMain != null) {
                if (customView != null) {
                    cMain.addView(customView);
                } else if (getLayoutId() != 0) {
                    View.inflate(getCurContext(), getLayoutId(), cMain);
                }
            }
        } else {
            if (cMain == null && cLayout instanceof ViewGroup) {
                cMain = (ViewGroup) cLayout;
            }
        }
        this.mLayout = cLayout;
        this.cToolbar = cToolbar;
        this.cMain = cMain;
    }

    protected Context getCurContext() {
        return this.iUI.getCurContext();
    }

    protected Resources getResources() {
        return getCurContext().getResources();
    }

    /**
     * 获取 控件
     *
     * @param id 布局中某个组件的id
     */
    @Override
    public <V extends View> V findViewById(int id) {
        if (this.views == null) {
            this.views = new SparseArray<>();
        }
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
    @Override
    public <V extends View> V findViewById(int id, View parent) {
        if (parent != null) return parent.findViewById(id);
        return findViewById(id);
    }

    @Override
    public <T extends View> T setOnClickListener(int id) {
        T v = findViewById(id);
        if (v != null) v.setOnClickListener(this);
        return v;
    }

    @Override
    public <T extends View> T setOnClickListener(int id, View parent) {
        T v = findViewById(id, parent);
        if (v != null) v.setOnClickListener(this);
        return v;
    }


    @Override
    public int getStatusBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return ResUtil.getStatusBarHeight(getCurContext());
        }
        return 0;
    }

    @Override
    public boolean isTranslucentStatus() {
        return true;
    }

    private int getHeaderHeight() {
        int[] attr = new int[]{R.attr.appHeaderHeight};
        TypedArray array = getCurContext().getTheme().obtainStyledAttributes(attr);
        int headerHeight = array.getDimensionPixelSize(0, getResources().getDimensionPixelSize(R.dimen.header_height));
        array.recycle();
        return headerHeight;
    }

    private void initToolbar() {
        if (cToolbar == null) return;
        if (isTranslucentStatus()) {
            int statusBarHeight = getStatusBarHeight();
            if (statusBarHeight > 0) {
                cToolbar.setPadding(0, statusBarHeight, 0, 0);
                if (cToolbar.getLayoutParams() != null) {
                    ViewGroup.LayoutParams params = cToolbar.getLayoutParams();
                    params.height = getHeaderHeight() + statusBarHeight;
                    cToolbar.setLayoutParams(params);
                }
            }
        }
    }

    @Override
    public void setTitle(int strId) {
        setTitle(getResources().getString(strId));
    }

    @Override
    public void setTitle(CharSequence titleStr) {
        if (this.cToolbar == null) return;
        View v = findViewById(R.id.base_vTitle, this.cToolbar);
        if (v == null) {
            View.inflate(getCurContext(), R.layout.in_head_title, this.cToolbar);
            v = findViewById(R.id.base_vTitle, this.cToolbar);
        }
        if (v instanceof TextView) {
            ((TextView) v).setText(titleStr);
        }
    }

    @Override
    public void setHeaderLeft(int drawLeft) {
        initHeaderLeft(drawLeft, null);
    }

    @Override
    public void setHeaderLeftTxt(CharSequence leftStr) {
        initHeaderLeft(0, leftStr);
    }

    @SuppressLint("RtlHardcoded")
    private void initHeaderLeft(int drawLeft, CharSequence leftStr) {
        if (this.cToolbar == null) return;
        View v = findViewById(R.id.base_vLeft, this.cToolbar);
        if (v == null) {
            boolean isImage = drawLeft != 0;
            if (isImage) {
                v = View.inflate(getCurContext(), R.layout.in_head_left, null);
            } else {
                v = View.inflate(getCurContext(), R.layout.in_head_tleft, null);
            }
            v.setOnClickListener(this);
            this.cToolbar.addView(v, generateHeadActionLayoutParams(isImage, Gravity.LEFT));
        }
        if (v instanceof ImageView) {
            ((ImageView) v).setImageResource(drawLeft);
            v.setOnClickListener(this);
        } else if (v instanceof TextView) {
            ((TextView) v).setText(leftStr);
            v.setOnClickListener(this);
        }
    }

    @Override
    public void setHeaderRightTxt(CharSequence rightStr) {
        initHeaderRight(false, 0, rightStr, R.id.base_vRight);
    }

    @Override
    public void setHeaderRight(int drawRight) {
        addHeaderRight(drawRight, R.id.base_vRight);
    }

    @Override
    public void addHeaderRight(int drawRight, int id) {
        initHeaderRight(true, drawRight, null, id);
    }

    @Override
    public void addHeaderRightPath(CharSequence rightPath, int id) {
        initHeaderRight(true, 0, rightPath, id);
    }

    /**
     * 头部按钮布局 如果header不是FrameLayout, 可以继承重写
     *
     * @param gravity Gravity
     * @return LayoutParams
     */
    protected ViewGroup.LayoutParams generateHeadActionLayoutParams(boolean isImage, int gravity) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, getHeaderHeight());
        if (isImage) {
            lp.width = getHeaderHeight();
        }
        lp.gravity = gravity;
        return lp;
    }

    @SuppressLint("RtlHardcoded")
    private void initHeaderRight(boolean isImage, int drawRightOrStrId, CharSequence pathOrStr, int id) {
        if (this.cToolbar == null) return;
        View v = findViewById(id, this.cToolbar);
        if (v == null) {
            if (drawRightOrStrId == 0 && pathOrStr == null) return;
            if (isImage) {
                v = View.inflate(getCurContext(), R.layout.in_head_right, null);
            } else {
                v = View.inflate(getCurContext(), R.layout.in_head_tright, null);
            }
            v.setId(id);
            v.setOnClickListener(this);
            this.cToolbar.addView(v, generateHeadActionLayoutParams(isImage, Gravity.RIGHT));
        }
        if (drawRightOrStrId == 0 && pathOrStr == null) {
            //remove
            this.cToolbar.removeView(v);
            return;
        }
        if (v instanceof ImageView) {
            if (!isImage) return;
            ImageView img = (ImageView) v;
            if (drawRightOrStrId != 0) {
                img.setImageResource(drawRightOrStrId);
            } else   {
                img.setImageURI(Uri.parse(pathOrStr.toString()));
            }
            return;
        }
        if (v instanceof TextView) {
            if (isImage) return;
            TextView txt = (TextView) v;
            if (drawRightOrStrId != 0) {
                txt.setText(drawRightOrStrId);
            } else {
                txt.setText(pathOrStr);
            }
        }
    }

    @Override
    public void showToast(CharSequence msg) {
        ToastUtil.show(getCurContext(), msg);
    }

    @Override
    public boolean initLoading() {
        if (this.iLoadingUI == null) {
            this.iLoadingUI = new LoadingUI();
        }
        return this.iLoadingUI.initLoading(this.cMain);
    }

    @Override
    public void setLoading(ILoadingUI iLoadingUI) {
        this.iLoadingUI = iLoadingUI;
    }

    @Override
    public void showLoading(int strId) {
        if (initLoading())
            this.iLoadingUI.showLoading(strId);
    }

    @Override
    public void showLoading(CharSequence msg) {
        if (initLoading())
            this.iLoadingUI.showLoading(msg);
    }

    @Override
    public void showNoData(int strId, int drawId) {
        if (initLoading())
            this.iLoadingUI.showNoData(strId, drawId);
    }

    @Override
    public void showNoData(CharSequence msg, int drawId) {
        if (initLoading())
            this.iLoadingUI.showNoData(msg, drawId);
    }

    @Override
    public void showCView() {
        if (initLoading())
            this.iLoadingUI.hide();
    }

    @Override
    public void setLoadingDialog(ILoadingDialog iLoading) {
        this.iLoadingDialog = iLoading;
    }

    @Override
    public void showLoadingDialog(int strId) {
        showLoadingDialog(getCurContext().getString(strId));
    }

    @Override
    public void showLoadingDialog(CharSequence msg) {
        if (this.iLoadingDialog == null)
            this.iLoadingDialog = new LoadingDialog(getCurContext());
        this.iLoadingDialog.showDialog(msg);
    }

    @Override
    public void hideLoadingDialog() {
        if (this.iLoadingDialog != null)
            this.iLoadingDialog.hideDialog();
    }

    @Override
    public void hideHeader() {
        if (this.cToolbar == null) return;
        this.cToolbar.setVisibility(View.GONE);
    }

    @Override
    public ViewGroup getHeader() {
        return this.cToolbar;
    }

    @Override
    public View getHeaderTitle() {
        return findViewById(R.id.base_vTitle, this.cToolbar);
    }

    @Override
    public View getHeaderLeft() {
        return findViewById(R.id.base_vLeft, this.cToolbar);
    }

    @Override
    public View getHeaderRight() {
        return findViewById(R.id.base_vRight, this.cToolbar);
    }

    @Override
    public ViewGroup getMainView() {
        return this.cMain;
    }

    @Override
    public void onDestroy() {
        this.iLoadingDialog = null;
        this.iLoadingUI = null;
        this.cToolbar = null;
        this.cMain = null;
        this.iUI = null;
    }

    @Override
    public void onClick(View v) {
        if (this.iUI == null || isFastClick()) return;
        int id = v.getId();
        if (id == R.id.base_vLeft) {
            this.iUI.onLeftClick();
        } else if (id == R.id.base_vRight) {
            this.iUI.onRightClick();
        } else if (id == R.id.base_cLoad) {
            this.iUI.onLoadViewClick();
        } else {
            onViewClick(v);
        }
    }

    @Override
    public void onViewClick(View v) {
        if (this.iUI instanceof IBaseUI) {
            ((IBaseUI) this.iUI).onViewClick(v);
        }
    }
}
