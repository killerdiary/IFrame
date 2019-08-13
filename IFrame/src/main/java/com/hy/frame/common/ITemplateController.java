package com.hy.frame.common;

import android.view.View;
import android.view.ViewGroup;

/**
 * title ITemplateController
 * author heyan
 * time 19-7-10 下午12:14
 * desc 无
 */
public interface ITemplateController {
    /**
     * 初始化
     *
     * @param cToolbar 标题栏
     * @param cMain    主布局
     */
    void init(ViewGroup cToolbar, ViewGroup cMain);

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
}
