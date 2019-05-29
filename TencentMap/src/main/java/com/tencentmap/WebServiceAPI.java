package com.tencentmap;

/**
 * Created by JayLer on 2019/5/15.
 */
public class WebServiceAPI {

    public static final String BASE_URL = "https://apis.map.qq.com";

    /**
     * GET
     * 地点搜索（search接口）
     */
    public static final String MAP_SEARCH = BASE_URL + "/ws/place/v1/search";


    /**
     * GET
     * 用于获取输入关键字的补完与提示，帮助用户快速输入
     */
    public static final String MAP_SUGGESTION = BASE_URL + "/ws/place/v1/suggestion";


    /**
     * GET
     * 逆地址解析(坐标位置描述)
     */
    public static final String MAP_GEOCODER = BASE_URL + "/ws/geocoder/v1/";



}
