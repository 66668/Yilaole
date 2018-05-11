package com.yilaole.bean.institution.detail;

import java.io.Serializable;
import java.util.List;

/**
 * 机构详情
 */

public class InstituteDetailBean implements Serializable {
    private int id;
    private int is_collect;//1收藏  2未收藏
    private int property;//1：民办：2公办
    private int status;//机构状态 0:未入驻，1:已认领，2:入驻
    private String name;
    private String address;
    private int bed;//床位
    private String opendate;//成立日期
    private String price;
    private String oldprice;
    private String agencydiscribe;
    private String logo_url;
    private String lng;
    private String lat;
    private String agencyphone;//机构电话

    private String agencytype;
    private String travel;//乘车路线
    private String panorama;//360度全景
    private String videodiscribe;//视频介绍
    private List<String> object;//收住对象
    private List<String> service;//机构特色
    private DeanInfo deaninfo;//院长介绍
    private List<IMG> image;//图片
    private Grade grade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public String getOpendate() {
        return opendate;
    }

    public void setOpendate(String opendate) {
        this.opendate = opendate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice;
    }

    public String getAgencydiscribe() {
        return agencydiscribe;
    }

    public void setAgencydiscribe(String agencydiscribe) {
        this.agencydiscribe = agencydiscribe;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAgencyphone() {
        return agencyphone;
    }

    public void setAgencyphone(String agencyphone) {
        this.agencyphone = agencyphone;
    }

    public String getAgencytype() {
        return agencytype;
    }

    public void setAgencytype(String agencytype) {
        this.agencytype = agencytype;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getPanorama() {
        return panorama;
    }

    public void setPanorama(String panorama) {
        this.panorama = panorama;
    }

    public String getVideodiscribe() {
        return videodiscribe;
    }

    public void setVideodiscribe(String videodiscribe) {
        this.videodiscribe = videodiscribe;
    }

    public List<String> getObject() {
        return object;
    }

    public void setObject(List<String> object) {
        this.object = object;
    }

    public List<String> getService() {
        return service;
    }

    public void setService(List<String> service) {
        this.service = service;
    }

    public DeanInfo getDeaninfo() {
        return deaninfo;
    }

    public void setDeaninfo(DeanInfo deaninfo) {
        this.deaninfo = deaninfo;
    }

    public List<IMG> getImage() {
        return image;
    }

    public void setImage(List<IMG> image) {
        this.image = image;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public static class DeanInfo implements Serializable {
        private String deanname;
        private String wishes;
        private String photo;
        private String wechat;

        public String getDeanname() {
            return deanname;
        }

        public void setDeanname(String deanname) {
            this.deanname = deanname;
        }

        public String getWishes() {
            return wishes;
        }

        public void setWishes(String wishes) {
            this.wishes = wishes;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        @Override
        public String toString() {
            return "DeanInfo{" +
                    "deanname='" + deanname + '\'' +
                    ", wishes='" + wishes + '\'' +
                    ", photo='" + photo + '\'' +
                    ", wechat='" + wechat + '\'' +
                    '}';
        }
    }

    public static class IMG implements Serializable {


        String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "IMG{" +
                    "image='" + image + '\'' +
                    '}';
        }
    }


    public static class Grade implements Serializable {
        private double info_score;
        private double comment_score;
        private double average_score;

        public double getInfo_score() {
            return info_score;
        }

        public void setInfo_score(double info_score) {
            this.info_score = info_score;
        }

        public double getComment_score() {
            return comment_score;
        }

        public void setComment_score(double comment_score) {
            this.comment_score = comment_score;
        }

        public double getAverage_score() {
            return average_score;
        }

        public void setAverage_score(double average_score) {
            this.average_score = average_score;
        }

        @Override
        public String toString() {
            return "Grade{" +
                    "info_score=" + info_score +
                    ", comment_score=" + comment_score +
                    ", average_score=" + average_score +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "InstituteDetailBean{" +
                "id=" + id +
                ", is_collect=" + is_collect +
                ", property=" + property +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", bed='" + bed + '\'' +
                ", opendate='" + opendate + '\'' +
                ", price='" + price + '\'' +
                ", oldprice='" + oldprice + '\'' +
                ", agencydiscribe='" + agencydiscribe + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", agencyphone='" + agencyphone + '\'' +
                ", agencytype='" + agencytype + '\'' +
                ", travel='" + travel + '\'' +
                ", panorama='" + panorama + '\'' +
                ", videodiscribe='" + videodiscribe + '\'' +
                ", object=" + object +
                ", service=" + service +
                ", deaninfo=" + deaninfo +
                ", image=" + image +
                ", grade=" + grade +
                '}';
    }
}
