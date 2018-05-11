package com.yilaole.bean.institution.detail;

import java.io.Serializable;
import java.util.List;

/**
 * 机构详情 评论列表的item数据
 */

public class DetailCommentBean implements Serializable {
    private int id;
    private String commenttime;
    private String commentscore;
    private String servicescore;
    private String content;
    private String userid;
    private String name;
    private String img;
    private List<UserPic> image;
    //    private FadeBack child;


    public static class UserPic implements Serializable {
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "UserPic{" +
                    "image='" + image + '\'' +
                    '}';
        }
    }

    public static class FadeBack implements Serializable {
        private int id;
        private String commenttime;
        private String commenttype;
        private String servicescore;
        private String commentscore;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCommenttime() {
            return commenttime;
        }

        public void setCommenttime(String commenttime) {
            this.commenttime = commenttime;
        }

        public String getCommenttype() {
            return commenttype;
        }

        public void setCommenttype(String commenttype) {
            this.commenttype = commenttype;
        }

        public String getServicescore() {
            return servicescore;
        }

        public void setServicescore(String servicescore) {
            this.servicescore = servicescore;
        }

        public String getCommentscore() {
            return commentscore;
        }

        public void setCommentscore(String commentscore) {
            this.commentscore = commentscore;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "FadeBack{" +
                    "id=" + id +
                    ", commenttime='" + commenttime + '\'' +
                    ", commenttype='" + commenttype + '\'' +
                    ", servicescore='" + servicescore + '\'' +
                    ", commentscore='" + commentscore + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(String commenttime) {
        this.commenttime = commenttime;
    }

    public String getCommentscore() {
        return commentscore;
    }

    public void setCommentscore(String commentscore) {
        this.commentscore = commentscore;
    }

    public String getServicescore() {
        return servicescore;
    }

    public void setServicescore(String servicescore) {
        this.servicescore = servicescore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    //    public FadeBack getChild() {
    //        return child;
    //    }
    //
    //    public void setChild(FadeBack child) {
    //        this.child = child;
    //    }

    public List<UserPic> getImage() {
        return image;
    }

    public void setImage(List<UserPic> image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "DetailCommentBean{" +
                "id=" + id +
                ", commenttime='" + commenttime + '\'' +
                ", commentscore='" + commentscore + '\'' +
                ", servicescore='" + servicescore + '\'' +
                ", content='" + content + '\'' +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", image=" + image +
                //                ", child=" + child +
                '}';
    }
}
