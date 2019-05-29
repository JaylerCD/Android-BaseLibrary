package com.tencentmap;

/**
 * Created by JayLer on 2019/5/15.
 */
public class BaseMapEntity {

    private int status;
    private String message;
    private int count;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
