package com.jl.baselibrary.logic;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JayLer on 2019/6/11.
 */
public class LogicServices {

    private static final String TAG = "LogicServices";
    private static LogicServices mInstance;
    private static Map<String, Object> mManagerMap = new HashMap<>();

    public static LogicServices getInstance(){
        if (mInstance == null) {
            synchronized (LogicServices.class){
                if (mInstance == null) {
                    mInstance = new LogicServices();
                }
            }
        }
        return mInstance;
    }


    @SuppressWarnings("unchecked")
    public <T extends Object> T findByLogicManagerClass(Class<T> logicManagerClass){
        T logicManager = null;
        String key = logicManagerClass.getName();

        if (mManagerMap.containsKey(key)) {
            logicManager = (T) mManagerMap.get(key);
        }

        if (logicManager == null) {
            try {
                logicManager = logicManagerClass.newInstance();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        if (logicManager != null) {
            mManagerMap.put(key, logicManager);
        }

        return logicManager;
    }

}
