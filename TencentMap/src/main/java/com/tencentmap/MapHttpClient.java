package com.tencentmap;

import android.content.Context;

import com.rxokhttplibrary.base.HttpClient;
import com.rxokhttplibrary.base.api.BaseApiParams;
import com.rxokhttplibrary.core.HttpClientFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by JayLer on 2019/5/15.
 */
public class MapHttpClient implements HttpClient {


    private volatile static MapHttpClient INSTANCE;

    //构造方法私有
    private MapHttpClient() {
    }

    //获取单例
    public static synchronized MapHttpClient getInstance() {
        if (INSTANCE == null) {
            synchronized (MapHttpClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MapHttpClient();
                }
            }
        }
        return INSTANCE;
    }

    private static HttpService httpService;


    /**
     * 统一请求
     */
    public HttpService with(Context context) {
        if (httpService == null) {

            okhttp3.OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(BaseApiParams.CONNECTION_TIME, TimeUnit.SECONDS);
            builder.readTimeout(BaseApiParams.TIMEOUT_READ, TimeUnit.SECONDS);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    return chain.proceed(request);
                }
            });
            httpService = new HttpClientFactory.Builder()
                    .okHttpClientBuilder(builder)
                    .useCache(false)
                    .url(WebServiceAPI.BASE_URL)
                    .build(HttpService.class);
        }
        return httpService;
    }


    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }

    }
}
