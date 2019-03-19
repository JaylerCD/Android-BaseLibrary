package com.jl.baselibrary;

import android.app.Application;

import com.jl.baselibrary.ioc.ViewInjector;
import com.jl.baselibrary.ioc.ViewInjectorImpl;

public class Core {

    public static void setViewInjector(ViewInjectorImpl injector) {
        Api.viewInjector = injector;
    }


    public static class Api{

        private static Application app;
        private static ViewInjector viewInjector;

        private Api() {
        }

        public static void init(Application app) {
            if (Api.app == null) {
                Api.app = app;
            }
        }

        public static ViewInjector view() {
            if (Api.viewInjector == null) {
                ViewInjectorImpl.registerInstance();
            }
            return Api.viewInjector;
        }

    }

}