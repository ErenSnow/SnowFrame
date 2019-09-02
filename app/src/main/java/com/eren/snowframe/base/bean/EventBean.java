package com.eren.snowframe.base.bean;

/**
 * EventBus事件类
 *
 * @param <T>
 * @author Eren
 */
public class EventBean<T> {

    private String action;
    private T data;

    public EventBean(String action) {
        this.action = action;
    }

    public EventBean(String action, T data) {
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action == null ? "" : action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
