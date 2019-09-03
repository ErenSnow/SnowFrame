package com.eren.snowframe.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * 二次封装 Glide
 */
public class GlideUtils {

    // 常用
    public static void load(Context context,
                            String url,
                            ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    // 添加options
    public static void load(Context context,
                            String url,
                            ImageView imageView,
                            RequestOptions options) {
        if (options != null) {
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .into(imageView);
        }
    }
}
