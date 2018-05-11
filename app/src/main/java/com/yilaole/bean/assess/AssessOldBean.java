package com.yilaole.bean.assess;

import java.io.Serializable;

/**
 * 在线评首页 老人bean
 */

public class AssessOldBean implements Serializable {
    int id;
    String imgPath;
    String birhday;
    String sex;
    String name;

    public AssessOldBean(String name, String sex, String imgPath, String birhday) {
        this.name = name;
        this.birhday = birhday;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirhday() {
        return birhday;
    }

    public void setBirhday(String birhday) {
        this.birhday = birhday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "AssessOldBean{" +
                "id=" + id +
                ", imgPath='" + imgPath + '\'' +
                ", birhday='" + birhday + '\'' +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
