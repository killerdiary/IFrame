package com.hy.frame.common;

/**
 * title ILoadingDialog
 * author heyan
 * time 19-7-10 上午11:55
 * desc 无
 */
public interface ILoadingDialog {

    /**
     * 显示
     *
     * @param strId 描述
     */
    void showDialog(int strId);

    /**
     * 显示
     *
     * @param msg 描述
     */
    void showDialog(CharSequence msg);

    /**
     * 隐藏
     */
    void hideDialog();

    /**
     * 关闭
     */
    void cancelDialog();
}
