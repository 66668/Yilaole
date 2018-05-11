package com.yilaole.bean.mine;

import java.io.Serializable;

/**
 * 我的-预约参观详情
 */

public class AppointVisitDetailBean implements Serializable {
    private int id;
    private int agencyid;
    private int number;
    private int orderstate;
    private String user_name;
    private String phone;
    private String orderid;
    private String agencyname;
    private String agencyaddress;
    private String time;
    private String name;
    private String age;
    private String visittime;
    private String order_time;
    private String agreetime;
    private String refusetime;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgencyid() {
        return agencyid;
    }

    public void setAgencyid(int agencyid) {
        this.agencyid = agencyid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(int orderstate) {
        this.orderstate = orderstate;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAgencyname() {
        return agencyname;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname;
    }

    public String getAgencyaddress() {
        return agencyaddress;
    }

    public void setAgencyaddress(String agencyaddress) {
        this.agencyaddress = agencyaddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
    }

    public String getOrdertime() {
        return order_time;
    }

    public void setOrdertime(String ordertime) {
        this.order_time = ordertime;
    }

    public String getAgreetime() {
        return agreetime;
    }

    public void setAgreetime(String agreetime) {
        this.agreetime = agreetime;
    }

    public String getRefusetime() {
        return refusetime;
    }

    public void setRefusetime(String refusetime) {
        this.refusetime = refusetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
