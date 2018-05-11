package com.yilaole.bean.institution;

import java.io.Serializable;

/**
 * 上传参数bean
 */

public class PostFilterBean implements Serializable {
    int provinceId;
    int cityId;
    int areaId;

    int typeId;
    int propertyId;
    int bedId;

    int priceId;
    int objectId;
    int orderId;
    int totle;
    int size;
    String featureId;
    String token;

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public int getBedId() {
        return bedId;
    }

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotle() {
        return totle;
    }

    public void setTotle(int totle) {
        this.totle = totle;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PostFilterBean{" +
                "provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", areaId=" + areaId +
                ", typeId=" + typeId +
                ", propertyId=" + propertyId +
                ", bedId=" + bedId +
                ", priceId=" + priceId +
                ", objectId=" + objectId +
                ", orderId=" + orderId +
                ", totle=" + totle +
                ", size=" + size +
                ", featureId='" + featureId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
