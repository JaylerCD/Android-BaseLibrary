package com.jl.baselibrary.http;

import java.util.Map;

/**
 * 网络访问引擎
 */
public interface HttpEngine {

    // post
    void post(String url, Map<String, Object> params, String contentType, Object tag, boolean cache, HttpEngineCallback httpEngineCallback);

    // get
    void get(String url, Map<String, Object> params, String contentType, Object tag, boolean cache, HttpEngineCallback httpEngineCallback);

    // 下载文件
    void download(String url, String filePath, String fileName, Object tag, HttpEngineCallback httpEngineCallback);

    // 上传文件
    void upload(String url, MultipartBody body, String filePath, String fileName, Object tag, HttpEngineCallback httpEngineCallback);

    // 取消请求
    void cancel(Object tag);

    // 取消所有请求
    void cancelAll();
}
