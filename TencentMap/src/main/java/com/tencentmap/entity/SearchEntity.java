package com.tencentmap.entity;

import java.util.List;

/**
 * Created by JayLer on 2019/5/15.
 */
public class SearchEntity extends BaseMapEntity{


    /**
     * status : 0
     * message : query ok
     * count : 8
     * request_id : 119129075024f7a8682ccfd7e6bc764f7da57933cc0e
     * data : [{"id":"6695052091083729501","title":"肯德基(华南大厦餐厅)洗手间","address":"北京市西城区西单北大街176号中友百货地下1层","tel":" ","category":"基础设施:公共设施:公共厕所","type":0,"location":{"lat":39.909527,"lng":116.374374},"_distance":115.57,"ad_info":{"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}},{"id":"5371594408805356897","title":"肯德基(新一代大厦餐厅)","address":"北京市西城区堂子胡同9号西单新一代商城F1","tel":"010-66017780","category":"美食:小吃快餐","type":0,"location":{"lat":39.911237,"lng":116.375139},"_distance":313.45,"ad_info":{"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}},{"id":"10789117468670417413","title":"肯德基","address":"北京市西城区堂子胡同9号西单新一代商城B1","tel":" ","category":"美食:小吃快餐","type":0,"location":{"lat":39.911265,"lng":116.375212},"_distance":318.25,"ad_info":{"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}},{"id":"17072044082533233922","title":"肯德基(新一代餐厅)洗手间","address":"北京市西城区西单北大街堂子胡同9号新一代商城B1楼","tel":" ","category":"基础设施:公共设施:公共厕所","type":0,"location":{"lat":39.91143,"lng":116.375572},"_distance":344.19,"ad_info":{"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}},{"id":"15797622414471794211","title":"肯德基(西单商场店)","address":"北京市西城区西单北大街118号地下1层","tel":"010-66012133","category":"美食:小吃快餐","type":0,"location":{"lat":39.91314,"lng":116.37429},"_distance":517.66,"ad_info":{"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}},{"id":"1795390499678668454","title":"肯德基(西单餐厅)","address":"北京市西城区西单北大街120号西单商场B1","tel":" ","category":"美食:小吃快餐","type":0,"location":{"lat":39.913233,"lng":116.374349},"_distance":527.84,"ad_info":{"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}},{"id":"4838433557157783667","title":"肯德基(西单商场餐厅)洗手间","address":"北京市西城区西单北大街112号附近","tel":" ","category":"基础设施:公共设施:公共厕所","type":0,"location":{"lat":39.913517,"lng":116.374164},"_distance":559.87,"ad_info":{"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}},{"id":"18305950192850968709","title":"肯德基(西单商场餐厅)洗手间","address":"北京市西城区西单北大街118号","tel":" ","category":"基础设施:公共设施:公共厕所","type":0,"location":{"lat":39.913517,"lng":116.374161},"_distance":559.87,"ad_info":{"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}}]
     * region : {"title":"中国"}
     */

    private String request_id;
    private RegionBean region;
    private List<DataBean> data;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public RegionBean getRegion() {
        return region;
    }

    public void setRegion(RegionBean region) {
        this.region = region;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class RegionBean {
        /**
         * title : 中国
         */

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class DataBean {
        /**
         * id : 6695052091083729501
         * title : 肯德基(华南大厦餐厅)洗手间
         * address : 北京市西城区西单北大街176号中友百货地下1层
         * tel :
         * category : 基础设施:公共设施:公共厕所
         * type : 0
         * location : {"lat":39.909527,"lng":116.374374}
         * _distance : 115.57
         * ad_info : {"adcode":110102,"province":"北京市","city":"北京市","district":"西城区"}
         */

        private String id;
        private String title;
        private String address;
        private String tel;
        private String category;
        private int type;
        private LocationBean location;
        private double _distance;
        private AdInfoBean ad_info;

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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
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

        public double get_distance() {
            return _distance;
        }

        public void set_distance(double _distance) {
            this._distance = _distance;
        }

        public AdInfoBean getAd_info() {
            return ad_info;
        }

        public void setAd_info(AdInfoBean ad_info) {
            this.ad_info = ad_info;
        }


        public static class AdInfoBean {
            /**
             * adcode : 110102
             * province : 北京市
             * city : 北京市
             * district : 西城区
             */

            private int adcode;
            private String province;
            private String city;
            private String district;

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
}
