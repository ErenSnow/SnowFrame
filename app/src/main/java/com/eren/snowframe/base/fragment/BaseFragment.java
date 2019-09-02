package com.eren.snowframe.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eren.snowframe.R;
import com.eren.snowframe.base.bean.EventBean;
import com.eren.snowframe.base.mvp.IBaseView;
import com.eren.snowframe.utils.EventBusUtils;
import com.eren.snowframe.utils.PerfectClickListener;
import com.eren.snowframe.utils.ToastUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author Eren
 * <p>
 * Fragment基类
 */
public abstract class BaseFragment extends SupportFragment implements IBaseView {
    @BindView(R.id.error_container)
    LinearLayout errorContainer;
    @BindView(R.id.avi_base_fragment)
    AVLoadingIndicatorView aviBaseFragment;

    /**
     * 上下文
     */
    Context mContext;
    /**
     * 数据绑定
     */
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 初始化布局
        View rootView = inflater.inflate(R.layout.fragment_base, container, false);
        FrameLayout mContainer = rootView.findViewById(R.id.container);
        mContainer.addView(getLayoutInflater().inflate(setLayoutId(), null));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.setLayoutParams(params);
        // 初始化ButterKnife
        unbinder = ButterKnife.bind(this, rootView);
        // 注册EventBus
        if (regEvent()) {
            EventBusUtils.register(this);
        }
        return rootView;
    }

    /**
     * 具体的布局
     *
     * @return 布局ID
     */
    protected abstract int setLayoutId();

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    private boolean regEvent() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 点击加载失败布局
        errorContainer.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
//                showLoading();
                onRefresh();
            }
        });
        // 子类自定义布局
        initView(savedInstanceState);
        // 初始化网络加载
        initData(savedInstanceState);
    }

    /**
     * 加载失败后点击后的操作
     */
    private void onRefresh() {
    }

    /**
     * 初始化布局
     *
     * @param savedInstanceState 异常关闭数据保存
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化网络加载
     */
    private void initData(Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销ButterKnife
        if (unbinder != null) {
            unbinder.unbind();
        }
        // 注销EventBus
        if (regEvent()) {
            EventBusUtils.unregister(this);
        }
    }

    protected View getView(int id) {
        if (getView() != null) {
            return getView().findViewById(id);
        }
        return null;
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(EventBean eventBean) {
        if (eventBean != null) {
            receiveStickyEvent(eventBean);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param eventBean 事件
     */
    protected void receiveEvent(EventBean eventBean) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param eventBean 粘性事件
     */
    protected void receiveStickyEvent(EventBean eventBean) {

    }

    /**
     * 跳转到Fragment
     *
     * @param supportFragment 要跳转的Fragment
     */
    public void startFragment(@NonNull SupportFragment supportFragment) {
        start(supportFragment);
    }

    /**
     * 跳转到Fragment返回数据
     *
     * @param supportFragment 要跳转的Fragment
     * @param requestCode     请求码
     */
    public void startFragmentForResult(@NonNull SupportFragment supportFragment, int
            requestCode) {
        startForResult(supportFragment, requestCode);
    }

    /**
     * 跳转到Activity
     *
     * @param clz 要跳转的Activity
     */
    public void startActivity(@NonNull Class<?> clz) {
        startActivity(new Intent(mContext, clz));
    }

    /**
     * 跳转到Activity并携带参数
     *
     * @param clz    要跳转的Activity
     * @param bundle bundle数据
     */
    public void startActivity(@NonNull Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到Activity返回数据
     *
     * @param clz         要跳转的Activity
     * @param bundle      bundle数据
     * @param requestCode 请求码
     */
    public void startActivityForResult(@NonNull Class<?> clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg, 1000);
    }
}