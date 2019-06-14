package com.jl.baselibrary.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JayLer on 2019/6/13.
 */
public class ModuleConfig {

    private static List<String> sModules = new ArrayList();

    public static void add(String className){
        if (!sModules.contains(className)) {
            sModules.add(className);
        }
    }

    public static List<String> getModules(){
        return sModules;
    }
}
