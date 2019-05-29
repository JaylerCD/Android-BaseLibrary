package com.rxokhttplibrary.base.api;


/**
 * Created by TIM on 2016/7/16.
 */
public class BaseApiParams {

    /**
     * 是否需要缓存处理
     */
    private boolean cache = true;
    /**
     * 基础url
     */
    public static final String BASE_URL = "";


    /**
     * 超时时间-默认6秒
     */
    public static final int CONNECTION_TIME = 6;
    /**
     * 重新连接时间
     */
    public static final int TIMEOUT_READ = 6;

    /**
     * 有网情况下的本地缓存时间默认60秒
     */
    public static final int COOKIE_NETWORK_TIME = 60;
    /**
     * 无网络的情况下本地缓存时间默认30天
     */
    public static final int COOKIE_NO_NETWORK_TIME = 24 * 60 * 60 * 30;
    /**
     * 失败后retry次数
     */
    public static final int RETRY_COUNT = 1;
    /**
     * 失败后retry延迟
     */
    public static final long RETRY_DELAY = 100;
    /**
     * 失败后retry叠加延迟
     */
    public static final long RETRY_INCREASE_DELAY = 10;


}
