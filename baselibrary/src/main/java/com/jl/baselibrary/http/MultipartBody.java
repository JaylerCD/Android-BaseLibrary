package com.jl.baselibrary.http;

import java.util.Map;

public class MultipartBody {

    // 请求参数
    private Map<String, Object> mParams;

    //ContentType
    private String mContentType;


    public MultipartBody() {
    }

    public MultipartBody(String contentType) {
        this.mContentType = contentType;
    }


    public Map<String, Object> getParams() {
        return mParams;
    }

    public void setParams(Map<String, Object> params) {
        this.mParams = params;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType) {
        this.mContentType = contentType;
    }
}
