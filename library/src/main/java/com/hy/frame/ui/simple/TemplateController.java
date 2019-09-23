package com.hy.frame.ui.simple;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.frame.common.IBaseTemplateUI;
import com.hy.frame.common.IBaseUI;
import com.hy.frame.common.IImageLoader;
import com.hy.frame.common.ILoadingDialog;
import com.hy.frame.common.ILoadingUI;
import com.hy.frame.common.ITemplateController;
import com.hy.frame.util.MyToast;
import com.hy.iframe.R;

/**
 * title 无
 * author heyan
 * time 19-7-11 上午11:34
 * desc 无
 */
public class TemplateController implements ITemplateController, View.OnClickListener {

    protected IBaseTemplateUI iUI;
    protected ViewGroup cToolbar = null;
    protected ViewGroup cMain = null;
    protected ILoadingUI iLoadingUI = null;
    protected ILoadingDialog iLoadingDialog = null;


    public TemplateController(IBaseTemplateUI iUI) {
        this.iUI = iUI;
    }

    @Override
    public void init(ViewGroup cToolbar, ViewGroup cMain) {
        this.cToolbar = cToolbar;
        this.cMain = cMain;
        initToolbar();
    }

    private Context getCurContext() {
        return this.iUI.getCurActivity();
    }

    private Resources getResources() {
        return getCurContext().getResources();
    }

    private <T extends View> T findViewById(int id, View parent) {
        if (iUI instanceof IBaseUI)
            return ((IBaseUI) iUI).findViewById(id, parent);
        return null;
    }

    private boolean isFastClick() {
        if (iUI instanceof IBaseUI)
            return ((IBaseUI) iUI).isFastClick();
        return false;
    }

    private int getStatusBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return iUI.getStatusBarHeight();
        }
        return 0;
    }

    private int getHeaderHeight() {
        int[] attr = new int[]{R.attr.appHeaderHeight};
        TypedArray array = getCurContext().getTheme().obtainStyledAttributes(attr);
        int headerHeight = array.getDimensionPixelSize(0, getResources().getDimensionPixelSize(R.dimen.header_height));
        array.recycle();
        return headerHeight + getStatusBarHeight();
    }

    private void initToolbar() {
        if (cToolbar == null) return;
        if (iUI.isTranslucentStatus()) {
            int statusBarHeight = getStatusBarHeight();
            if (statusBarHeight > 0) {
                cToolbar.setPadding(0, statusBarHeight, 0, 0);
                if (cToolbar.getLayoutParams() != null) {
                    ViewGroup.LayoutParams params = cToolbar.getLayoutParams();
                    params.height = getHeaderHeight();
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

    private void initHeaderLeft(int drawLeft, CharSequence leftStr) {
        if (this.cToolbar == null) return;
        View v = findViewById(R.id.base_vLeft, this.cToolbar);
        if (v == null) {
            if (drawLeft != 0) {
                View.inflate(getCurContext(), R.layout.in_head_left, this.cToolbar);
            } else {
                View.inflate(getCurContext(), R.layout.in_head_tleft, this.cToolbar);
            }
            v = findViewById(R.id.base_vLeft, this.cToolbar);
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
    protected ViewGroup.LayoutParams generateHeadActionLayoutParams(int gravity) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, getHeaderHeight());
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
            this.cToolbar.addView(v, generateHeadActionLayoutParams(Gravity.RIGHT));
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
            } else if (iUI instanceof IBaseUI) {
                IImageLoader loader = iUI.getImageLoader();
                if (loader != null) {
                    loader.load(img, pathOrStr);
                }
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
        MyToast.show(getCurContext(), msg);
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
            this.iLoadingDialog = new com.hy.frame.ui.dialog.LoadingDialog(getCurContext());
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
        } else if (this.iUI instanceof IBaseUI) {
            ((IBaseUI) this.iUI).onViewClick(v);
        }
    }
}
