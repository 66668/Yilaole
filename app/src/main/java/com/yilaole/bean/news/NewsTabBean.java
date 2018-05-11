package com.yilaole.bean.news;

import java.io.Serializable;

/**
 * 资讯tab数据
 */

public class NewsTabBean implements Serializable {
    String name;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NewsTabBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
