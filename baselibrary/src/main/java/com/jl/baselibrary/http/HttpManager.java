package com.jl.baselibrary.http;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpManager {

    // 网络访问引擎
    private HttpEngine mHttpEngine;

    // 上下文
    private Context mContext;

    // 接口地址
    private String mUrl;

    // 请求参数
    private Map<String, Object> mParams;

    // 请求方式
    private HttpMethod mHttpMethod = HttpMethod.GET;

    // 是否缓存
    private boolean mCache = false;

    // 切换引擎
    public HttpManager exchangeEngine(HttpEngine httpEngine){
        this.mHttpEngine = httpEngine;
        return this;
    }


    private HttpManager(Context context){
        this.mContext = context;
        this.mParams = new HashMap<>();
    }

    public static HttpManager with(Context context){
        return new HttpManager(context);
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
    }

    private void get(String url, Map<String, Object> params, HttpEngineCallback httpEngineCallback) {
        mHttpEngine.get(mContext, url, params, httpEngineCallback, mCache);
    }

    private void post(String url, Map<String, Object> params, HttpEngineCallback httpEngineCallback) {
        mHttpEngine.post(mContext, url, params, httpEngineCallback, mCache);
    }


}
