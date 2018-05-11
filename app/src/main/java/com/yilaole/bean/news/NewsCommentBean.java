package com.yilaole.bean.news;

import java.io.Serializable;
import java.util.List;

/**
 * 资讯评论
 */

public class NewsCommentBean implements Serializable {
    int id;
    int article_id;//文章id
    int user_id;//
    String content;
    String pid;//父级id(该字段可忽略
    String create_time;
    String name;
    List<NewsCommentBackBean> child;

    public static class NewsCommentBackBean implements Serializable {

        int id;
        int article_id;//文章id
        int user_id;//
        String content;
        String pid;//父级id(该字段可忽略
        String create_time;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "NewsCommentBackBean{" +
                    "id=" + id +
                    ", article_id=" + article_id +
                    ", user_id=" + user_id +
                    ", content='" + content + '\'' +
                    ", pid='" + pid + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NewsCommentBackBean> getChild() {
        return child;
    }

    public void setChild(List<NewsCommentBackBean> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "NewsCommentBean{" +
                "id=" + id +
                ", article_id=" + article_id +
                ", user_id=" + user_id +
                ", content='" + content + '\'' +
                ", pid='" + pid + '\'' +
                ", create_time='" + create_time + '\'' +
                ", name='" + name + '\'' +
                ", child=" + child +
                '}';
    }
}
