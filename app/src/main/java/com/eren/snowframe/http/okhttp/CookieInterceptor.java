package com.eren.snowframe.http.okhttp;

import com.eren.snowframe.app.MyApplication;
import com.eren.snowframe.utils.SpUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author Eren
 * <p>
 * Cache拦截器
 */
public class CookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String cookie : originalResponse.headers("Set-Cookie")) {
                sb.append(cookie).append("-");
            }
            SpUtils.setParam(MyApplication.getInstance(), "cookies", sb.length() > 0 ? sb.subSequence(0, sb.length() - 1).toString() : "");
        }
        return originalResponse;
    }
}
