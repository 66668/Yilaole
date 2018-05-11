package com.yilaole.bean.home;

import java.io.Serializable;

/**
 * 资讯bean
 */

public class HomeNewsBean implements Serializable {
    int id;
    String logo_url;
    String title  ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "HomeNewsBean{" +
                "id=" + id +
                ", logo_url='" + logo_url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
