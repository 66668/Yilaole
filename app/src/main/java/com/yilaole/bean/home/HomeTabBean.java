package com.yilaole.bean.home;

import java.io.Serializable;

/**
 * tab数据
 */

public class HomeTabBean implements Serializable {
    String name;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "HomeTabBean{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
