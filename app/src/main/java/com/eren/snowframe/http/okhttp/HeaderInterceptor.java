package com.eren.snowframe.http.okhttp;

import com.eren.snowframe.app.MyApplication;
import com.eren.snowframe.utils.SpUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Eren
 * <p>
 * 增加头部信息
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json");
        String[] cookies = ((String) SpUtils.getParam(MyApplication.getInstance(), "cookies", "")).split("-");
        for (String cookie : cookies) {
            builder.addHeader("Cookie", cookie);
        }

        Request request = builder.build();
        return chain.proceed(request);
    }
}