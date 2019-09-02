package com.eren.snowframe.widget.web;


/**
 * @author Eren
 * <p>
 * 进度条接口
 */
public interface IWebPageView {

    /**
     * 隐藏进度条
     */
    void hindProgressBar();

    /**
     * 显示WebView
     */
    void showWebView();

    /**
     * 隐藏WebView
     */
    void hindWebView();

    /**
     * 进度条变化时调用
     *
     * @param newProgress
     */
    void startProgress(int newProgress);
}
