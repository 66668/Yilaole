package com.yilaole.bean.institution.detail;

import java.io.Serializable;
import java.util.List;

/**
 * 机构资质
 */

public class QualificationBean implements Serializable {
    private int id;
    private List<IMGS> image;

    public static class IMGS implements Serializable  {
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "IMGS{" +
                    "image='" + image + '\'' +
                    '}';
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<IMGS> getImage() {
        return image;
    }

    public void setImage(List<IMGS> image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "QualificationBean{" +
                "id=" + id +
                ", image=" + image +
                '}';
    }
}
