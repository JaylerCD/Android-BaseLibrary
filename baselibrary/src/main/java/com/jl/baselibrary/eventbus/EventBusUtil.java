package com.jl.baselibrary.eventbus;


import org.greenrobot.eventbus.EventBus;


public class EventBusUtil {

    /**
     * 注册事件
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 解除事件
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送普通事件
     * @param event
     */
    public static void sendEvent(MessageEvent event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送黏性事件
     * @param event
     */
    public static void sendStickyEvent(MessageEvent event) {
        EventBus.getDefault().postSticky(event);
    }
}
