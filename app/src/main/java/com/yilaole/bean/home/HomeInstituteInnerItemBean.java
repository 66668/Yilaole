package com.yilaole.bean.home;

import java.io.Serializable;

/**
 * tab对应的list数据
 */

public class HomeInstituteInnerItemBean implements Serializable {
    int agencyid;
    String shop_price;
    String name;
    String address;
    String status;
    String property;
    String market_pric;
    String image_logo;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMarket_pric() {
        return market_pric;
    }

    public void setMarket_pric(String market_pric) {
        this.market_pric = market_pric;
    }

    public String getImage_logo() {
        return image_logo;
    }

    public void setImage_logo(String image_logo) {
        this.image_logo = image_logo;
    }

    @Override
    public String toString() {
        return "HomeTabChildDataBean{" +
                "agencyid='" + agencyid + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", property='" + property + '\'' +
                ", market_pric='" + market_pric + '\'' +
                ", image_logo='" + image_logo + '\'' +
                '}';
    }
}
