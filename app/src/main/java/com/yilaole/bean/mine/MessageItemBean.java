package com.yilaole.bean.mine;

import java.io.Serializable;

/**
 * 我的消息 listitem bean
 */

public class MessageItemBean implements Serializable {
    private int id;
    private String imgPath;
    private String name;
    private String time;


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
