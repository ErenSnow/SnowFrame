package com.eren.snowframe.base.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.eren.snowframe.R;
import com.eren.snowframe.base.bean.EventBean;
import com.eren.snowframe.base.mvp.IBaseView;
import com.eren.snowframe.utils.ActivityCollector;
import com.eren.snowframe.utils.EventBusUtils;
import com.eren.snowframe.utils.PerfectClickListener;
import com.eren.snowframe.utils.PermissionUtils;
import com.eren.snowframe.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author Eren
 * <p>
 * Activity基类
 */
public abstract class BaseActivity extends SupportActivity implements IBaseView {
    @BindView(R.id.tool_bar)
    public Toolbar toolBar;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    @BindView(R.id.error_container)
    LinearLayout errorContainer;
    @BindView(R.id.avi_base_activity)
    AVLoadingIndicatorView aviBaseActivity;

    /**
     * 数据绑定
     */
    private Unbinder unbinder;
    private ActionBar mActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取传递参数
        getParams(getIntent().getExtras());
        // 注册EventBus
        if (regEvent()) {
            EventBusUtils.register(this);
        }
        // 当前所在类
        Logger.i("当前Activity:" + getClass().getSimpleName());
        // 将当前正在创建的活动添加到活动管理期里
        ActivityCollector.addActivity(this);
        // 初始化布局
        initUI();
    }

    /**
     * 获取传递参数
     *
     * @param extras 传递参数
     */
    protected void getParams(Bundle extras) {
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }

    /**
     * 初始化布局
     */
    private void initUI() {
        setContentView(R.layout.activity_base);
        // 显示具体的布局界面，由子类显示
        FrameLayout mContainer = findViewById(R.id.container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.setLayoutParams(params);
        mContainer.addView(View.inflate(this, setLayoutId(), null));
        // 初始化ButterKnife
        unbinder = ButterKnife.bind(this);
        // 点击加载失败布局
        errorContainer.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
//                showLoading();
                onRefresh();
            }
        });
    }

    /**
     * 具体的布局
     *
     * @return 布局ID
     */
    protected abstract int setLayoutId();

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销ButterKnife
        if (unbinder != null) {
            unbinder.unbind();
        }
        // 注销EventBus
        if (regEvent()) {
            EventBusUtils.unregister(this);
        }
        // 将一个马上要销毁的活动从管理器里移除
        ActivityCollector.removeActivity(this);
    }

    /**
     * 对Toolbar进行设置
     */
    protected void setToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                // 去除默认Title显示
                mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setDisplayHomeAsUpEnabled(true);
                // 设置返回键图片
                mActionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            }
            // 返回键的点击事件
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else {
                        onBackPressedSupport();
                    }
                }
            });
        }
    }

    /**
     * 去掉Toolbar返回键
     */
    protected void setToolBarGone() {
        if (mActionBar != null) {
            // 去除默认Title显示
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setDisplayHomeAsUpEnabled(false);
            // 设置返回键图片
            mActionBar.setHomeAsUpIndicator(null);
        }
    }

    /**
     * 隐藏ToolBar
     */
    protected void hideToolBar() {
        toolBar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    /**
     * 初始化布局
     */
    protected abstract void initView();

    @Override
    public void setTitle(CharSequence title) {
        if (tvToolbar != null) {
            tvToolbar.setText(title);
        }
        // 初始化Toolbar
        setToolbar();
    }

    /**
     * 默认Toolbar设置
     */
    protected void setToolbar() {
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                // 去除默认Title显示
                mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setDisplayHomeAsUpEnabled(true);
                // 设置返回键图片
                mActionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            }
            // 返回键的点击事件
            toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else {
                        onBackPressedSupport();
                    }
                }
            });
        }
    }

    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventBean eventBean) {
        if (eventBean != null) {
            receiveEvent(eventBean);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param eventBean 事件
     */
    protected void receiveEvent(EventBean eventBean) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(EventBean eventBean) {
        if (eventBean != null) {
            receiveStickyEvent(eventBean);
        }
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param eventBean 粘性事件
     */
    protected void receiveStickyEvent(EventBean eventBean) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 页面跳转
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 页面跳转
     *
     * @param clz    要跳转的Activity
     * @param intent intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz    要跳转的Activity
     * @param bundle bundle数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundle数据
     * @param requestCode requestCode
     */
    public void startActivityForResult(Class<?> clz, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }
}
