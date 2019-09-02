package com.eren.snowframe.contract;

import com.eren.snowframe.base.BaseResponseBean;
import com.eren.snowframe.base.mvp.BasePresenter;
import com.eren.snowframe.base.mvp.IBaseModel;
import com.eren.snowframe.base.mvp.IBaseMvpView;
import com.eren.snowframe.bean.BannerBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 测试接口
 *
 * @author Eren
 */
public interface TestContract {
    interface View extends IBaseMvpView {
        /**
         * Banner图
         *
         * @param bannerList
         */
        void bannerList(List<BannerBean> bannerList);
    }

    interface Model extends IBaseModel {
        /**
         * Banner图
         *
         * @return
         */
        Observable<BaseResponseBean<List<BannerBean>>> getBannerList();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        /**
         * Banner图
         */
        public abstract void getBannerList();
    }
}
