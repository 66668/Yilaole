package com.yilaole.bean.institution.detail;

import java.io.Serializable;

/**
 * 机构详情/我的 上门评估/我的 预约参观
 * <p>
 * 发表评论的暂存bean
 */

public class PostCommentBean implements Serializable {
    private int id;
    private int agency_score;
    private int servoce_score;
    private int type;//0,预约参观评论需要传1，上门评估评论为2
    private int order_id;//订单id
    private String token;
    private String content;
    private Object file0;
    private Object file1;
    private Object file2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgency_score() {
        return agency_score;
    }

    public void setAgency_score(int agency_score) {
        this.agency_score = agency_score;
    }

    public int getServoce_score() {
        return servoce_score;
    }

    public void setServoce_score(int servoce_score) {
        this.servoce_score = servoce_score;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getFile0() {
        return file0;
    }

    public void setFile0(Object file0) {
        this.file0 = file0;
    }

    public Object getFile1() {
        return file1;
    }

    public void setFile1(Object file1) {
        this.file1 = file1;
    }

    public Object getFile2() {
        return file2;
    }

    public void setFile2(Object file2) {
        this.file2 = file2;
    }
}
