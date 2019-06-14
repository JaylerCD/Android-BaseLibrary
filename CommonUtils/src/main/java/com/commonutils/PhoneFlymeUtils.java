package com.commonutils;

import android.content.Context;
import android.content.Intent;


/**
 * 魅族系统工具类
 * Created by JayLer on 2019/5/31.
 */
public class PhoneFlymeUtils extends AppSettingUtils{


    /**
     * 跳转到魅族的权限管理系统
     */
    public static void gotoPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }
}
