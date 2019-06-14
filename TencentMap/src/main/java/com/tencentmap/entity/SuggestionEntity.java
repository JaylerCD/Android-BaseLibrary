package com.tencentmap.entity;

import java.util.List;

/**
 * Created by JayLer on 2019/5/17.
 */
public class SuggestionEntity {


    /**
     * status : 0
     * message : query ok
     * count : 100
     * data : [{"id":"14287485404961640880","title":"局气(五道口店)","address":"北京市海淀区城府路28号五道口购物中心F6","category":"美食:中餐厅:北京菜","type":0,"location":{"lat":39.99152,"lng":116.339438},"adcode":110108,"province":"北京市","city":"北京市","district":"海淀区"},{"id":"15477449678633568235","title":"金鼎轩(地坛店)","address":"北京市东城区和平里西街77号","category":"美食:中餐厅:其它中餐厅","type":0,"location":{"lat":39.95097,"lng":116.41592},"adcode":110101,"province":"北京市","city":"北京市","district":"东城区"},{"id":"12370493166429532647","title":"满恒记牛街清真小吃","address":"北京市西城区平安里西大街13号","category":"美食:清真","type":0,"location":{"lat":39.93216,"lng":116.36765},"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"},{"id":"7334967656608876089","title":"聚宝源火锅城","address":"北京市西城区牛街5-2号","category":"美食:火锅","type":0,"location":{"lat":39.88677,"lng":116.36328},"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"},{"id":"4062879599476274838","title":"海底捞火锅(西单店)","address":"北京市西城区西单北大街109号西单婚庆大楼7层","category":"美食:火锅","type":0,"location":{"lat":39.9139,"lng":116.3732},"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"},{"id":"13969943039479527053","title":"南京大牌档(西直门凯德店)","address":"北京市西城区西直门外大街1号院凯德MALL·西直门5层L5-01B","category":"美食:中餐厅:南京菜","type":0,"location":{"lat":39.94149,"lng":116.352867},"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"},{"id":"12080302990753075447","title":"郁陵岛炭火烤肉","address":"北京市海淀区恩济庄18号院(近恩济里小区南门)","category":"美食:日韩菜:韩国料理","type":0,"location":{"lat":39.92738,"lng":116.28671},"adcode":110108,"province":"北京市","city":"北京市","district":"海淀区"},{"id":"17003011861735444102","title":"金鼎轩(亚运村店)","address":"北京市朝阳区安慧北里逸园15号","category":"美食:小吃快餐","type":0,"location":{"lat":40.00253,"lng":116.41298},"adcode":110105,"province":"北京市","city":"北京市","district":"朝阳区"},{"id":"5752080184187445609","title":"护国寺小吃店(护国寺街店)","address":"北京市西城区护国寺街68号","category":"美食:小吃快餐","type":0,"location":{"lat":39.93487,"lng":116.37529},"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"},{"id":"13774482554307834979","title":"海底捞火锅(牡丹园店)","address":"北京市海淀区花园东路2号(牡丹宾馆北200米)","category":"美食:火锅","type":0,"location":{"lat":39.97884,"lng":116.36849},"adcode":110108,"province":"北京市","city":"北京市","district":"海淀区"}]
     * request_id : 8128940993750671851
     */

    private int status;
    private String message;
    private int count;
    private String request_id;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 14287485404961640880
         * title : 局气(五道口店)
         * address : 北京市海淀区城府路28号五道口购物中心F6
         * category : 美食:中餐厅:北京菜
         * type : 0
         * location : {"lat":39.99152,"lng":116.339438}
         * adcode : 110108
         * province : 北京市
         * city : 北京市
         * district : 海淀区
         */

        private String id;
        private String title;
        private String address;
        private String category;
        private int type;
        private LocationBean location;
        private int adcode;
        private String province;
        private String city;
        private String district;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public int getAdcode() {
            return adcode;
        }

        public void setAdcode(int adcode) {
            this.adcode = adcode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

    }
}
