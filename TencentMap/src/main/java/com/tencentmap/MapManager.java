package com.tencentmap;

import android.content.Context;

import com.jl.baselibrary.rxhttp.BaseObserver;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.RoutePlanningParam;
import com.tencent.lbssearch.object.result.WalkingResultObject;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by JayLer on 2019/6/11.
 */
public class MapManager {

    private static MapManager mapManager;

    public static MapManager getInstance() {
        if (mapManager == null) {
            synchronized (MapManager.class){
                if (mapManager == null) {
                    mapManager = new MapManager();
                }
            }
        }
        return mapManager;
    }

    public void POISearch(Context context, LatLng latLng, BaseObserver observer) {
        MapHttpClient.getInstance()
                .with(context)
                .search("MKWBZ-IH53W-NGSRB-OTOS7-2SW52-AHBOI", "厕所", String.format("nearby(%1$s,%2$s,%3$s)", latLng.latitude, latLng.longitude, 350))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void getWalkPlan(Context context, LatLng fromLatLng, LatLng toLatLng, HttpResponseListener listener) {
        TencentSearch tencentSearch = new TencentSearch(context);
        RoutePlanningParam param = new RoutePlanningParam() {
            @Override
            public String getUrl() {
                return "https://apis.map.qq.com/ws/direction/v1/walking?key=MKWBZ-IH53W-NGSRB-OTOS7-2SW52-AHBOI";
            }

            @Override
            public Class<?> getResultClass() {
                return WalkingResultObject.class;
            }
        };
        param.from(fromLatLng);
        param.to(toLatLng);
        tencentSearch.getRoutePlan(param, listener);
    }

    public void geoCoder(Context context, LatLng latLng, int pageIndex, BaseObserver observer) {

        if (latLng == null) {
            return;
        }
        MapHttpClient.getInstance()
                .with(context)
                .geoCoder("MKWBZ-IH53W-NGSRB-OTOS7-2SW52-AHBOI", String.format("%1$s,%2$s", latLng.latitude, latLng.longitude),
                        1, String.format("page_size=20;page_index=%1$s", pageIndex))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void suggestion(Context context, String keyWord, int pageIndex, BaseObserver observer) {

        MapHttpClient.getInstance()
                .with(context)
                .suggestion("MKWBZ-IH53W-NGSRB-OTOS7-2SW52-AHBOI", keyWord, "广州", pageIndex, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
