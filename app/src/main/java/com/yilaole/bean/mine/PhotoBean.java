package com.yilaole.bean.mine;

import java.io.Serializable;

/**
 * 头像bean
 */

public class PhotoBean implements Serializable {
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
