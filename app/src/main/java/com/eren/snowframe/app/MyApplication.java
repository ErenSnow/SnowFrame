package com.eren.snowframe.app;


import androidx.multidex.MultiDexApplication;

import com.eren.snowframe.utils.ActivityCollector;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author Eren
 * <p>
 * App初始化
 */
public class MyApplication extends MultiDexApplication {

    private static MyApplication myApplication;

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        initLogger();
        initUM();
//        setRxJavaErrorHandler();
    }

    /**
     * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收，需要提前设置ErrorHandler
     * 详情：http://engineering.rallyhealth.com/mobile/rxjava/reactive/2017/03/15/migrating-to-rxjava-2.html#Error Handling
     */
    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Logger.d("RxJavaError" + throwable.getMessage(), "throw test");
            }
        });
    }

    /**
     * 初始化日志打印框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                //（可选）是否显示线程信息。 默认值为true
                .showThreadInfo(true)
                //（可选）要显示的方法行数。 默认2
                .methodCount(2)
                //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .methodOffset(7)
                //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .logStrategy(new LogcatLogStrategy())
                //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .tag("AMD")
                .build();
        // 开启
        Logger.addLogAdapter(new AndroidLogAdapter());
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
//            @Override
//            public boolean isLoggable(int priority, String tag) {
//                //DEBUG模式下不打印LOG
//                return BuildConfig.DEBUG;
//            }
//        });
    }

    /**
     * 初始化友盟
     */
    private void initUM() {
        //友盟调试
//        UMConfigure.setLogEnabled(true);
//
//        // 初始化友盟
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
//        // 选用AUTO页面采集模式
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
//        // 支持在子进程中统计自定义事件
//        UMConfigure.setProcessEvent(true);
//
//        // 微信
//        PlatformConfig.setWeixin(Constant.WeixinAppId, Constant.WeixinSecret);
//        // QQ
//        PlatformConfig.setQQZone(Constant.QQAppId, Constant.QQSecret);
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        ActivityCollector.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
