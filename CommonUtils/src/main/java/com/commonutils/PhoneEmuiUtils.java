package com.commonutils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * 华为系统工具类
 * Created by JayLer on 2019/5/31.
 */
public class PhoneEmuiUtils extends AppSettingUtils{

    /**
     * 华为的权限管理页面
     */
    public static void gotoPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            context.startActivity(getAppDetailSettingIntent(context));
        }

    }
}
