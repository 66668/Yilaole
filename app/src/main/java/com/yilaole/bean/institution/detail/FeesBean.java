package com.yilaole.bean.institution.detail;

import java.io.Serializable;
import java.util.List;

/**
 * 机构资质
 */

public class FeesBean implements Serializable {
    private int id;
    private List<FeesDetailBean> detail;

    public static class FeesDetailBean implements Serializable  {
        private String type;
        private String price;
        private String unit;
        private String feename;
        private String feediscribe;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getFeename() {
            return feename;
        }

        public void setFeename(String feename) {
            this.feename = feename;
        }

        public String getFeediscribe() {
            return feediscribe;
        }

        public void setFeediscribe(String feediscribe) {
            this.feediscribe = feediscribe;
        }

        @Override
        public String toString() {
            return "FeesDetailBean{" +
                    "type='" + type + '\'' +
                    ", price='" + price + '\'' +
                    ", unit='" + unit + '\'' +
                    ", feename='" + feename + '\'' +
                    ", feediscribe='" + feediscribe + '\'' +
                    '}';
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FeesDetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<FeesDetailBean> detail) {
        this.detail = detail;
    }
}
