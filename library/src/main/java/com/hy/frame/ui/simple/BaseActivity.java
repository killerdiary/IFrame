package com.hy.frame.ui.simple;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hy.frame.common.BaseApplication;
import com.hy.frame.common.IBaseActivity;
import com.hy.frame.common.IBaseApplication;
import com.hy.frame.common.IBaseTemplateUI;
import com.hy.frame.common.IBaseUI;
import com.hy.frame.common.IImageLoader;
import com.hy.frame.common.ITemplateController;
import com.hy.frame.util.MyLog;
import com.hy.frame.util.ResUtil;
import com.hy.iframe.R;

/**
 * title BaseActivity
 * author heyan
 * time 19-7-11 上午9:59
 * desc 无
 */
public abstract class BaseActivity extends Activity implements IBaseUI, IBaseTemplateUI, IBaseActivity, View.OnClickListener {

    private IBaseApplication mApp = null;
    private ITemplateController mTemplateController = null;
    private IImageLoader mImageLoader = null;
    private String mLastSkipAct = null; //获取上一级的Activity名

    private long mLastTime;
    private Bundle mArgs = null;
    private View mLayout = null;

    private boolean mDestroy;
    private boolean mPause;
    private boolean mStop;
    private boolean mResume;

    @Override
    public int getScreenOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    @Override
    public boolean isPermissionDenied() {
        return false;
    }

    @Override
    public String getLastSkipAct() {
        return this.mLastSkipAct;
    }

    @Override
    public boolean isSingleLayout() {
        return true;
    }

    @Override
    public ITemplateController getTemplateController() {
        return this.mTemplateController;
    }

    @Override
    public ITemplateController buildTemplateController() {
        return new TemplateController(this);
    }

    @Override
    public IImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    @Override
    public IImageLoader buildImageLoader() {
        return null;
    }

    @Override
    public boolean isTranslucentStatus() {
        return false;
    }

    @Override
    public int getStatusBarHeight() {
        return ResUtil.getStatusBarHeight(getCurContext());
    }

    @Override
    public IBaseApplication getCurApp() {
        return this.mApp;
    }

    @Override
    public Activity getCurActivity() {
        return this;
    }

    @Override
    public Bundle getArgs() {
        return this.mArgs;
    }

    public String getStrings(int... ids) {
        StringBuilder sb = new StringBuilder();
        for (int id : ids) {
            sb.append(getString(id));
        }
        return sb.toString();
    }

    @Override
    public void onLeftClick() {
        onBackPressed();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onLoadViewClick() {

    }

    @Override
    public void startAct(Class<?> cls) {
        startAct(cls, null, null);
    }

    @Override
    public void startAct(Class<?> cls, Bundle bundle, Intent intent) {
        startActForResult(cls, 0, bundle, intent);
    }

    @Override
    public void startActForResult(Class<?> cls, int requestCode) {
        startActForResult(cls, requestCode, null, null);
    }

    @Override
    public void startActForResult(Class<?> cls, int requestCode, Bundle bundle, Intent intent) {
        Intent i = intent;
        if (i == null)
            i = new Intent();
        Bundle b = bundle;
        if (b == null)
            b = new Bundle();
        b.putString(ARG_LAST_ACT, getClass().getSimpleName());
        i.putExtra(ARG_BUNDLE, b);
        i.setClass(getCurContext(), cls);
        if (requestCode != 0)
            startActivityForResult(i, requestCode);
        else
            startActivity(i);
    }

    @Override
    public Context getCurContext() {
        return this;
    }

    @Override
    public int getBaseLayoutId() {
        return R.layout.v_base;
    }
//    @Override
//    public int getLayoutId() {
//        return 0;
//    }

    @Override
    public View getLayoutView() {
        return null;
    }
//
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
    public void onClick(View v) {
        if (!isFastClick())
            onViewClick(v);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        if (this.mApp != null)
            this.mApp.getActivityCache().remove(this);
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!initAttrs()) return;
        if (isPermissionDenied()) {
            finish();
            return;
        }
        if (getScreenOrientation() != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(getScreenOrientation());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isTranslucentStatus()) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        initLayout();
        if (this.mLayout != null)
            setContentView(this.mLayout);
        initView();
        initData();
    }

    /**
     * 初始化基本属性
     *
     * @return boolean
     */
    private boolean initAttrs() {
        if (!(getApplication() instanceof BaseApplication)) {
            MyLog.e("Application configuration exception, currently application must extends BaseApplication");
            setContentView(R.layout.v_frame_warn);
            return false;
        }
        this.mApp = (IBaseApplication) getApplication();
        if (getIntent().hasExtra(ARG_BUNDLE))
            this.mArgs = getIntent().getBundleExtra(ARG_BUNDLE);
        else
            this.mArgs = getIntent().getExtras();
        if (this.mArgs != null)
            this.mLastSkipAct = this.mArgs.getString(ARG_LAST_ACT);
        this.mApp.getActivityCache().add(this);
        this.mTemplateController = buildTemplateController();
        this.mImageLoader = buildImageLoader();
        return true;
    }

    @Override
    public View initLayout() {
        if (this.mLayout != null) return this.mLayout;
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
        if (cLayout == null) return null;
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
        if (this.mTemplateController != null) {
            this.mTemplateController.init(cToolbar, cMain);
        }
        this.mLayout = cLayout;
        return this.mLayout;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mPause = false;
        this.mStop = false;
        this.mResume = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mPause = true;
        this.mResume = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mStop = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mDestroy = true;
    }

    public boolean isIDestroy() {
        return mDestroy;
    }

    public boolean isIPause() {
        return mPause;
    }

    public boolean isIStop() {
        return mStop;
    }

    public boolean isIResume() {
        return mResume;
    }
}
