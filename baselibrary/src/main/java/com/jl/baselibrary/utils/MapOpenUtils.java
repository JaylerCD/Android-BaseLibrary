package com.jl.baselibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by JayLer on 2019/5/29.
 */
public class MapOpenUtils {


    /**
     * https://lbs.qq.com/uri_v1/guide-route.html
     */
    public static void openTencentMap(Context context, String title, double lat, double lng) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        Uri uri = Uri.parse("qqmap://map/routeplan?type=walk&to="+title+"&tocoord=" + lat + "," + lng);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "您尚未安装腾讯地图", Toast.LENGTH_LONG).show();
        }
    }


    /**
     *  http://lbsyun.baidu.com/index.php?title=uri/api/android
     */
    public static void openBaiduMap(Context context, String title, double lat, double lng) {
        if (isInstalled(context, "com.baidu.BaiduMap")) {
            try {
                Intent intent = Intent.getIntent("intent://map/direction?" +
                        "destination=latlng:" + lat + "," + lng + "|name:"+title+        //终点
                        "&mode=walking&" +          //导航路线方式
                        "&coord_type=gcj02"+
                        "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        } else {
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
    }

    /**
     * http://lbs.amap.com/api/amap-mobile/guide/android/route
     */
    public static void openGaodeMap(Context context, String title, double lat, double lng) {

        if (isInstalled(context, "com.autonavi.minimap")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            Uri uri = Uri.parse("amapuri://route/plan/?did=BGVIS2&dlat="+lat+"&dlon="+lng+"&dname="+title+"&dev=0&t=2");
            intent.setData(uri);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
    }



    public static boolean isInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pInfo != null) {
            for (int i = 0; i < pInfo.size(); i++) {
                String pn = pInfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
