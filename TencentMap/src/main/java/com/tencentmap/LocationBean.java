package com.tencentmap;

/**
 * Created by JayLer on 2019/5/17.
 */
public class LocationBean {

    private double lat;
    private double lng;

    public LocationBean(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LocationBean() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
