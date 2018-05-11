package com.yilaole.bean.mine;

import java.io.Serializable;

/**
 * 微信 绑定后台 返回
 */

public class WeichatBindResultBean implements Serializable {
    private String name;
    private String image;
    private String token;
    private int expires;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }
}
