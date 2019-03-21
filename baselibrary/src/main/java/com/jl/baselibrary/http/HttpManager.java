package com.jl.baselibrary.http;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpManager {

    // 网络访问引擎
    private static HttpEngine mHttpEngine;

    // 标识（作用:取消网络请求）
    private Object mTag;

    // 接口地址
    private String mUrl;

    // 请求参数
    private Map<String, Object> mParams;

    // 请求方式
    private HttpMethod mHttpMethod = HttpMethod.GET;

    // 请求ContentType
    private String mContentType = "application/json";

    // 是否缓存
    private boolean mCache = false;

    // 文件地址
    private String mFilePath;

    // 文件名字
    private String mFileName;

    // 可以在Application中配置HttpEngine
    public static void initEngine(HttpEngine httpEngine){
        mHttpEngine = httpEngine;
    }

    // 切换引擎
    public HttpManager exchangeEngine(HttpEngine httpEngine){
        this.mHttpEngine = httpEngine;
        return this;
    }

    public static HttpManager getInstance(){
        return new HttpManager();
    }


    private HttpManager(){
        this.mParams = new HashMap<>();
    }


    public HttpManager tag(Object tag){
        this.mTag = tag;
        return this;
    }

    public HttpManager url(String url){
        this.mUrl = url;
        return this;
    }

    public HttpManager addParam(String key, Object value){
        mParams.put(key, value);
        return this;
    }

    public HttpManager addParams(Map<String, Object> params){
        mParams.putAll(params);
        return this;
    }

    public HttpManager get(){
        this.mHttpMethod = HttpMethod.GET;
        return this;
    }

    public HttpManager post(){
        this.mHttpMethod = HttpMethod.POST;
        return this;
    }

    public HttpManager upload(String filePath, String fileName){
        this.mFilePath = filePath;
        this.mFileName = fileName;
        return this;
    }

    public HttpManager contentType(String contentType){
        this.mContentType = contentType;
        this.mHttpMethod = HttpMethod.POST;
        return this;
    }

    public HttpManager cache(boolean cache){
        this.mCache = cache;
        return this;
    }

    public void execute(){
        execute(null);
    }

    //执行网络请求
    public void execute(HttpEngineCallback httpEngineCallback){

        if (TextUtils.isEmpty(mUrl)) {
            throw new NullPointerException("访问路径不能为空");
        }

        if (mHttpMethod == HttpMethod.GET) {
            get(mUrl, mParams, httpEngineCallback);
            return;
        }
        if (mHttpMethod == HttpMethod.POST) {
            post(mUrl, mParams, httpEngineCallback);
            return;
        }
        if (!TextUtils.isEmpty(mContentType) && mContentType.toLowerCase().contains("multipart")) {
            MultipartBody multipartBody = new MultipartBody();
            multipartBody.setContentType(mContentType);
            multipartBody.setParams(mParams);
            upload(mUrl, multipartBody, mFilePath, mFileName, httpEngineCallback);
            return;
        }else {
            get(mUrl, mParams, httpEngineCallback);
        }
    }

    private void get(String url, Map<String, Object> params, HttpEngineCallback httpEngineCallback) {
        mHttpEngine.get(url, params, mContentType, mTag, mCache , httpEngineCallback);
    }

    private void post(String url, Map<String, Object> params, HttpEngineCallback httpEngineCallback) {
        mHttpEngine.post(url, params, mContentType, mTag, mCache , httpEngineCallback);
    }

    private void upload(String url, MultipartBody body, String filePath, String fileName, HttpEngineCallback httpEngineCallback){
        mHttpEngine.upload(url, body, filePath, fileName, mTag, httpEngineCallback);
    }

    public void cancel(Object tag){
        mHttpEngine.cancel(tag);
    }

    public void cancelAll(){
        mHttpEngine.cancelAll();
    }


}
