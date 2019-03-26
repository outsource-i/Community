package com.onion.community.bean;

import java.io.Serializable;

/**
 * Created by OnionMac on 2019/3/12.
 */
public class Community implements Serializable {

    private String id;
    private String onwerId;
    private String communityId;
    private String communityName;
    private String communityPeopleCount;
    private String communityImg;
    private String communityArticleCount;
    private String communityInfo;
    private String communityCreateId;
    private String createDate;
    private String updateDate;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private boolean follow;

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOnwerId() {
        return onwerId;
    }

    public void setOnwerId(String onwerId) {
        this.onwerId = onwerId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityPeopleCount() {
        return communityPeopleCount;
    }

    public void setCommunityPeopleCount(String communityPeopleCount) {
        this.communityPeopleCount = communityPeopleCount;
    }

    public String getCommunityImg() {
        return communityImg;
    }

    public void setCommunityImg(String communityImg) {
        this.communityImg = communityImg;
    }

    public String getCommunityArticleCount() {
        return communityArticleCount;
    }

    public void setCommunityArticleCount(String communityArticleCount) {
        this.communityArticleCount = communityArticleCount;
    }

    public String getCommunityInfo() {
        return communityInfo;
    }

    public void setCommunityInfo(String communityInfo) {
        this.communityInfo = communityInfo;
    }

    public String getCommunityCreateId() {
        return communityCreateId;
    }

    public void setCommunityCreateId(String communityCreateId) {
        this.communityCreateId = communityCreateId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
