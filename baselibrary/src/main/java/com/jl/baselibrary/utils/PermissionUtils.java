package com.jl.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 权限检测工具类
 * 检查权限 --> 请求权限 --> 回调请求结果
 * eg:PermissionUtils.with(this).permissions(...).code(.).request();
 *    PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
 * Created by JayLer on 2019/3/22.
 */
public class PermissionUtils {

    private Context mContext;

    private int mRequestCode;

    private String[] mPermissions;

    private PermissionResultCallback mCallback;

    private static PermissionUtils mPermissionUtils;

    public PermissionUtils(Context context) {
        this.mContext = context;
    }

    public static PermissionUtils getInstance() {
        return mPermissionUtils;
    }

    public static PermissionUtils with(Context context) {
        mPermissionUtils = new PermissionUtils(context);
        return mPermissionUtils;
    }

    public PermissionUtils permissions(String... permissions) {
        this.mPermissions = permissions;
        return mPermissionUtils;
    }

    public PermissionUtils code(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }


    /**
     * 请求权限
     */
    public void request(PermissionResultCallback callback) {

        mCallback = callback;

        LinkedList<String> deniedPermissions = getDeniedPermissions(mContext, mPermissions);

        if (deniedPermissions == null || deniedPermissions.isEmpty()) {
            if (callback != null) {
                callback.onGranted();
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] requestPermissions = deniedPermissions.toArray(new String[deniedPermissions.size()]);
            ActivityCompat.requestPermissions((Activity) mContext,requestPermissions, mRequestCode);
        }

    }

    /**
     * 检查权限,获取未授权的权限
     */
    public LinkedList getDeniedPermissions(Context context, String[] permissions) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return null;
        }

        LinkedList<String> deniedPermissions = new LinkedList<>();
        for (String permission : permissions) {
            int checkSelfPermission = checkSelfPermission(context, permission);
            //如果是拒绝状态则装入拒绝集合中
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }

        return deniedPermissions;

    }

    /**
     * 检查权限的状态
     */
    static int checkSelfPermission(Context context, String permission) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    return ContextCompat.checkSelfPermission(context, permission);
                } else {
                    return PermissionChecker.checkSelfPermission(context, permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ContextCompat.checkSelfPermission(context, permission);
    }


    /**
     * 请求权限的回调，在 onRequestPermissionsResult(...) 中使用
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (mCallback == null) {
            return;
        }

        if (requestCode != mRequestCode) {
            return;
        }

        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            // 拒绝权限
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                permissionList.add(permissions[i]);
            }
        }

        if (permissionList.isEmpty()) {
            mCallback.onGranted();
        } else {
            mCallback.onDenied(permissionList);
        }

    }

    /**
     * 跳转到权限设置界面
     */
    public static void toAppSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }


    public interface PermissionResultCallback {
        /**
         * 同意
         */
        void onGranted();

        /**
         * 拒绝
         */
        void onDenied(List<String> permissions);
    }
}
