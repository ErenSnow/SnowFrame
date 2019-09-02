package com.eren.snowframe.base.mvp;


/**
 * @author Eren
 * <p>
 * View基类接口
 */
public interface IBaseMvpView {
    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void dismissLoading();

    /**
     * 空数据
     *
     * @param tag TAG
     */
    void onEmpty(Object tag);

    /**
     * 错误数据
     *
     * @param code     错误码
     * @param errorMsg 错误信息
     */
    void onError(int code, String errorMsg);

    /**
     * 开启下拉刷新
     */
    void startRefresh();

    /**
     * 关闭下拉刷新
     */
    void stopRefresh();
//
//    /**
//     * 开启加载更多
//     */
//    void startLoadingMore();
//
//    /**
//     * 关闭加载更多
//     */
//    void stopLoadingMore();
}
