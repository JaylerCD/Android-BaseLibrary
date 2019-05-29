package com.rxokhttplibrary.error;

/**
 *
 * @author tim
 * @date 2019/4/2
 */

public class HttpException extends BaseException {

    private int errorCode;
    private String errorMsg;

    public HttpException(int errorCode) {
        this.errorCode = errorCode;
    }

    public HttpException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }


}
