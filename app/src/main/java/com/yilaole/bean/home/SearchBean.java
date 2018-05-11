package com.yilaole.bean.home;

import java.io.Serializable;

/**
 *
 */

public class SearchBean implements Serializable {
    String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String bannerBean) {
        keywords = bannerBean;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "keywords='" + keywords + '\'' +
                '}';
    }
}
