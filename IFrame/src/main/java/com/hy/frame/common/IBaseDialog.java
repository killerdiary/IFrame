package com.hy.frame.common;

import android.content.DialogInterface;

/**
 * title 无
 * author heyan
 * time 19-7-10 下午4:09
 * desc 无
 */
public interface IBaseDialog {
    /**
     * 初始化Window，窗口大小和配置
     */
    void initWindow();

    /**
     * 调整窗口大小和位置
     *
     * @param width   宽
     * @param height  高
     * @param gravity 对齐方式
     */
    void windowDeploy(float width, float height, int gravity);

    /**
     * 设置按钮事件监听
     *
     * @param listener 监听
     */
    void setOnClickListener(DialogInterface.OnClickListener listener);
}
