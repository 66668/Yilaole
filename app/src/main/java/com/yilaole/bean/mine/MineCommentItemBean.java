package com.yilaole.bean.mine;

import com.yilaole.base.adapterbase.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 我的评论itembean
 */

public class MineCommentItemBean implements Serializable, MultiItemEntity {
    private int id;
    private int agencyid;
    private String commenttime;
    private String content;
    private String agencyname;
    private String agencyaddress;
    private String agency_image;//https://image.efulai.cn
    private List<String> image;
    public MineCommentItemChildBean child;

    //保存的本地参数 切换布局的判断参数
    private int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public class MineCommentItemChildBean implements Serializable {
        private int id;
        private int agencyid;
        private String commenttime;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAgencyid() {
            return agencyid;
        }

        public void setAgencyid(int agencyid) {
            this.agencyid = agencyid;
        }

        public String getCommenttime() {
            return commenttime;
        }

        public void setCommenttime(String commenttime) {
            this.commenttime = commenttime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgencyid() {
        return agencyid;
    }

    public String getAgencyname() {
        return agencyname;
    }

    public void setAgencyname(String agencyname) {
        this.agencyname = agencyname;
    }

    public String getAgencyaddress() {
        return agencyaddress;
    }

    public void setAgencyaddress(String agencyaddress) {
        this.agencyaddress = agencyaddress;
    }

    public String getAgency_image() {
        return agency_image;
    }

    public void setAgency_image(String agency_image) {
        this.agency_image = agency_image;
    }

    public void setAgencyid(int agencyid) {
        this.agencyid = agencyid;
    }

    public String getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(String commenttime) {
        this.commenttime = commenttime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public MineCommentItemChildBean getChild() {
        return child;
    }

    public void setChild(MineCommentItemChildBean child) {
        this.child = child;
    }
}
