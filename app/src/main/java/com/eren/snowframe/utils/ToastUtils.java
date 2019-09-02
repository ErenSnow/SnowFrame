package com.eren.snowframe.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.eren.snowframe.app.MyApplication;

/**
 * @author Eren
 * <p>
 * toast工具类封装
 */
public class ToastUtils {

    private static Toast mToast = null;

    /**
     * 显示一个toast提示
     *
     * @param resourceId toast字符串资源id
     */
    public static void showToast(int resourceId) {
        showToast(ResourcesUtils.getString(resourceId));
    }

    /**
     * 显示一个toast提示
     *
     * @param text toast字符串
     */
    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个toast提示
     *
     * @param text     toast字符串
     * @param duration toast显示时间
     */
    public static void showToast(String text, int duration) {
        showToast(MyApplication.getInstance(), text, duration);
    }

    /**
     * 显示一个toast提示
     *
     * @param context  context 上下文对象
     * @param text     toast字符串
     * @param duration toast显示时间
     */
    public static void showToast(final Context context, final String text, final int duration) {
        try {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, duration);
            } else {
                mToast.setText(text);
                mToast.setDuration(duration);
            }
            mToast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, text, duration).show();
            Looper.loop();
        }

    }
}
