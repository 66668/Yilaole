package com.yilaole.bean.news;

import java.io.Serializable;
import java.util.List;

/**
 * 资讯详情
 */

public class NewsDetailBean implements Serializable {
    int id;
    String title;
    String image;//https://user.efulai.cn
    String author;
    String content;
    String time;
    int support_number;
    int is_support;//是否点赞 0 没有 1 已赞
    List<String> label;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSupport_number() {
        return support_number;
    }

    public void setSupport_number(int support_number) {
        this.support_number = support_number;
    }

    public int getIs_support() {
        return is_support;
    }

    public void setIs_support(int is_support) {
        this.is_support = is_support;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "NewsDetailBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", support_number=" + support_number +
                ", is_support=" + is_support +
                ", label=" + label +
                '}';
    }
}
