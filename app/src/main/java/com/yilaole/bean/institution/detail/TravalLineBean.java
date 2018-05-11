package com.yilaole.bean.institution.detail;

import java.io.Serializable;

/**
 * 乘车线路
 */

public class TravalLineBean implements Serializable {
    private int id;
    private String travel;
    private String lng;
    private String lat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
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

    @Override
    public String toString() {
        return "TravalLineBean{" +
                "id=" + id +
                ", travel='" + travel + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
