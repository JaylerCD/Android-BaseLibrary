package com.tencentmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.result.WalkingResultObject;
import com.tencent.tencentmap.mapsdk.maps.model.PolylineOptions;
import com.jl.baselibrary.base.BaseActivity;
import com.jl.baselibrary.utils.PermissionUtils;
import com.rxokhttplibrary.base.BaseObserver;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.MyLocationStyle;
import com.tencent.tencentmap.mapsdk.maps.model.ScaleAnimation;
import com.tencentmap.entity.SearchEntity;

import java.util.List;

import static com.tencent.tencentmap.mapsdk.maps.model.MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER;


/**
 * SDK提供了SupportMapFragment这个类来加载地图，这个类的方便之处就在于不用手动管理内存
 */
public class SupportMapFragmentActivity extends BaseActivity implements SensorEventListener, LocationSource,
        TencentLocationListener, TencentMap.OnCameraChangeListener, TencentMap.OnMarkerClickListener,
        TencentMap.OnMapClickListener, HttpResponseListener,View.OnClickListener {

    private android.support.v4.app.FragmentManager fm;
    protected TencentMap tencentMap;
    private SupportMapFragment supportMapFragment;
    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    private MyLocationStyle locationStyle;
    private OnLocationChangedListener locationChangedListener;
    private UiSettings mapUiSettings;
    private LatLng inScreenCenterLatLng;
    private boolean isFirstLocation = true;
    private SensorManager sensorManager;
    private Sensor orientationSensor;
    private Marker marker;
    private Marker previousClickedMarker;
    private TencentLocation tencentLocation;
    private Marker inScreenCenterMarker;

    private View mapOtherView;
    private ImageView ivLocation;


    protected View getOtherView(){
        return mapOtherView;
    }

    protected TencentLocation getTencentLocation(){
        return tencentLocation;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_map_fragment);
        mapOtherView = findViewById(R.id.map_other_view);
        ivLocation = findViewById(R.id.iv_location);
        ivLocation.setOnClickListener(this);
        initMap();
        initListener();
        initSensorManager();
        initUiSettings();
        requestPermission();
    }

    /**
     * 创建Map地图对象，可以完成对地图的几乎所有操作
     */
    private void initMap() {

        fm = getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_frag);
        tencentMap = supportMapFragment.getMap();
    }

    /**
     * 初始化传感器，监听方向
     */
    private void initSensorManager() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }


    /**
     * UiSettings
     */
    private void initUiSettings() {
        mapUiSettings = tencentMap.getUiSettings();
        mapUiSettings.setGestureScaleByMapCenter(true);
        mapUiSettings.setCompassEnabled(false);
        mapUiSettings.setTiltGesturesEnabled(false);
    }

    /**
     * 申请定位权限
     */
    private void requestPermission() {
        PermissionUtils.getInstance().with(this)
                .permissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .code(0)
                .request(new PermissionUtils.PermissionResultCallback() {
                    @Override
                    public void onGranted() {
                        Log.e("location quest: ", "success");
                        initLocation();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Log.e("location quest: ", "failed");
                    }
                });
    }

    /**
     * 定位
     */
    private void initLocation() {
        locationManager = TencentLocationManager.getInstance(this);
        locationRequest = TencentLocationRequest.create();
        locationRequest.setInterval(1000 * 30);// 定位周期 (毫秒)
        locationRequest.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI); // 定位要求水准
        locationRequest.setAllowCache(true);// 是否使用缓存
        locationRequest.setAllowDirection(true);
        locationRequest.setIndoorLocationMode(true);
        locationRequest.setAllowGPS(true);
        //设置定位数据源
        tencentMap.setLocationSource(this);
        tencentMap.setMyLocationEnabled(true);
        setLocMarkerStyle();
    }

    /**
     * 设置定位图标样式
     */
    private void setLocMarkerStyle() {
        locationStyle = new MyLocationStyle();
        locationStyle.myLocationType(LOCATION_TYPE_MAP_ROTATE_NO_CENTER);
        //创建图标
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getMyLocationStyleBitMap(R.drawable.navi_marker_location));
        locationStyle.icon(bitmapDescriptor);
        locationStyle.strokeWidth(0);
        locationStyle.strokeColor(Color.argb(0, 255, 255, 255));
        locationStyle.fillColor(Color.argb(0, 255, 255, 255));
        tencentMap.setMyLocationStyle(locationStyle);
    }


    /**
     * 事件监听
     */
    private void initListener() {
        // 地图视图改变
        tencentMap.setOnCameraChangeListener(this);
        tencentMap.setOnMarkerClickListener(this);
        tencentMap.setOnMapClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * 实现位置监听
     */
    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        this.tencentLocation = tencentLocation;
        if (i == TencentLocation.ERROR_OK && locationChangedListener != null) {
            Location location = new Location(tencentLocation.getProvider());
            location.setLatitude(tencentLocation.getLatitude());
            location.setLongitude(tencentLocation.getLongitude());
            location.setAccuracy(tencentLocation.getAccuracy());

            if (isFirstLocation) {
                LatLng latLng = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
                CameraUpdate cameraSigma =
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                latLng,
                                16,
                                0f,
                                0f));
                //首次定位成功，移动地图
                tencentMap.animateCamera(cameraSigma);
                setMarker(latLng);
                inScreenCenterMarker = MapUtils.addMarkerInScreenCenter(tencentMap, MapUtils.getBitMap(this, R.drawable.marker, 42, 76));
                isFirstLocation = false;
            }

            float rotation = (float) tencentLocation.getExtra().getDouble(TencentLocation.EXTRA_DIRECTION);
            updateMarker(rotation);

            locationChangedListener.onLocationChanged(location);
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        int err = locationManager.requestLocationUpdates(locationRequest, this);
        switch (err) {
            case 1:
                Toast.makeText(this, "设备缺少使用腾讯定位服务需要的基本条件", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "manifest 中配置的 key 不正确", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "自动加载libtencentloc.so失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void deactivate() {
        locationManager.removeUpdates(this);
        locationManager = null;
        locationRequest = null;
        locationChangedListener = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinished(CameraPosition cameraPosition) {
        inScreenCenterLatLng =  cameraPosition.target;
        if (isFirstLocation) {
            return;
        }
        POISearch(inScreenCenterLatLng);
    }

    private void POISearch(LatLng latLng) {
        BaseObserver<SearchEntity> observer = new BaseObserver<SearchEntity>(this) {
            @Override
            public void onSuccess(SearchEntity result) {
                List<SearchEntity.DataBean> data = result.getData();

                for (int k = 0; k < data.size(); k++) {
                    SearchEntity.DataBean poi = data.get(k);

                    LatLng a = new LatLng(poi.getLocation().getLat(), poi.getLocation().getLng());
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getMarkerLevelBitMap(R.drawable.icon_marker_level_1));
                    MarkerOptions options = new MarkerOptions().position(a).icon(bitmapDescriptor);
                    options.tag(poi);
                    tencentMap.addMarker(options);
                }

            }

            @Override
            public void onFail(Throwable e) {


            }
        };

        MapManager.getInstance().POISearch(this, latLng, observer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event != null) updateMarker(event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    /**
     * 设置标注
     */
    private void setMarker(LatLng latLng) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getDefaultMarkerBitMap(R.drawable.navi_marker_location));
        MarkerOptions options = new MarkerOptions().position(latLng).icon(bitmapDescriptor);
        marker = tencentMap.addMarker(options);
        marker.setInfoWindowEnable(false);
    }

    /**
     * 更新标注
     */
    private void updateMarker(float rotation) {
        if (marker != null && tencentLocation != null) {
            marker.setPosition(new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude()));
            marker.setRotation(rotation);
        }
    }


    private Bitmap getDefaultMarkerBitMap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 80;
        int newHeight = 80;
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    private Bitmap getMyLocationStyleBitMap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, 1, 1, null, true);
        return bitmap;
    }

    private Bitmap getMarkerLevelBitMap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 73;
        int newHeight = 90;
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        startMarkerAnimation(previousClickedMarker, false);
        previousClickedMarker = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (previousClickedMarker == marker) {
            return false;
        }
        startMarkerAnimation(previousClickedMarker, false);
        startMarkerAnimation(marker, true);

        getWalkPlan(marker.getPosition());
        return false;
    }

    /**
     * 点击Marker效果
     */
    private void startMarkerAnimation(Marker marker, boolean isEnlarge){
        if (marker == null) {
            return;
        }

        if (isEnlarge) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.22f, 1f, 1.22f);
            scaleAnimation.setDuration(100);
            marker.setAnimation(scaleAnimation);
            marker.startAnimation();
            marker.setZIndex(marker.getZIndex()+1);
            previousClickedMarker = marker;
        }else {
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.22f, 1f, 1.22f, 1f);
            scaleAnimation.setDuration(10);
            marker.setAnimation(scaleAnimation);
            marker.startAnimation();
            marker.setZIndex(marker.getZIndex()-1);
        }

    }

    protected void getWalkPlan(LatLng toLatLng){
        LatLng fromLatLng = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
        MapManager.getInstance().getWalkPlan(this, fromLatLng, toLatLng, this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_location) {
            LatLng latLng = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
            MapUtils.cameraUpdate(tencentMap, latLng);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            LatLng latLng = data.getParcelableExtra(Constants.LAT_LNG);
            if (latLng == null) {
                return;
            }
            inScreenCenterLatLng  = latLng;
            MapUtils.cameraUpdate(tencentMap, latLng, false);
        }
    }

    @Override
    public void onSuccess(int i, Object o) {
        if (o instanceof WalkingResultObject) {
            WalkingResultObject walkObj = (WalkingResultObject) o;
            List<WalkingResultObject.Route> walkRoutes = walkObj.result.routes;
        }
    }

    /**
     * 将路线以实线画到地图上
     * @param locations
     */
    protected void drawSolidLine(List<LatLng> locations) {
        PolylineOptions polylineOptions = new PolylineOptions().
                addAll(locations).
                color(0xff0090ff);
        tencentMap.addPolyline(polylineOptions);
    }


    @Override
    public void onFailure(int i, String s, Throwable throwable) {

    }
}
