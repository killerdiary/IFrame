package com.hy.frame.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * title 供 Acitivity和Fragment使用
 * author heyan
 * time 19-7-10 下午12:13
 * desc 无
 */
public interface IAppUI {

    /**
     * 当前Application
     *
     * @return Application
     */
    IApplication getCurApp();

    /**
     * 当前 Activity
     *
     * @return Activity
     */
    Activity getCurActivity();


    Context getCurContext();

    /**
     * 传入参数
     *
     * @return Bundle
     */
    Bundle getArgs();

    /**
     * 拼接多个strId
     *
     * @param ids strId
     * @return String
     */
    String getStrings(int... ids);

    /**
     * 头-左边图标点击
     */
    void onLeftClick();

    /**
     * 头-右边图标点击
     */
    void onRightClick();

    /**
     * 加载View点击，用于加载失败后重试
     */
    void onLoadViewClick();

    /**
     * 启动Activity
     *
     * @param cls Class of Activity
     */
    void startAct(Class<?> cls);

    /**
     * 启动Activity
     *
     * @param cls    Class of Activity
     * @param bundle 参数
     * @param intent Intent
     */
    void startAct(Class<?> cls, Bundle bundle, Intent intent);

    /**
     * 启动Activity
     *
     * @param cls         Class of Activity
     * @param requestCode 请求码 为0时 使用startActivity
     */
    void startActForResult(Class<?> cls, int requestCode);

    /**
     * 启动Activity
     *
     * @param cls         Class of Activity
     * @param requestCode 请求码 为0时 使用startActivity
     * @param bundle      参数
     * @param intent      Intent
     */
    void startActForResult(Class<?> cls, int requestCode, Bundle bundle, Intent intent);

    String ARG_LAST_ACT = "arg_last_act";
    String ARG_BUNDLE = "arg_bundle";
}