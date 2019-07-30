package com.hy.frame.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hy.iframe.R;
import com.hy.frame.common.ILoadingDialog;

/**
 * title 无
 * author heyan
 * time 19-7-12 下午3:38
 * desc 无
 */
public class LoadingDialog extends com.hy.frame.ui.dialog.BaseDialog implements ILoadingDialog {
    private TextView txtMessage = null;
    private CharSequence message;

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dlg_loading;
    }

    @Override
    public void initWindow() {
        windowDeploy(0, 0, Gravity.CENTER);
    }

    @Override
    public void initView() {
        this.txtMessage = findViewById(R.id.loading_txtMessage);
    }

    @Override
    public void initData() {
        if (this.txtMessage != null && message != null)
            this.txtMessage.setText(message);
    }

    @Override
    public void onViewClick(View v) {

    }

    @Override
    public void showDialog(int strId) {
        showDialog(getCurContext().getString(strId));
    }

    @Override
    public void showDialog(CharSequence msg) {
        this.message = msg;
        if (this.txtMessage != null && message != null)
            this.txtMessage.setText(message);
        this.show();
    }

    @Override
    public void hideDialog() {
        this.dismiss();
    }

    @Override
    public void cancelDialog() {
        this.cancel();
    }
}
