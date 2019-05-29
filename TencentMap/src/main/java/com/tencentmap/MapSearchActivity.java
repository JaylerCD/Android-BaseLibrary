package com.tencentmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencentmap.R;
import com.jl.baselibrary.base.BaseActivity;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

public class MapSearchActivity extends BaseActivity implements MapSearchLinearLayout.OnViewListener{


    private MapSearchLinearLayout mapSearchLinearLayout;
    private View mapOtherView;

    protected View getOtherView(){
        return mapOtherView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_search);
        initView();
        initData();
    }

    private void initView() {
        mapSearchLinearLayout = findViewById(R.id.mapSearchLinearLayout);
        mapOtherView = findViewById(R.id.map_other_view);
        mapSearchLinearLayout.setViewListener(this);
    }

    private void initData() {
        LatLng latLng = getIntent().getParcelableExtra(Constants.LAT_LNG);
        mapSearchLinearLayout.setCurrentLatLng(latLng);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mapSearchLinearLayout.onActivityResult(requestCode, resultCode, data);
    }


    public LatLng getSelectedLatLng(){
        return mapSearchLinearLayout.getSelectedLatLng();
    }

    public String getSelectedPoiTitle() {
        return mapSearchLinearLayout.getSelectedPoiTitle();
    }

    @Override
    public void onCameraChangeFinished(CameraPosition cameraPosition) {

    }

    @Override
    public void onItemClick(GeoCoderEntity.ResultBean.PoisBean bean) {

    }
}
