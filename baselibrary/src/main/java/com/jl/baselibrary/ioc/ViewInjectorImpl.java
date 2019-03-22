package com.jl.baselibrary.ioc;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jl.baselibrary.Core;
import com.jl.baselibrary.ioc.annotation.ContentView;
import com.jl.baselibrary.ioc.annotation.OnClick;
import com.jl.baselibrary.ioc.annotation.ViewById;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ViewInjectorImpl implements ViewInjector {


    private static final Object lock = new Object();
    private static volatile ViewInjectorImpl instance;

    private ViewInjectorImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ViewInjectorImpl();
                }
            }
        }
        Core.setViewInjector(instance);
    }


    @Override
    public void inject(View view) {
        inject(view, view.getClass(), new ViewFinder(view));
    }

    @Override
    public void inject(Activity activity) {


        ContentView contentView = activity.getClass().getAnnotation(ContentView.class);
        if (contentView != null) {
            int contentViewId = contentView.value();
            int viewId = contentView.value();
            if (viewId <= 0) {
                return;
            }
            try {
                Method method = activity.getClass().getMethod("setContentView", int.class);
                method.setAccessible(true);
                method.invoke(activity, contentViewId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        inject(activity, activity.getClass(), new ViewFinder(activity));
    }

    @Override
    public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {

        ContentView contentView = fragment.getClass().getAnnotation(ContentView.class);
        if (contentView == null) {
            return null;
        }
        View view = inflater.inflate(contentView.value(), container, false);
        inject(fragment, fragment.getClass(), new ViewFinder(view));
        return view;
    }

    @Override
    public void inject(Object handler, View view) {
        inject(handler, handler.getClass(), new ViewFinder(view));
    }

    /**
     * 兼容接口的所有方法
     */
    private static void inject(Object object, Class<?> objectClass, ViewFinder finder) {

        injectFiled(object, objectClass, finder);
        injectEvent(object, objectClass, finder);

    }

    /**
     * 注入属性
     */
    private static void injectFiled(Object object, Class<?> objectClass, ViewFinder finder) {
        if (object == null || objectClass == null) {
            return;
        }

        //1.获取类中的所有的属性
        Field[] fields = objectClass.getDeclaredFields();
        //2.获取ViewById的value
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (
                /* 不注入静态字段 */     Modifier.isStatic(field.getModifiers()) ||
                    /* 不注入final字段 */    Modifier.isFinal(field.getModifiers()) ||
                    /* 不注入基本类型字段 */  fieldType.isPrimitive() ||
                    /* 不注入数组类型字段 */  fieldType.isArray()) {
                continue;
            }

            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //找到注解里面的id值 --> R.id.xx
                int viewId = viewById.value();
                //3.findViewById找到view
                View view = finder.findViewById(viewId);
                //能够注入所有修饰符
                field.setAccessible(true);
                //4.动态的注入找到的view
                try {
                    field.set(object, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 注入事件，暂时只有普通点击事件(常用)
     */
    private static void injectEvent(Object object, Class<?> objectClass, ViewFinder finder) {

        if (object == null || objectClass == null) {
            return;
        }

        Method[] methods = objectClass.getDeclaredMethods();

        if (methods.length <= 0) {
            return;
        }
        for (Method method : methods) {

            if (Modifier.isStatic(method.getModifiers())
                    || !Modifier.isPrivate(method.getModifiers())) {
                continue;
            }

            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                if (viewIds.length <= 0) {
                    return;
                }

                for (int viewId : viewIds) {
                    View view = finder.findViewById(viewId);
                    if (view != null) {
                        //可以查看View源码是怎么实现的
                        view.setOnClickListener(new DeclaredOnClickListener(method, object));
                    }
                }
            }


        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Method mResolvedMethod;
        private Object mObject;

        public DeclaredOnClickListener(@NonNull Method method, @NonNull Object object) {
            mResolvedMethod = method;
            mObject = object;
        }

        @Override
        public void onClick(@NonNull View v) {
            mResolvedMethod.setAccessible(true);
            try {
                mResolvedMethod.invoke(mObject, v);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Could not execute method for android:onClick", e);
            }
        }
    }


}
