package com.eren.snowframe.http;

import com.eren.snowframe.base.BaseResponseBean;
import com.eren.snowframe.bean.BannerBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author Eren
 * <p>
 * Api接口
 */
public interface ApiService {
    /**
     * 测试
     */
    @GET("banner/json")
    Observable<BaseResponseBean<List<BannerBean>>> getBannerList();
}