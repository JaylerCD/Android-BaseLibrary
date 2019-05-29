package com.tencentmap;

/**
 * Created by JayLer on 2019/5/14.
 */
public class BaseAndroid {

    public static BaseMapConfig baseConfig;

    public static void init(BaseMapConfig config) {
        baseConfig = config;
    }

    public static BaseMapConfig getBaseConfig() {
        if (baseConfig == null) {
            throw new IllegalArgumentException("请先调用init方法");
        }
        return baseConfig;
    }
}
