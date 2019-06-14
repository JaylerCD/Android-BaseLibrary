package com.jl.baselibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.jl.baselibrary.utils.NetworkUtils;


/**
 * @author macTim
 */
public class NetWorkBroadcastReceiver extends BroadcastReceiver {

    /**
     * 网络状态监听接口
     */
    private NetsStatusMonitorEvent netStatusMonitor;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        Log.i("NetBroadcastReceiver", "NetBroadcastReceiver changed");
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetworkUtils.getNetWorkState(context);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
            if (netWorkState == NetworkUtils.NET_PING_OK) {
                if (netStatusMonitor != null) {
                    // 接口传递网络状态的类型到注册广播的页面
                    netStatusMonitor.onNetChange(true);
                }
            } else {
                netStatusMonitor.onNetChange(false);

            }
        }
    }


    /**
     * 设置网络状态监听接口
     */
    public void setStatusMonitor(NetsStatusMonitorEvent netStatusMonitor) {
        this.netStatusMonitor = netStatusMonitor;
    }

    public interface NetsStatusMonitorEvent {
        void onNetChange(boolean netStatus);
    }
}