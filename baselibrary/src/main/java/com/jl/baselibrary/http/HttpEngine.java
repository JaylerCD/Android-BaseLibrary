package com.jl.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * 网络访问引擎
 */
public interface HttpEngine {

    //post
    void post(Context context, String url, Map<String, Object> params, HttpEngineCallback httpEngineCallback, boolean cache);


    //get
    void get(Context context, String url, Map<String, Object> params, HttpEngineCallback httpEngineCallback, boolean cache);

    //...
}
