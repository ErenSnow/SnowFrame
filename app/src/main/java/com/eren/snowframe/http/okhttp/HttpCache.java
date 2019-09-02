package com.eren.snowframe.http.okhttp;

import com.eren.snowframe.app.MyApplication;

import java.io.File;

import okhttp3.Cache;

/**
 * @author Eren
 * <p>
 * 緩存
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 1024 * 1024 * 100;

    public static Cache getCache() {
        return new Cache(new File(MyApplication.getInstance().getCacheDir(), "cache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
