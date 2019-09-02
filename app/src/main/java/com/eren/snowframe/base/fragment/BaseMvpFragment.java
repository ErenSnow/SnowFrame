package com.eren.snowframe.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
 * Fragment MVP基类
 */
public abstract class BaseMvpFragment<P extends BasePresenter, M extends IBaseModel> extends BaseFragment implements IBaseMvpView {

    public P mPresenter;
    public M mModel;

    /**
     * 内容布局
     */
    private FrameLayout mContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 显示具体的布局界面，由子类显示
        if (container != null) {
            mContainer = container.findViewById(R.id.container);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化Mvp
        initMvp();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    /**
     * 初始化网络加载
     */
    protected abstract void initData();

    /**
     * 初始化P、M
     */
    private void initMvp() {
        if (this instanceof IBaseView &&
                this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            mPresenter = InstanceUtil.getInstance(this, 0);
            mModel = InstanceUtil.getInstance(this, 1);
        }
        if (mPresenter != null) {
            mPresenter.setContext(mContext);
        }
        // 初始化Presenter
        initPresenter();
    }

    /**
     * 初始化Presenter
     */
    protected abstract void initPresenter();

    @Override
    public void onDestroy() {
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
//        MobclickAgent.reportError(mContext, errorMsg);
    }


    @Override
    public void showLoading() {
        aviBaseFragment.smoothToShow();
    }

    @Override
    public void dismissLoading() {
        aviBaseFragment.smoothToHide();
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
}
