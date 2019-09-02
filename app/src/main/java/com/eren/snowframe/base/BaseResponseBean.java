package com.eren.snowframe.base;

/**
 * @author Eren
 * <p>
 * 基础response bean
 */
public class BaseResponseBean<T> {

    /**
     * 返回数据
     */
    private T data;
    /**
     * 0 代表执行成功  -1001 代表登录失效，需要重新登录
     */
    private int errorCode;
    /**
     * 返回信息
     */
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
