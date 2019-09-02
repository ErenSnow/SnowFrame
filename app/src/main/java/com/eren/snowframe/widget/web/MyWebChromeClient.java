package com.eren.snowframe.widget.web;

import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * @author Eren
 * <p>
 * 允许在WebView里面弹出JS的窗体
 */
public class MyWebChromeClient extends WebChromeClient {

    private WebActivity mActivity;
    private IWebPageView mIWebPageView;

    public MyWebChromeClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        this.mActivity = (WebActivity) mIWebPageView;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        mIWebPageView.startProgress(newProgress);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        mIWebPageView.hindWebView();
    }

    @Override
    public void onHideCustomView() {
        mIWebPageView.showWebView();
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
