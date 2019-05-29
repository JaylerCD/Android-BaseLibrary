package com.rxokhttplibrary.base;

/**
 * @author tim
 * @date 2019/4/1
 * 该类仅供参考，实际业务返回的固定字段, 根据需求来定义，
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

    private int code = Integer.MIN_VALUE;
    private String message;
    private T data;

    private String cityid;
    private String city;
    private String update_time;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T result) {
        this.data = result;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isSuccess() {
        return code == STATE_SUCCESS || code == STATE_OK;
    }
}
