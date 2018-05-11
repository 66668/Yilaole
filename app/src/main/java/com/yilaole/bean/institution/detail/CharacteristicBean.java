package com.yilaole.bean.institution.detail;

import java.io.Serializable;
import java.util.List;

/**
 * 医养特色
 */

public class CharacteristicBean implements Serializable {
    private int id;
    private List<CharacteristicDetailBean> detail;

    public static class CharacteristicDetailBean implements Serializable {
        private String title;
        private String content;
        private String image;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "CharacteristicDetailBean{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CharacteristicDetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<CharacteristicDetailBean> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "CharacteristicBean{" +
                "id=" + id +
                ", detail=" + detail +
                '}';
    }
}
