package com.yilaole.bean.home;

import java.io.Serializable;

/**
 * 首页 轮播图
 */

public class BannerBean implements Serializable {
    String image_url;
    String link_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "image_url='" + image_url + '\'' +
                ", link_url='" + link_url + '\'' +
                '}';
    }
}
