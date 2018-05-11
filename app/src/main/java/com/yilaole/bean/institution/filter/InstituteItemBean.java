package com.yilaole.bean.institution.filter;

import java.io.Serializable;


/**
 * 机构列表数据
 */

public class InstituteItemBean implements Serializable {
    private int agencyid;//
    private int is_collect;//是否收藏 0:未收藏 1：已收藏
    private int status;//机构状态 0未入驻，1认领，2入驻
    private double num;//颐福来指数
    private int property;//机构性质 1：民办 2：公办
    private String name;
    private String address;
    private String logo_url;
    private String shop_price;
    private String oldprice;
    private String popularity;

    public int getAgencyid() {
        return agencyid;
    }

    public void setAgencyid(int agencyid) {
        this.agencyid = agencyid;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return agencyid;
    }

    public void setId(int id) {
        this.agencyid = id;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
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

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getPrice() {
        return shop_price;
    }

    public void setPrice(String price) {
        this.shop_price = price;
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice;
    }

    @Override
    public String toString() {
        return "InstituteItemBean{" +
                "agencyid=" + agencyid +
                ", is_collect=" + is_collect +
                ", status=" + status +
                ", num=" + num +
                ", property=" + property +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", oldprice='" + oldprice + '\'' +
                '}';
    }
}
