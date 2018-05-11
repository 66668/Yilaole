package com.yilaole.bean.mine;

import java.io.Serializable;

/**
 * 我的收藏 机构bean
 *
 */

public class MineCollectInstituteBean implements Serializable {

    private int agencyid;
    private String name;
    private String address;
    private String shop_price;
    private int status;
    private int property;
    private String market_pric;
    private String image_logo;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
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

    public int getId() {
        return agencyid;
    }

    public void setId(int id) {
        this.agencyid = id;
    }
}
