package com.yilaole.bean.news;

import java.io.Serializable;

/**
 * 资讯页 轮播图
 */

public class NewsBannerBean implements Serializable {
    private String image;
    private String link_url;

    public String getImage() {
        return image;
    }

    public void setImage(String image_url) {
        this.image = image_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

}
