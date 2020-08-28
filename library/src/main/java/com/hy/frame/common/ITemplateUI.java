package com.hy.frame.common;

import android.view.View;
import android.view.ViewGroup;

/**
 * title ITemplateUI
 * author heyan
 * time 19-7-10 下午12:14
 * desc 无
 */
public interface ITemplateUI {
    /**
     * 是否启用唯一布局，否者使用公有模板[ITemplateControl]
     */
    boolean isSingleLayout();

    /**
     * LayoutId 默认值为0
     */
    int getBaseLayoutId();

    /**
     * LayoutId 默认值为0
     */
    int getLayoutId();

    /**
     * Layout View 不为空时优先使用
     */
    View getLayoutView();

    /**
     * 根布局
     */
    View getRootLayout();


    /**
     * 是否开启透明状态栏
     */
    boolean isTranslucentStatus();

    /**
     * 状态栏高度
     */
    int getStatusBarHeight();

    /**
     * 初始化基本布局
     */
    void initLayout();

    /**
     * 初始化布局
     */
    void initView();

    /**
     * 初始化
     */
    ITemplateUI build();

    /**
     * inflate
     */
    View inflate(int resource, ViewGroup root);

    /**
     * 是否是快速点击
     */
    boolean isFastClick();

    /**
     * 获取 控件
     *
     * @param id 行布局中某个组件的id
     */
    <T extends View> T findViewById(int id);

    /**
     * 获取 控件
     *
     * @param id     行布局中某个组件的id
     * @param parent parent
     */
    <T extends View> T findViewById(int id, View parent);

    /**
     * 获取并绑定点击
     *
     * @param id 行布局中某个组件的id
     */
    <T extends View> T setOnClickListener(int id);

    /**
     * 获取并绑定点击
     *
     * @param id     行布局中某个组件的id
     * @param parent parent
     */
    <T extends View> T setOnClickListener(int id, View parent);

    /**
     * 设置标题
     *
     * @param strId 文本资源ID
     */
    void setTitle(int strId);

    /**
     * 设置标题
     *
     * @param titleStr 标题
     */
    void setTitle(CharSequence titleStr);


    /**
     * 设置头部左侧图标
     *
     * @param drawLeft 图片资源ID
     */
    void setHeaderLeft(int drawLeft);

    /**
     * 设置头部左侧文本
     *
     * @param leftStr 文本
     */
    void setHeaderLeftTxt(CharSequence leftStr);

    /**
     * 设置头部右侧图标
     *
     * @param drawRight 图片资源ID
     */
    void setHeaderRight(int drawRight);

    /**
     * 设置头部右侧文本
     *
     * @param rightStr 文本
     */
    void setHeaderRightTxt(CharSequence rightStr);

    /**
     * 添加头部右侧图标
     *
     * @param drawRight 图片资源ID
     * @param id        View ID
     */
    void addHeaderRight(int drawRight, int id);

    /**
     * 添加头部右侧网络图标
     *
     * @param rightPath 图片资源路径
     * @param id        View ID
     */
    void addHeaderRightPath(CharSequence rightPath, int id);

    /**
     * 显示提示
     *
     * @param msg 描述
     */
    void showToast(CharSequence msg);

    /**
     * 初始化LoadingUI控制器
     *
     * @return 是否成功
     */
    boolean initLoading();

    /**
     * 设置LoadingUI控制器
     *
     * @param iLoadingUI ILoadingUI
     */
    void setLoading(ILoadingUI iLoadingUI);

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 显示加载中
     *
     * @param strId 文本资源ID
     */
    void showLoading(int strId);

    /**
     * 显示加载中
     *
     * @param msg 描述
     */
    void showLoading(CharSequence msg);

    /**
     * 显示没有数据或错误页
     *
     * @param msg 描述
     */
    void showNoData(CharSequence msg);

    /**
     * 显示没有数据或错误页
     *
     * @param strId  文本资源ID
     * @param drawId 图片资源ID
     */
    void showNoData(int strId, int drawId);

    /**
     * 显示没有数据或错误页
     *
     * @param msg    描述
     * @param drawId 图片资源ID
     */
    void showNoData(CharSequence msg, int drawId);

    /**
     * 显示内容View
     */
    void showCView();

    /**
     * 设置加载对话框
     *
     * @param iLoading 自定义对话框
     */
    void setLoadingDialog(ILoadingDialog iLoading);

    /**
     * 显示加载中
     */
    void showLoadingDialog();

    /**
     * 显示加载中
     *
     * @param strId 文本资源ID
     */
    void showLoadingDialog(int strId);

    /**
     * 显示加载中
     *
     * @param msg 描述
     */
    void showLoadingDialog(CharSequence msg);

    /**
     * 隐藏加载中
     */
    void hideLoadingDialog();


    /**
     * 隐藏头部
     */
    void hideHeader();

    /**
     * 头部ViewGroup
     */
    ViewGroup getHeader();

    /**
     * 头部 标题
     */
    View getHeaderTitle();

    /**
     * 头部 左
     */
    View getHeaderLeft();

    /**
     * 头部 右
     */
    View getHeaderRight();

    /**
     * 主布局
     */
    ViewGroup getMainView();

    /**
     * 资源释放
     */
    void onDestroy();

    /**
     * 控件点击事件
     */
    void onViewClick(View v);
}
