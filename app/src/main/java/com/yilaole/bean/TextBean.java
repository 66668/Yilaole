package com.yilaole.bean;

import java.io.Serializable;

/**
 * 假数据使用 开发完注意删除
 */

public class TextBean implements Serializable {
    //// TODO: 2017/10/9  删除
    int id;
    String imgPath;
    String name;
    String time;

    public TextBean(String name, String imgPath) {
        this.name = name;
        this.imgPath = imgPath;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
