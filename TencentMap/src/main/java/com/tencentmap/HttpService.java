package com.tencentmap;

import com.tencentmap.entity.GeoCoderEntity;
import com.tencentmap.entity.SearchEntity;
import com.tencentmap.entity.SuggestionEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpService {

    @GET(WebServiceAPI.MAP_SEARCH)
    Observable<SearchEntity> search(@Query("key")String key, @Query("keyword") String keyword, @Query("boundary") String boundary);


    @GET(WebServiceAPI.MAP_GEOCODER)
    Observable<GeoCoderEntity> geoCoder(@Query("key")String key, @Query("location") String location, @Query("get_poi") int get_poi, @Query("poi_options") String poi_options);

    @GET(WebServiceAPI.MAP_SUGGESTION)
    Observable<SuggestionEntity> suggestion(@Query("key")String key, @Query("keyword") String keyword, @Query("region") String boundary, @Query("page_index") int page_index, @Query("page_size") int page_size);

}
