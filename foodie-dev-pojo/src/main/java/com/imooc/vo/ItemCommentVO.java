package com.imooc.vo;

import java.util.Date;

/**
 * 用户展示商品评价的VO
 */
public class ItemCommentVO {
    /**
     * 商品评价等级
     */
    private Integer commentLevel;
    /**
     * 商品评价内容
     */
    private String Content;
    /**
     * 规格名称
     */
    private String sepcname;
    /**
     * 评价时间
     */
    private Date createdTime;
    /**
     * 用户头像
     */
    private String userFace;
    /**
     * 用户昵称(匿名)
     */
    private String nickname;

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSepcname() {
        return sepcname;
    }

    public void setSepcname(String sepcname) {
        this.sepcname = sepcname;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}