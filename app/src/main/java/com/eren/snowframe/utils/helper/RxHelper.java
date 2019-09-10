package com.eren.snowframe.utils.helper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {

    /**
     * 统一线程处理
     * <p>
     * 发布事件io线程，接收事件主线程
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {//compose处理线程
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 主线程做操作
     */
    public static void doOnUIThread(UITask uiTask) {
        Observable.just(uiTask)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uiTask1 -> uiTask1.doOnUI());

    }

    /**
     * io线程做操作
     */
    public static void doOnThread(ThreadTask threadTask) {
        Observable.just(threadTask)
                .observeOn(Schedulers.io())
                .subscribe(threadTask1 -> threadTask1.doOnThread());
    }

    /**
     * 延迟操作刷新数据
     *
     * @param time 时间毫秒
     */
    public static void doDelay(long time, Delay delay) {
        Observable.interval(time, TimeUnit.MILLISECONDS)
                .just(delay)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(delay1 -> delay1.doDelay(time));
    }

    public interface ThreadTask {
        void doOnThread();
    }

    public interface UITask {
        void doOnUI();
    }

    public interface Delay {
        void doDelay(long time);
    }
}
