package com.eren.snowframe.presenter;

import com.eren.snowframe.base.BaseResponseBean;
import com.eren.snowframe.bean.BannerBean;
import com.eren.snowframe.contract.TestContract;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Eren
 * <p>
 * 测试P层
 */
public class TestPresenter extends TestContract.Presenter {

    @Override
    public void getBannerList() {
        mModel.getBannerList()
                //请求数据的事件发生在io线程
                .subscribeOn(Schedulers.io())
                //请求完成后在主线程更显UI
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doAfterTerminate(() -> mView.dismissLoading())
                //订阅
                .subscribe(new Observer<BaseResponseBean<List<BannerBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseResponseBean<List<BannerBean>> bannerBean) {
                        if (bannerBean != null) {
                            if (bannerBean.getErrorCode() == 0) {
                                mView.bannerList(bannerBean.getData());
                            } else {
                                mView.onError(bannerBean.getErrorCode(), bannerBean.getErrorMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(250, e.getMessage());
                        mView.dismissLoading();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}