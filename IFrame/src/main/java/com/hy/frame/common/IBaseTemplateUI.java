package com.hy.frame.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * title 供 Acitivity和Fragment使用
 * author heyan
 * time 19-7-10 下午12:13
 * desc 无
 */
public interface IBaseTemplateUI {
    /**
     * 是否启用唯一布局，否者使用公有模板[ITemplateControl]
     */
    boolean isSingleLayout();

    /**
     * LayoutId 默认值为0
     */
    int getBaseLayoutId();

    /**
     * 获取模板[ITemplateControl]
     */
    ITemplateController getTemplateController();

    /**
     * 用于子类扩展
     *
     * @return ITemplateController
     */
    ITemplateController buildTemplateController();

    /**
     * 获取图片加载器
     */
    IImageLoader getImageLoader();

    /**
     * 设置图片加载器
     *
     * @return IImageLoader 用于子类拓展
     */
    IImageLoader buildImageLoader();

    /**
     * 是否开启透明状态栏
     */
    boolean isTranslucentStatus();

    /**
     * 状态栏高度
     */
    int getStatusBarHeight();

    /**
     * 当前Application
     *
     * @return Application
     */
    IBaseApplication getCurApp();

    /**
     * 当前 Activity
     *
     * @return Activity
     */
    Activity getCurActivity();

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