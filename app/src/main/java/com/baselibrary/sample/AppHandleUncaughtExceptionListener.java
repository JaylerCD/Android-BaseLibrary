package com.baselibrary.sample;

import android.content.Context;
import android.util.Log;

import com.jl.baselibrary.handler.ExceptionCrashHandler;

/**
 * Created by JayLer on 2019/3/22.
 */
public class AppHandleUncaughtExceptionListener implements ExceptionCrashHandler.Listener {
    @Override
    public void onHandleUncaughtExceptionListener(Thread t, Throwable e, Context context) {
        Log.d("CJL", t.getName() +": "+e.getLocalizedMessage());
    }
}
