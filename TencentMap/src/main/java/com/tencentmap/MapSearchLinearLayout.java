package com.tencentmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rxokhttplibrary.base.BaseObserver;
import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.Animation;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.TranslateAnimation;
import com.tencentmap.entity.GeoCoderEntity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by JayLer on 2019/5/16.
 */
public class MapSearchLinearLayout extends LinearLayout implements TencentMap.OnCameraChangeListener, View.OnClickListener{

    private RecyclerView recyclerView;
    private android.support.v4.app.FragmentManager fm;
    protected TencentMap tencentMap;
    private SupportMapFragment supportMapFragment;
    private Context context;
    private List<GeoCoderEntity.ResultBean.PoisBean> mList;
    private LoadMoreAdapter<GeoCoderEntity.ResultBean.PoisBean> mAdapter;
    private boolean mHasNext;
    private UiSettings uiSettings;
    private LatLng inScreenCenterLatLng;
    private int pageIndex = 1;
    private int poiCount;
    private LatLng currentLatLng;
    private LatLng selectedLatLng;
    private String selectedPoiTitle;
    private int selectedPosition;
    private boolean isItemClickAction;
    private Marker marker;
    private Marker selectedMarker;
    private float previousZoom;
    private Marker inScreenCenterMarker;
    private ImageView ivLocation;
    private OnViewListener viewListener;
    private boolean isActivityResult;

    public void setViewListener(OnViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void setCurrentLatLng(LatLng latLng){
        currentLatLng = latLng;
        MapUtils.cameraUpdate(tencentMap, currentLatLng);
        setMarker(latLng);
    }

    public void setSelectedLatLng(LatLng latLng){
        this.selectedLatLng = latLng;
    }

    public LatLng getSelectedLatLng(){
        return selectedLatLng;
    }

    public String getSelectedPoiTitle() {
        return selectedPoiTitle;
    }

    public void setSelectedPoiTitle(String title) {
        this.selectedPoiTitle = title;
    }

    /**
     * 设置标注
     */
    private void setMarker(LatLng latLng) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getDefaultMarkerBitMap(R.drawable.icon_point_blue));
        MarkerOptions options = new MarkerOptions().position(latLng).icon(bitmapDescriptor);
        marker = tencentMap.addMarker(options);
        marker.setInfoWindowEnable(true);
    }


    /**
     * 更新标注
     */
    private void updateMarker(LatLng latLng) {
        if (selectedMarker != null ) {
            selectedMarker.setPosition(latLng);
        }else {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getDefaultMarkerBitMap(R.drawable.icon_point_gray));
            MarkerOptions options = new MarkerOptions().position(latLng).icon(bitmapDescriptor);
            selectedMarker = tencentMap.addMarker(options);
            selectedMarker.setInfoWindowEnable(false);
        }
    }


    private Bitmap getDefaultMarkerBitMap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 45;
        int newHeight = 45;
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    public MapSearchLinearLayout(Context context) {
        this(context, null);
    }

    public MapSearchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    /**
     * XML布局被加载完回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.iv_location).setOnClickListener(this);
        initRecyclerView();
        initMap();

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        mAdapter = new LoadMoreAdapter<GeoCoderEntity.ResultBean.PoisBean>(context, mList) {
            @Override
            protected int getItemLayoutID() {
                return R.layout.holder_item_address;
            }

            @Override
            protected int getItemHeaderLayoutID() {
                return R.layout.holder_item_address_header;
            }

            @Override
            protected void bindDataToView(LoadMoreBaseViewHolder holder, final int position, final GeoCoderEntity.ResultBean.PoisBean entity, final int itemViewType) {
                if (itemViewType == TYPE_ITEM_HEADER) {
                    TextView tvTitle = holder.getView(R.id.tvTitle);
                    tvTitle.setText(TextUtils.isEmpty(entity.getTitle())?"":entity.getTitle());
                }else {
                    TextView tvTitle = holder.getView(R.id.tvTitle);
                    TextView tvAddress = holder.getView(R.id.tvAddress);
                    tvTitle.setText(TextUtils.isEmpty(entity.getTitle())?"":entity.getTitle());
                    tvAddress.setText(TextUtils.isEmpty(entity.getAddress())?"":entity.getAddress());
                }

                ImageView ivSelected = holder.getView(R.id.ivSelected);
                ivSelected.setVisibility(selectedPosition == position?VISIBLE:INVISIBLE);
                holder.getView(R.id.layoutItem).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int previous = selectedPosition;
                        selectedPosition = position;
                        notifyItemChanged(position);
                        notifyItemChanged(previous);

                        LatLng latLng = new LatLng(entity.getLocation().getLat(), entity.getLocation().getLng());

                        setSelectedPoiTitle(entity.getTitle());
                        setSelectedLatLng(latLng);

                        if (itemViewType == TYPE_ITEM_HEADER) {
                            if (selectedMarker != null) {
                                selectedMarker.remove();
                                selectedMarker = null;
                            }
                        }else {
                            updateMarker(latLng);
                        }
                        MapUtils.cameraUpdate(tencentMap, latLng);

                        isItemClickAction = true;

                        if (viewListener != null) {
                            viewListener.onItemClick(entity);
                        }
                    }
                });
            }

        };
        mAdapter.setItemHeaderEnable(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isVisBottom(recyclerView)) {
                    if (mAdapter.getItemCount() == 0) {
                        mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
                        return;
                    }
                    if (!mHasNext) {
                        mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
                        return;
                    }
                    addData();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));//设置布局管理器
        recyclerView.setAdapter(mAdapter);
        addData();
        mAdapter.setLoadState(mAdapter.LOADING);
        mAdapter.notifyDataSetChanged();

    }

    public static boolean isVisBottom(RecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }

    private void addData() {
        geoCoder(currentLatLng);
    }



    /**
     * 创建Map地图对象，可以完成对地图的几乎所有操作
     */
    private void initMap() {
        fm = ((FragmentActivity)context).getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_frag);
        tencentMap = supportMapFragment.getMap();

        uiSettings = MapUtils.initUiSettings(tencentMap);
        tencentMap.setOnCameraChangeListener(this);
    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinished(CameraPosition cameraPosition) {

        if (previousZoom !=0 && previousZoom != cameraPosition.zoom) {
            previousZoom = cameraPosition.zoom;
            return;
        }

        previousZoom = cameraPosition.zoom;

        if (isItemClickAction) {
            isItemClickAction = false;
            return;
        }

        if (selectedMarker != null) {
            selectedMarker.remove();
            selectedMarker = null;
        }

        if (inScreenCenterMarker != null) {
            inScreenCenterMarker.remove();
            inScreenCenterMarker = null;
        }

        inScreenCenterLatLng = cameraPosition.target;
        poiCount = 0;
        pageIndex = 1;
        selectedPosition = 0;
        currentLatLng = inScreenCenterLatLng;
        inScreenCenterMarker = MapUtils.addMarkerInScreenCenter(tencentMap, MapUtils.getBitMap(context, R.drawable.marker, 42, 76));
        startMarkerAnimation();
        geoCoder(currentLatLng);
        if (viewListener != null) {
            viewListener.onCameraChangeFinished(cameraPosition);
        }
    }

    /**
     * 逆地址解析(坐标位置描述)
     */
    private void geoCoder(LatLng latLng) {
        BaseObserver<GeoCoderEntity> observer = new BaseObserver<GeoCoderEntity>(context) {

            @Override
            public void onSuccess(GeoCoderEntity result) {

                List<GeoCoderEntity.ResultBean.PoisBean> pois = result.getResult().getPois();
                if (pageIndex == 1) {
                    mList.clear();
                    poiCount = result.getResult().getPoi_count();
                    recyclerView.smoothScrollToPosition(0);
                    GeoCoderEntity.ResultBean.PoisBean bean = new GeoCoderEntity.ResultBean.PoisBean();
                    bean.setTitle(result.getResult().getFormatted_addresses().getRecommend());
                    bean.setLocation(result.getResult().getLocation());
                    mList.add(bean);

                    if (isActivityResult) {
                        isActivityResult = false;
                    } else {
                        setSelectedPoiTitle(bean.getTitle());
                        setSelectedLatLng(new LatLng(bean.getLocation().getLat(), bean.getLocation().getLng()));
                    }

                }
                mList.addAll(pois);
                if (mList.size() <= poiCount) {
                    mHasNext = true;
                    pageIndex++;
                } else {
                    mHasNext = false;
                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFail(Throwable e) {
            }
        };

        MapManager.getInstance().geoCoder(context, latLng, pageIndex, observer);
    }


    /**
     * 标注动画
     */
    private void startMarkerAnimation(){
        if (inScreenCenterMarker != null && tencentMap != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = inScreenCenterMarker.getPosition();
            Point point =  tencentMap.getProjection().toScreenLocation(latLng);

            if (point == null) {
                return;
            }
            point.y -= ScreenUtil.dip2px(context,50);
            LatLng target = tencentMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if(input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f)*(1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(420);
            //设置动画
            inScreenCenterMarker.setAnimation(animation);
            //开始动画
            inScreenCenterMarker.startAnimation();

        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_location) {
            mAdapter.notifyItemChanged(0);
            mAdapter.notifyItemChanged(selectedPosition);
            selectedPosition = 0;
            GeoCoderEntity.ResultBean.PoisBean bean = mList.get(selectedPosition);
            setSelectedLatLng(new LatLng(bean.getLocation().getLat(), bean.getLocation().getLng()));
            setSelectedPoiTitle(bean.getTitle());
            if (selectedMarker != null) {
                selectedMarker.remove();
                selectedMarker = null;
            }
            MapUtils.cameraUpdate(tencentMap, marker.getOptions().getPosition());
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            LatLng latLng = data.getParcelableExtra(Constants.LAT_LNG);
            String title = data.getStringExtra(Constants.TITLE);
            if (latLng == null) {
                return;
            }
            inScreenCenterLatLng  = latLng;
            isActivityResult = true;

            setSelectedPoiTitle(title);
            setSelectedLatLng(latLng);

            MapUtils.cameraUpdate(tencentMap, latLng, false);
        }
    }


    public interface OnViewListener{
         void onCameraChangeFinished(CameraPosition cameraPosition);
         void onItemClick(GeoCoderEntity.ResultBean.PoisBean bean);
    }
}
