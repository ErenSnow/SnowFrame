package com.eren.snowframe.widget.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.eren.snowframe.R;
import com.eren.snowframe.widget.status.StatusBarTextUtil;
import com.eren.snowframe.widget.status.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * @author Eren
 * <p>
 * Web界面
 */
public class WebActivity extends AppCompatActivity implements IWebPageView {
    /**
     * 进度条最大值
     */
    public static final int PROGRESSBAR = 100;

    @BindView(R.id.pb_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.h5_frame_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.tv_web_title)
    TextView tvWebTitle;
    @BindView(R.id.tool_bar_web)
    Toolbar toolBarWeb;

    /**
     * H5地址
     */
    private String mUrl;
    /**
     * H5标题
     */
    private String mTitle;

    private WebView webView;

    @Override
    public void hindProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWebView() {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hindWebView() {
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startProgress(int newProgress) {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(newProgress);
        if (newProgress == PROGRESSBAR) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        getParams();
        initTitle();
        //初始化WebView
        initWebView();
        //加载网页
        webView.loadUrl(mUrl);
        //WebView与JS交互
        webView.addJavascriptInterface(new JsInterface(), "JSCallJava");
    }

    private void getParams() {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra("title");
            mUrl = getIntent().getStringExtra("url");
        }
    }

    private void initTitle() {
        setSupportActionBar(toolBarWeb);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // 返回键的点击事件
        toolBarWeb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    onBackPressed();
                }
            }
        });
        // 设置布局向下移动，防止嵌入到状态栏中
        StatusBarUtil.setMargin(this, toolBarWeb);
        // 设置状态栏字体颜色为深色（黑色）
        StatusBarTextUtil.setAndroidNativeLightStatusBar(this, true);
        tvWebTitle.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvWebTitle.setSelected(true);
            }
        }, 1900);
        tvWebTitle.setText(mTitle);
    }

    /**
     * 初始化WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mProgressBar.setVisibility(View.VISIBLE);
        webView = new WebView(getApplicationContext());
        mFrameLayout.addView(webView);

        //在webView里面打开网页
        webView.setWebViewClient(new MyWebViewClient(this));
        //允许在WebView里面弹出JS的窗体
        webView.setWebChromeClient(new MyWebChromeClient(this));

        //声明WebSettings子类
        WebSettings ws = webView.getSettings();

        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }

        // WebView从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)
        ws.setTextZoom(100);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
            mProgressBar.clearAnimation();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
                //退出网页
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        }
        return false;
    }

    private class JsInterface {
    }
}
