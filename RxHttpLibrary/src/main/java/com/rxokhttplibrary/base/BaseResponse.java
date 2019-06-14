package com.rxokhttplibrary.base;

/**
 * 该类仅供参考，也可以继承使用
 */
public class BaseResponse<T> {


    public static final int STATE_IGNORE = -1;//忽略的错误
    public static final int STATE_SUCCESS = 0;//成功
    public static final int STATE_OK = 200;//成功
    public static final int STATE_SERVICE_FAILED = 500;//服务器异常

    public static final int STATE_TOKEN_EXPIRE = 403;//token过期
    public static final int STATE_TOKEN_30004_EXPIRE = 30004;//token过期
    public static final int STATE_SECRET_KEY_EXPIRE = 70008;//secretKey过期
    public static final int STATE_TOKEN_15010_EXPIRE = 15010;//token过期

    private T data;


    public T getData() {
        return data;
    }

    public void setData(T result) {
        this.data = result;
    }

}
