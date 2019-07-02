package com.jl.baselibrary.rxhttp;

import android.util.ArrayMap;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * HttpClient工厂类
 */

public final class HttpClientFactory {

    private static ArrayMap<String, Retrofit> retrofitArrayMap = new ArrayMap<>();

    private HttpClientFactory() {
    }

    public static final class Builder {

        private OkHttpClient.Builder mBuilder;
        private String mUrl;
        private boolean mUseCache = true;

        public Builder() {
        }

        public Builder okHttpClientBuilder(OkHttpClient.Builder builder) {
            mBuilder = builder;
            return this;
        }

        public Builder url(@NonNull String url) {
            mUrl = url;
            return this;
        }

        public Builder useCache(boolean useCache) {
            mUseCache = useCache;
            return this;
        }

        public <T> T build(Class<T> clazz) {
            return create(mBuilder, mUrl, clazz, mUseCache);
        }

        /**
         * 通过Retrofit生成接口实现类
         *
         * @param builder  自定义OkHttpClient
         * @param url      请求地址
         * @param clazz    Restful接口类
         * @param useCache 使用缓存
         * @return 接口实现对象
         */
        @NonNull
        private <T> T create(@Nullable OkHttpClient.Builder builder, @NonNull String url, @NonNull Class<T> clazz, boolean useCache) {
            if (useCache) {
                T client = createFromCache(url, clazz);
                if (client != null) {
                    return client;
                }
            }
            if (!url.endsWith("/")) {
                url += "/";
            }
            builder.addInterceptor(getHttpLoggingInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            if (useCache){
                retrofitArrayMap.put(url, retrofit);
            }
            return retrofit.create(clazz);
        }

        /**
         * 根据Url从缓存Retrofit实例中创建Restful接口类
         *
         * @param url   请求地址
         * @param clazz Restful接口类
         * @return 接口实现对象
         */
        @Nullable
        private <T> T createFromCache(@NonNull String url, @NonNull Class<T> clazz) {
            Retrofit retrofit = retrofitArrayMap.get(url);
            if (retrofit == null) {
                return null;
            }
            return retrofit.create(clazz);
        }

    }


    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }
}
