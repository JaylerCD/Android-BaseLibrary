package com.jl.baselibrary.handler;

import android.content.Context;
import android.net.sip.SipSession;
import android.util.Log;

/**
 * 捕获应用闪退信息
 * 继承此类，重写 handleUncaughtException() 处理闪退信息
 * Created by JayLer on 2019/3/22.
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler{

    // 单例设计模式(双重校验锁)，volatile 与主线程变量同步
    private volatile static ExceptionCrashHandler mInstance;

    // 留下原来的，便于开发的时候调试
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    // 上下文，获取版本信息和设备信息
    private Context mContext;

    // 闪退信息监听
    private Listener mListener;

    public static ExceptionCrashHandler getInstance(){
        if (mInstance == null) {
            synchronized (ExceptionCrashHandler.class){
                if (mInstance == null) {
                    mInstance = new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }

    public ExceptionCrashHandler  init(Context context){

        //Set the handler invoked when this thread abruptly terminates due to an uncaught exception
        Thread.currentThread().setUncaughtExceptionHandler(this);

        //Return the default uncaught exception handler for all threads
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.mContext = context;

        return mInstance;
    }

    public void setListener(Listener listener){
        this.mListener = listener;
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            if (mListener != null) {
                mListener.onHandleUncaughtExceptionListener(t, e, this.mContext);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            mDefaultHandler.uncaughtException(t, e);
        }

    }

    /**
     * 闪退信息监听
     */
    public interface Listener{

        void onHandleUncaughtExceptionListener(Thread t, Throwable e, Context context);
    }

}
