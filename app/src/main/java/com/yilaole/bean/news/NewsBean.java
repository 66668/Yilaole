package com.yilaole.bean.news;

import com.yilaole.base.adapterbase.entity.MultiItemEntity;

import java.io.Serializable;

import static android.R.attr.name;

/**
 * 资讯bean
 */

public class NewsBean implements Serializable, MultiItemEntity {
    int id;
    int type;
    String title;
    String image;
    String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "id=" + id +
                ", title='" + name + '\'' +
                ", image='" + image + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return type;
    }
}
