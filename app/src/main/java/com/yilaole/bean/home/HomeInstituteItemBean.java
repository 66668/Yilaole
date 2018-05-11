package com.yilaole.bean.home;

import com.yilaole.base.adapterbase.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * tab对应的list数据
 * MultiItemEntity是baseRecyclerViewHelper的类
 */

public class HomeInstituteItemBean implements Serializable, MultiItemEntity {

    private int agencyid;
    private String shop_price;
    private String name;
    private String address;
    private int status;
    private int property;
    private String market_pric;
    private String image_logo;
    private String popularity;
    private int is_collect;
    private double number;
    private List<HomeInstituteInnerItemBean> child;

    //保存的本地参数 切换布局的判断参数
    private int itemType;


    public int getAgencyid() {
        return agencyid;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public List<HomeInstituteInnerItemBean> getChild() {
        return child;
    }

    public void setChild(List<HomeInstituteInnerItemBean> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "HomeTabDataBean{" +
                "agencyid='" + agencyid + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", property='" + property + '\'' +
                ", market_pric='" + market_pric + '\'' +
                ", image_logo='" + image_logo + '\'' +
                ", is_collect='" + is_collect + '\'' +
                ", child=" + child.toString() +
                '}';
    }

    /**
     * MultiItemEntity 重写接口的方法，获取 多布局样式viewType
     *
     * @return
     */
    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
