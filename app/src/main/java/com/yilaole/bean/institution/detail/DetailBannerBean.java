package com.yilaole.bean.institution.detail;

import java.io.Serializable;

/**
 * 详情banner
 */

public class DetailBannerBean implements Serializable {
    String image;
    String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DetailBannerBean{" +
                "image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
