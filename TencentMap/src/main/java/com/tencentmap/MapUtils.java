package com.tencentmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

/**
 * Created by JayLer on 2019/5/17.
 */
public class MapUtils {

    /**
     * UiSettings
     */
    public static UiSettings initUiSettings(TencentMap tencentMap){
        UiSettings mapUiSettings = tencentMap.getUiSettings();
        mapUiSettings.setGestureScaleByMapCenter(true);
        mapUiSettings.setCompassEnabled(false);
        mapUiSettings.setTiltGesturesEnabled(false);
        return mapUiSettings;
    }


    /**
     * 移动地图 - 动画
     */
    public static void cameraUpdate(TencentMap tencentMap, LatLng latLng) {
        cameraUpdate(tencentMap, latLng, true);
    }


    /**
     * 移动地图
     */
    public static void cameraUpdate(TencentMap tencentMap, LatLng latLng, boolean isAnimation) {
        CameraUpdate cameraSigma =
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        latLng,
                        15,
                        0f,
                        0f));
        if (isAnimation) {
            tencentMap.animateCamera(cameraSigma);
        }else {
            tencentMap.moveCamera(cameraSigma);
        }
    }

    /**
     * 获取图片
     */
    public static Bitmap getBitMap(Context context, int resourceId, int newWidth, int newHeight) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }


    /**
     * 添加屏幕中心marker
     */
    public static Marker addMarkerInScreenCenter(TencentMap tencentMap, Bitmap bitmap){
        LatLng latLng = tencentMap.getCameraPosition().target;
        Point screenPosition = tencentMap.getProjection().toScreenLocation(latLng);
        Marker marker = tencentMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));

        //设置Marker在屏幕上,不跟随地图移动
        marker.setFixingPoint(screenPosition.x,screenPosition.y - (bitmap.getHeight() - 12)/2);
        marker.setZIndex(1);
        return marker;
    }


}
