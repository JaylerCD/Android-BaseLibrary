package com.baselibrary.sample.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.baselibrary.sample.utils.Utils;
import com.jl.baselibrary.http.HttpEngineCallback;
import com.jl.baselibrary.http.HttpEngine;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpEngine implements HttpEngine {

    private static OkHttpClient mOkHttpClient;

    private static Handler mHandler;


    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    private OkHttpClient getOkHttpClient(){
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }
        return mOkHttpClient;
    }


    @Override
    public void post(Context context, String url, Map<String, Object> params, HttpEngineCallback httpEngineCallback, boolean cache) {

//        RequestBody requestBody = Utils.appendBody(params);
//        Request request = new Request.Builder()
//                .url(url)
//                .tag(context)
//                .post(requestBody)
//                .build();
//
//        mOkHttpClient.newCall(request).enqueue(
//                new Callback() {
//                    @Override
//                    public void onFailure(Call call, final IOException e) {
//                        executeError(httpEngineCallback, e);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String resultJson = response.body().string();
//                        executeSuccessMethod(httpEngineCallback, resultJson);
//                        // 缓存处理，下一期我们没事干，自己手写数据库框架
//                    }
//                }
//        );

    }
    @Override
    public void get(Context context, String url, Map<String, Object> params, final HttpEngineCallback httpEngineCallback, boolean cache) {
        // 公共参数...

        String jointUrl = Utils.jointParams(url, params);
        Logger.d("请求路径："+jointUrl);
        Request.Builder requestBuilder = new Request.Builder().url(jointUrl).tag(context);
        Request request = requestBuilder.build();
        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 失败
                executeFailure(httpEngineCallback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // 1.JSON解析转换
                // 2.显示列表数据
                // 3.缓存数据
                String msg = response.body().string();
                executeSuccess(httpEngineCallback, msg);
                Logger.d("请求结果："+ msg);
            }
        });
    }


    /**
     *  成功
     **/
    private void executeSuccess(final HttpEngineCallback httpEngineCallback, final String resultJson) {
        if (httpEngineCallback == null) {
            return;
        }
        try {
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    httpEngineCallback.onSuccess(resultJson);
                }
            });
        } catch (Exception e) {
            executeFailure(httpEngineCallback, e);
            e.printStackTrace();
        }
    }

    /**
     *  失败
     */
    private void executeFailure(final HttpEngineCallback httpEngineCallback, final Exception e) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                httpEngineCallback.onFailure(e);
            }
        });
    }

}
