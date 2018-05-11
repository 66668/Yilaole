package com.yilaole.bean.mine;

import java.io.Serializable;

/**
 * 我的-上门评估详情
 */

public class DoorAssessDetailBean implements Serializable {
    private int id;
    private int agencyid;
    private int orderstate;
    private int is_comment;
    private String user_name;
    private String phone;
    private String orderid;
    private String agencyname;
    private String time;
    private String address;
    private String agreetime;
    private String refusetime;
    private String visittime;
    private String evalname;
    private String evalresult;
    private String remark;
    private String order_time;
    private String status;
    private String old_name;
    private String birthday;

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

    public int getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(int orderstate) {
        this.orderstate = orderstate;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getVisittime() {
        return visittime;
    }

    public void setVisittime(String visittime) {
        this.visittime = visittime;
    }

    public String getEvalname() {
        return evalname;
    }

    public void setEvalname(String evalname) {
        this.evalname = evalname;
    }

    public String getEvalresult() {
        return evalresult;
    }

    public void setEvalresult(String evalresult) {
        this.evalresult = evalresult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOld_name() {
        return old_name;
    }

    public void setOld_name(String old_name) {
        this.old_name = old_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
