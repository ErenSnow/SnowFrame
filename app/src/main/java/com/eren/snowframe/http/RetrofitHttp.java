package com.eren.snowframe.http;

import com.eren.snowframe.app.Constant;
import com.eren.snowframe.http.okhttp.CookieInterceptor;
import com.eren.snowframe.http.okhttp.HeaderInterceptor;
import com.eren.snowframe.http.okhttp.HttpCache;
import com.eren.snowframe.http.okhttp.LogInterceptor;
import com.eren.snowframe.http.okhttp.TrustManager;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Eren
 * <p>
 * 封装Retrofit
 */
public class RetrofitHttp {

    private static RetrofitHttp mRetrofitHttp;
    private static HeaderInterceptor headerInterceptor = new HeaderInterceptor();
    private static CookieInterceptor cookieInterceptor = new CookieInterceptor();
    private Retrofit retrofit;

    private RetrofitHttp() {
    }

    public static RetrofitHttp getInstance() {
        if (mRetrofitHttp == null) {
            synchronized (RetrofitHttp.class) {
                if (mRetrofitHttp == null) {
                    mRetrofitHttp = new RetrofitHttp();
                }
            }
        }
        return mRetrofitHttp;
    }

    /**
     * 调用接口
     *
     * @return 网络接口
     */
    public ApiService getApiService() {
        initRetrofit();
        return retrofit.create(ApiService.class);
    }

    private void initRetrofit() {
        if (retrofit == null) {

            OkHttpClient builder = new OkHttpClient.Builder()
                    // SSL证书
                    .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    // Time out
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    // 增加头部信息
                    .addInterceptor(headerInterceptor)
                    // 设置Cache拦截器
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(cookieInterceptor)
                    .cache(HttpCache.getCache())
                    // 打印日志
                    .addInterceptor(new LogInterceptor())
                    //失败重连
                    .retryOnConnectionFailure(true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.URL_HOST)
                    // 添加自定义转换器
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    // 支持RxJava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder)
                    .build();
        }
    }
}