package com.eren.snowframe.utils;

import com.eren.snowframe.base.bean.EventBean;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Eren
 * <p>
 * 事件工具类
 */
public class EventBusUtils {

    /**
     * 注册事件
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 解除事件
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送普通事件
     */
    public static void sendEvent(EventBean eventBean) {
        EventBus.getDefault().post(eventBean);
    }

    /**
     * 发送粘性事件
     */
    public static void sendStickyEvent(EventBean eventBean) {
        EventBus.getDefault().postSticky(eventBean);
    }

    /**
     * 移除指定的粘性订阅事件
     */
    public static <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyEvent);
        }
    }

    /**
     * 取消事件传送
     */
    public static void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    /**
     * 移除所有的粘性订阅事件
     */
    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }
}
