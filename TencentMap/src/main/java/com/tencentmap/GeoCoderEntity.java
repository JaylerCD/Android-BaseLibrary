package com.tencentmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JayLer on 2019/5/17.
 */
public class GeoCoderEntity {


    /**
     * status : 0
     * message : query ok
     * request_id : 3a4e9008-784e-11e9-b7cb-6c92bf93b531
     * result : {"location":{"lat":39.984154,"lng":116.30749},"address":"北京市海淀区北四环西路66号","formatted_addresses":{"recommend":"海淀区中关村中国技术交易大厦","rough":"海淀区中关村中国技术交易大厦"},"address_component":{"nation":"中国","province":"北京市","city":"北京市","district":"海淀区","street":"北四环西路","street_number":"北四环西路66号"},"ad_info":{"nation_code":"156","adcode":"110108","city_code":"156110000","name":"中国,北京市,北京市,海淀区","location":{"lat":40.045132,"lng":116.375},"nation":"中国","province":"北京市","city":"北京市","district":"海淀区"},"address_reference":{"business_area":{"id":"14178584199053362783","title":"中关村","location":{"lat":39.980598,"lng":116.310997},"_distance":0,"_dir_desc":"内"},"famous_area":{"id":"14178584199053362783","title":"中关村","location":{"lat":39.980598,"lng":116.310997},"_distance":0,"_dir_desc":"内"},"crossroad":{"id":"529981","title":"彩和坊路/北四环西路辅路(路口)","location":{"lat":39.985001,"lng":116.308113},"_distance":102.8,"_dir_desc":"西南"},"town":{"id":"110108012","title":"海淀街道","location":{"lat":39.974819,"lng":116.284409},"_distance":0,"_dir_desc":"内"},"street_number":{"id":"6996505596656075740","title":"北四环西路66号","location":{"lat":39.984119,"lng":116.307503},"_distance":1.7,"_dir_desc":""},"street":{"id":"7465477252594362897","title":"北四环","location":{"lat":39.987461,"lng":116.376266},"_distance":96.1,"_dir_desc":"南"},"landmark_l2":{"id":"3629720141162880123","title":"中国技术交易大厦","location":{"lat":39.984104,"lng":116.307503},"_distance":0,"_dir_desc":"内"}},"poi_count":10,"pois":[{"id":"3629720141162880123","title":"中国技术交易大厦","address":"北京市海淀区北四环西路66号","category":"房产小区:商务楼宇","location":{"lat":39.984104,"lng":116.307503},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":0,"_dir_desc":"内"},{"id":"9969038414753335812","title":"腾讯科技(北京)有限公司(中国技术交易大厦)","address":"北京市海淀区北四环西路66号中国技术交易大厦","category":"公司企业:公司企业","location":{"lat":39.984131,"lng":116.307503},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":2.9,"_dir_desc":""},{"id":"13477589832396847863","title":"品·咖啡","address":"北京市海淀区北四环西路66号中国技术交易大厦1楼大厅内","category":"娱乐休闲:咖啡厅","location":{"lat":39.984184,"lng":116.307335},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":13.5,"_dir_desc":""},{"id":"2845372667492951071","title":"中国技术交易大厦A座","address":"北京市海淀区北四环西路66号","category":"房产小区:商务楼宇","location":{"lat":39.984329,"lng":116.307419},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":20.4,"_dir_desc":""},{"id":"12925244666643621769","title":"中国技术交易大厦B座","address":"北京市海淀区北四环西路66号","category":"房产小区:商务楼宇","location":{"lat":39.983906,"lng":116.307556},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":28.2,"_dir_desc":""},{"id":"3724888736111897241","title":"万学教育·海文考研","address":"北京市海淀区北三环西路66号中国技术交易大厦17层","category":"教育学校:培训","location":{"lat":39.984112,"lng":116.307587},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":9.7,"_dir_desc":""},{"id":"14934388099779470343","title":"潘师傅红烧肉(中国技术交易大厦店)","address":"北京市海淀区北四环西路66号中国技术交易大厦地下1层","category":"美食:小吃快餐","location":{"lat":39.984112,"lng":116.307587},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":9.7,"_dir_desc":""},{"id":"2740626921918730425","title":"水果蔬菜沙拉","address":"北京市海淀区北四环西路66号B1层","category":"购物:农贸市场","location":{"lat":39.984138,"lng":116.307587},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":8.6,"_dir_desc":""},{"id":"13113576790131482342","title":"中国技术交易所","address":"北京市海淀区北四环西路66号中国技术交易大厦B座16层","category":"机构团体:政府机关","location":{"lat":39.984112,"lng":116.307587},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":9.7,"_dir_desc":""},{"id":"3187032738687555052","title":"中关村创业大街","address":"北京市海淀区海淀西大街","category":"购物:商业步行街","location":{"lat":39.983582,"lng":116.306824},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":36.3,"_dir_desc":"东北"}]}
     */

    private int status;
    private String message;
    private String request_id;
    private ResultBean result;

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

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * location : {"lat":39.984154,"lng":116.30749}
         * address : 北京市海淀区北四环西路66号
         * formatted_addresses : {"recommend":"海淀区中关村中国技术交易大厦","rough":"海淀区中关村中国技术交易大厦"}
         * address_component : {"nation":"中国","province":"北京市","city":"北京市","district":"海淀区","street":"北四环西路","street_number":"北四环西路66号"}
         * ad_info : {"nation_code":"156","adcode":"110108","city_code":"156110000","name":"中国,北京市,北京市,海淀区","location":{"lat":40.045132,"lng":116.375},"nation":"中国","province":"北京市","city":"北京市","district":"海淀区"}
         * address_reference : {"business_area":{"id":"14178584199053362783","title":"中关村","location":{"lat":39.980598,"lng":116.310997},"_distance":0,"_dir_desc":"内"},"famous_area":{"id":"14178584199053362783","title":"中关村","location":{"lat":39.980598,"lng":116.310997},"_distance":0,"_dir_desc":"内"},"crossroad":{"id":"529981","title":"彩和坊路/北四环西路辅路(路口)","location":{"lat":39.985001,"lng":116.308113},"_distance":102.8,"_dir_desc":"西南"},"town":{"id":"110108012","title":"海淀街道","location":{"lat":39.974819,"lng":116.284409},"_distance":0,"_dir_desc":"内"},"street_number":{"id":"6996505596656075740","title":"北四环西路66号","location":{"lat":39.984119,"lng":116.307503},"_distance":1.7,"_dir_desc":""},"street":{"id":"7465477252594362897","title":"北四环","location":{"lat":39.987461,"lng":116.376266},"_distance":96.1,"_dir_desc":"南"},"landmark_l2":{"id":"3629720141162880123","title":"中国技术交易大厦","location":{"lat":39.984104,"lng":116.307503},"_distance":0,"_dir_desc":"内"}}
         * poi_count : 10
         * pois : [{"id":"3629720141162880123","title":"中国技术交易大厦","address":"北京市海淀区北四环西路66号","category":"房产小区:商务楼宇","location":{"lat":39.984104,"lng":116.307503},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":0,"_dir_desc":"内"},{"id":"9969038414753335812","title":"腾讯科技(北京)有限公司(中国技术交易大厦)","address":"北京市海淀区北四环西路66号中国技术交易大厦","category":"公司企业:公司企业","location":{"lat":39.984131,"lng":116.307503},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":2.9,"_dir_desc":""},{"id":"13477589832396847863","title":"品·咖啡","address":"北京市海淀区北四环西路66号中国技术交易大厦1楼大厅内","category":"娱乐休闲:咖啡厅","location":{"lat":39.984184,"lng":116.307335},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":13.5,"_dir_desc":""},{"id":"2845372667492951071","title":"中国技术交易大厦A座","address":"北京市海淀区北四环西路66号","category":"房产小区:商务楼宇","location":{"lat":39.984329,"lng":116.307419},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":20.4,"_dir_desc":""},{"id":"12925244666643621769","title":"中国技术交易大厦B座","address":"北京市海淀区北四环西路66号","category":"房产小区:商务楼宇","location":{"lat":39.983906,"lng":116.307556},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":28.2,"_dir_desc":""},{"id":"3724888736111897241","title":"万学教育·海文考研","address":"北京市海淀区北三环西路66号中国技术交易大厦17层","category":"教育学校:培训","location":{"lat":39.984112,"lng":116.307587},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":9.7,"_dir_desc":""},{"id":"14934388099779470343","title":"潘师傅红烧肉(中国技术交易大厦店)","address":"北京市海淀区北四环西路66号中国技术交易大厦地下1层","category":"美食:小吃快餐","location":{"lat":39.984112,"lng":116.307587},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":9.7,"_dir_desc":""},{"id":"2740626921918730425","title":"水果蔬菜沙拉","address":"北京市海淀区北四环西路66号B1层","category":"购物:农贸市场","location":{"lat":39.984138,"lng":116.307587},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":8.6,"_dir_desc":""},{"id":"13113576790131482342","title":"中国技术交易所","address":"北京市海淀区北四环西路66号中国技术交易大厦B座16层","category":"机构团体:政府机关","location":{"lat":39.984112,"lng":116.307587},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":9.7,"_dir_desc":""},{"id":"3187032738687555052","title":"中关村创业大街","address":"北京市海淀区海淀西大街","category":"购物:商业步行街","location":{"lat":39.983582,"lng":116.306824},"ad_info":{"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"},"_distance":36.3,"_dir_desc":"东北"}]
         */

        private LocationBean location;
        private String address;
        private FormattedAddressesBean formatted_addresses;
        private AddressComponentBean address_component;
        private AdInfoBean ad_info;
        private AddressReferenceBean address_reference;
        private int poi_count;
        private List<PoisBean> pois;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public FormattedAddressesBean getFormatted_addresses() {
            return formatted_addresses;
        }

        public void setFormatted_addresses(FormattedAddressesBean formatted_addresses) {
            this.formatted_addresses = formatted_addresses;
        }

        public AddressComponentBean getAddress_component() {
            return address_component;
        }

        public void setAddress_component(AddressComponentBean address_component) {
            this.address_component = address_component;
        }

        public AdInfoBean getAd_info() {
            return ad_info;
        }

        public void setAd_info(AdInfoBean ad_info) {
            this.ad_info = ad_info;
        }

        public AddressReferenceBean getAddress_reference() {
            return address_reference;
        }

        public void setAddress_reference(AddressReferenceBean address_reference) {
            this.address_reference = address_reference;
        }

        public int getPoi_count() {
            return poi_count;
        }

        public void setPoi_count(int poi_count) {
            this.poi_count = poi_count;
        }

        public List<PoisBean> getPois() {
            return pois;
        }

        public void setPois(List<PoisBean> pois) {
            this.pois = pois;
        }

        public static class FormattedAddressesBean {
            /**
             * recommend : 海淀区中关村中国技术交易大厦
             * rough : 海淀区中关村中国技术交易大厦
             */

            private String recommend;
            private String rough;

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public String getRough() {
                return rough;
            }

            public void setRough(String rough) {
                this.rough = rough;
            }
        }

        public static class AddressComponentBean {
            /**
             * nation : 中国
             * province : 北京市
             * city : 北京市
             * district : 海淀区
             * street : 北四环西路
             * street_number : 北四环西路66号
             */

            private String nation;
            private String province;
            private String city;
            private String district;
            private String street;
            private String street_number;

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
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

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }
        }

        public static class AdInfoBean {
            /**
             * nation_code : 156
             * adcode : 110108
             * city_code : 156110000
             * name : 中国,北京市,北京市,海淀区
             * location : {"lat":40.045132,"lng":116.375}
             * nation : 中国
             * province : 北京市
             * city : 北京市
             * district : 海淀区
             */

            private String nation_code;
            private String adcode;
            private String city_code;
            private String name;
            private LocationBean location;
            private String nation;
            private String province;
            private String city;
            private String district;

            public String getNation_code() {
                return nation_code;
            }

            public void setNation_code(String nation_code) {
                this.nation_code = nation_code;
            }

            public String getAdcode() {
                return adcode;
            }

            public void setAdcode(String adcode) {
                this.adcode = adcode;
            }

            public String getCity_code() {
                return city_code;
            }

            public void setCity_code(String city_code) {
                this.city_code = city_code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getNation() {
                return nation;
            }

            public void setNation(String nation) {
                this.nation = nation;
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

        public static class AddressReferenceBean {
            /**
             * business_area : {"id":"14178584199053362783","title":"中关村","location":{"lat":39.980598,"lng":116.310997},"_distance":0,"_dir_desc":"内"}
             * famous_area : {"id":"14178584199053362783","title":"中关村","location":{"lat":39.980598,"lng":116.310997},"_distance":0,"_dir_desc":"内"}
             * crossroad : {"id":"529981","title":"彩和坊路/北四环西路辅路(路口)","location":{"lat":39.985001,"lng":116.308113},"_distance":102.8,"_dir_desc":"西南"}
             * town : {"id":"110108012","title":"海淀街道","location":{"lat":39.974819,"lng":116.284409},"_distance":0,"_dir_desc":"内"}
             * street_number : {"id":"6996505596656075740","title":"北四环西路66号","location":{"lat":39.984119,"lng":116.307503},"_distance":1.7,"_dir_desc":""}
             * street : {"id":"7465477252594362897","title":"北四环","location":{"lat":39.987461,"lng":116.376266},"_distance":96.1,"_dir_desc":"南"}
             * landmark_l2 : {"id":"3629720141162880123","title":"中国技术交易大厦","location":{"lat":39.984104,"lng":116.307503},"_distance":0,"_dir_desc":"内"}
             */

            private BusinessAreaBean business_area;
            private FamousAreaBean famous_area;
            private CrossroadBean crossroad;
            private TownBean town;
            private StreetNumberBean street_number;
            private StreetBean street;
            private LandmarkL2Bean landmark_l2;

            public BusinessAreaBean getBusiness_area() {
                return business_area;
            }

            public void setBusiness_area(BusinessAreaBean business_area) {
                this.business_area = business_area;
            }

            public FamousAreaBean getFamous_area() {
                return famous_area;
            }

            public void setFamous_area(FamousAreaBean famous_area) {
                this.famous_area = famous_area;
            }

            public CrossroadBean getCrossroad() {
                return crossroad;
            }

            public void setCrossroad(CrossroadBean crossroad) {
                this.crossroad = crossroad;
            }

            public TownBean getTown() {
                return town;
            }

            public void setTown(TownBean town) {
                this.town = town;
            }

            public StreetNumberBean getStreet_number() {
                return street_number;
            }

            public void setStreet_number(StreetNumberBean street_number) {
                this.street_number = street_number;
            }

            public StreetBean getStreet() {
                return street;
            }

            public void setStreet(StreetBean street) {
                this.street = street;
            }

            public LandmarkL2Bean getLandmark_l2() {
                return landmark_l2;
            }

            public void setLandmark_l2(LandmarkL2Bean landmark_l2) {
                this.landmark_l2 = landmark_l2;
            }

            public static class BusinessAreaBean {
                /**
                 * id : 14178584199053362783
                 * title : 中关村
                 * location : {"lat":39.980598,"lng":116.310997}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String id;
                private String title;
                private LocationBean location;
                private double _distance;
                private String _dir_desc;

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

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }
            }

            public static class FamousAreaBean {
                /**
                 * id : 14178584199053362783
                 * title : 中关村
                 * location : {"lat":39.980598,"lng":116.310997}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String id;
                private String title;
                private LocationBean location;
                private double _distance;
                private String _dir_desc;

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

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

            }

            public static class CrossroadBean {
                /**
                 * id : 529981
                 * title : 彩和坊路/北四环西路辅路(路口)
                 * location : {"lat":39.985001,"lng":116.308113}
                 * _distance : 102.8
                 * _dir_desc : 西南
                 */

                private String id;
                private String title;
                private LocationBean location;
                private double _distance;
                private String _dir_desc;

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

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }
            }

            public static class TownBean {
                /**
                 * id : 110108012
                 * title : 海淀街道
                 * location : {"lat":39.974819,"lng":116.284409}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String id;
                private String title;
                private LocationBean location;
                private double _distance;
                private String _dir_desc;

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

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }
            }

            public static class StreetNumberBean {
                /**
                 * id : 6996505596656075740
                 * title : 北四环西路66号
                 * location : {"lat":39.984119,"lng":116.307503}
                 * _distance : 1.7
                 * _dir_desc :
                 */

                private String id;
                private String title;
                private LocationBean location;
                private double _distance;
                private String _dir_desc;

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

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

            }

            public static class StreetBean {
                /**
                 * id : 7465477252594362897
                 * title : 北四环
                 * location : {"lat":39.987461,"lng":116.376266}
                 * _distance : 96.1
                 * _dir_desc : 南
                 */

                private String id;
                private String title;
                private LocationBean location;
                private double _distance;
                private String _dir_desc;

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

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

            }

            public static class LandmarkL2Bean {
                /**
                 * id : 3629720141162880123
                 * title : 中国技术交易大厦
                 * location : {"lat":39.984104,"lng":116.307503}
                 * _distance : 0
                 * _dir_desc : 内
                 */

                private String id;
                private String title;
                private LocationBean location;
                private double _distance;
                private String _dir_desc;

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

                public String get_dir_desc() {
                    return _dir_desc;
                }

                public void set_dir_desc(String _dir_desc) {
                    this._dir_desc = _dir_desc;
                }

            }
        }

        public static class PoisBean {
            /**
             * id : 3629720141162880123
             * title : 中国技术交易大厦
             * address : 北京市海淀区北四环西路66号
             * category : 房产小区:商务楼宇
             * location : {"lat":39.984104,"lng":116.307503}
             * ad_info : {"adcode":"110108","province":"北京市","city":"北京市","district":"海淀区"}
             * _distance : 0
             * _dir_desc : 内
             */

            private String id;
            private String title;
            private String address;
            private String category;
            private LocationBean location;
            private AdInfoBeanX ad_info;
            private double _distance;
            private String _dir_desc;

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

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public AdInfoBeanX getAd_info() {
                return ad_info;
            }

            public void setAd_info(AdInfoBeanX ad_info) {
                this.ad_info = ad_info;
            }

            public double get_distance() {
                return _distance;
            }

            public void set_distance(double _distance) {
                this._distance = _distance;
            }

            public String get_dir_desc() {
                return _dir_desc;
            }

            public void set_dir_desc(String _dir_desc) {
                this._dir_desc = _dir_desc;
            }

            public static class AdInfoBeanX {
                /**
                 * adcode : 110108
                 * province : 北京市
                 * city : 北京市
                 * district : 海淀区
                 */

                private String adcode;
                private String province;
                private String city;
                private String district;

                public String getAdcode() {
                    return adcode;
                }

                public void setAdcode(String adcode) {
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
}
