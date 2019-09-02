package com.eren.snowframe.http.okhttp;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * @author Eren
 * <p>
 * okHttp3拦截器
 */
public class LogInterceptor implements Interceptor {

    @Override

    public okhttp3.Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Logger.i("okhttp3 request:" + request.toString());

        okhttp3.Response response = chain.proceed(chain.request());

        okhttp3.MediaType mediaType = response.body().contentType();

        String content = response.body().string();

        Logger.i("okhttp3 response body:" + content);

        return response.newBuilder()

                .body(okhttp3.ResponseBody.create(mediaType, content))

                .build();
    }
}

