package com.jl.baselibrary.app;

import androidx.multidex.MultiDexApplication;

/**
 * Created by JayLer on 2019/6/13.
 */
public class BaseApplication extends MultiDexApplication {

    private BaseApplication mApplication;

    public BaseApplication getApplication(){
        return mApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
        //Module类的APP初始化
        modulesApplicationInit();
    }

    private void modulesApplicationInit() {

        for (String moduleImpl : ModuleConfig.getModules()) {
            try {
                Class clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication)
                    ((IComponentApplication) obj).onCreate(mApplication);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
