package com.baselibrary.sample.http;

import android.os.Handler;
import android.os.Looper;

import com.baselibrary.sample.utils.Utils;
import com.jl.baselibrary.http.HttpEngineCallback;
import com.jl.baselibrary.http.HttpEngine;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

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
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build();
        }
        return mOkHttpClient;
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
                    Logger.d("请求结果："+ resultJson);
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
    @Override
    public void post(String url, Map<String, Object> params, String contentType, Object tag, boolean cache, HttpEngineCallback httpEngineCallback) {
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
    public void get(String url, Map<String, Object> params, String contentType, Object tag, boolean cache, final HttpEngineCallback httpEngineCallback) {
        // 公共参数...

        String jointUrl = Utils.jointParams(url, params);
        Logger.d("请求路径："+jointUrl);
        Request.Builder requestBuilder = new Request.Builder().url(jointUrl).header("Content-Type", contentType).tag(tag);
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

            }
        });
    }

    @Override
    public void download(String url, String filePath, String fileName, Object tag, HttpEngineCallback httpEngineCallback) {

    }

    @Override
    public void upload(String url, com.jl.baselibrary.http.MultipartBody body, String filePath, String fileName, Object tag, HttpEngineCallback httpEngineCallback) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();

        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


//        //增加拦截器
//        getOkHttpClient().networkInterceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                //拦截
//                Response originalResponse = chain.proceed(chain.request());
//                //包装响应体并返回
//                return originalResponse.newBuilder()
//                        .body(new RealResponseBody())
//                        .build();
//            }
//        });
    }


    @Override
    public void cancel(Object tag) {
        try {
            for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
                if (call.request().tag().toString().contains(tag.toString())) {
                    call.cancel();
                }
            }
            for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
                if (call.request().tag().toString().contains(tag.toString())) {
                    call.cancel();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void cancelAll() {
        try {
            //去掉队里中或者带运行状态的所有接口call
            getOkHttpClient().dispatcher().cancelAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
