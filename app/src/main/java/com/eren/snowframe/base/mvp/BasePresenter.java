package com.eren.snowframe.base.mvp;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;


/**
 * * Presenter基类
 *
 * @param <M> M
 * @param <V> V
 * @author Eren
 */
public abstract class BasePresenter<M, V> {

    protected M mModel;
    protected V mView;
    protected Context mContext;
    protected CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    public void setMV(M m, V v) {
        mModel = m;
        mView = v;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void onDetached() {
        mCompositeSubscription.dispose();
    }
}