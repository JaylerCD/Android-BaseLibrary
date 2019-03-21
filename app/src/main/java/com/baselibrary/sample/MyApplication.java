package com.baselibrary.sample;

import android.app.Application;

import com.baselibrary.sample.http.OkHttpEngine;
import com.jl.baselibrary.http.HttpManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        HttpManager.initEngine(new OkHttpEngine());
    }
}
