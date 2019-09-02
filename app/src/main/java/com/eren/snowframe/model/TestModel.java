package com.eren.snowframe.model;

import com.eren.snowframe.base.BaseResponseBean;
import com.eren.snowframe.bean.BannerBean;
import com.eren.snowframe.contract.TestContract;
import com.eren.snowframe.http.RetrofitHttp;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Eren
 * <p>
 * 测试M层
 */
public class TestModel implements TestContract.Model {
    @Override
    public Observable<BaseResponseBean<List<BannerBean>>> getBannerList() {
        return RetrofitHttp.getInstance().getApiService().getBannerList();
    }
}