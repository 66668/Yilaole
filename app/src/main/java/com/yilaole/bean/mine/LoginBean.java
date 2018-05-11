package com.yilaole.bean.mine;

import java.io.Serializable;

/**
 * 登录返回数据的bean
 */

public class LoginBean implements Serializable {
    int expires;
    String name;
    String image;
    String token;

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

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

    @Override
    public String toString() {
        return "LoginBean{" +
                "expires=" + expires +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
