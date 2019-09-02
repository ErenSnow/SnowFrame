package com.eren.snowframe.base.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.eren.snowframe.R;
import com.eren.snowframe.base.mvp.BasePresenter;
import com.eren.snowframe.base.mvp.IBaseModel;
import com.eren.snowframe.base.mvp.IBaseMvpView;
import com.eren.snowframe.base.mvp.IBaseView;
import com.eren.snowframe.utils.InstanceUtil;
import com.orhanobut.logger.Logger;

import java.lang.reflect.ParameterizedType;

/**
 * @author Eren
 * <p>
 * Activity MVP基类
 */
public abstract class BaseMvpActivity<P extends BasePresenter, M extends IBaseModel> extends BaseActivity implements IBaseMvpView {

    public P mPresenter;
    public M mModel;

    /**
     * 内容布局
     */
    private FrameLayout mContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 显示具体的布局界面，由子类显示
        mContainer = findViewById(R.id.container);
        // 初始化Mvp
        initMvp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    /**
     * 初始化网络加载
     */
    protected abstract void initData();

    /**
     * 初始化MVP
     */
    private void initMvp() {
        if (this instanceof IBaseView &&
                this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            mPresenter = InstanceUtil.getInstance(this, 0);
            mModel = InstanceUtil.getInstance(this, 1);
        }
        if (mPresenter != null) {
            mPresenter.setContext(this);
        }
        // 初始化Presenter
        if (mPresenter != null) {
            mPresenter.setMV(mModel, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销MVP
        detachMvp();
    }

    /**
     * 注销MVP
     */
    private void detachMvp() {
        if (mPresenter != null) {
            mPresenter.onDetached();
        }
    }

    /**
     * 报错上传友盟
     *
     * @param code     错误码
     * @param errorMsg 错误信息
     */
    @Override
    public void onError(int code, String errorMsg) {
        Logger.e(errorMsg);
//        MobclickAgent.reportError(this, errorMsg);
    }

    @Override
    public void showLoading() {
        aviBaseActivity.smoothToShow();
    }

    @Override
    public void dismissLoading() {
        aviBaseActivity.smoothToHide();
    }

    @Override
    public void startRefresh() {

    }

    @Override
    public void stopRefresh() {

    }

    @Override
    public void onEmpty(Object tag) {
        // 隐藏内容
        if (mContainer.getVisibility() != View.GONE) {
            mContainer.setVisibility(View.GONE);
        }
        // 显示错误信息
        if (errorContainer.getVisibility() != View.VISIBLE) {
            errorContainer.setVisibility(View.VISIBLE);
        }
    }

//    /**
//     * 错误返回
//     *
//     * @param code     错误码
//     * @param errorMsg 错误信息
//     */
//    @Override
//    public abstract void onError(int code, String errorMsg);
}
