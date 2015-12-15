/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author calarrick
 */
public abstract class Post {

    private int postId;
    private int userId;
    private String postBody;
    private LocalDate lastEditDate;
    private boolean isPrivate;
    private String postTitle;
    private LocalDate publishDate;
    private String postExcerpt;
    private PubStatus pubStatus;
    private PostType postType;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public LocalDate getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(LocalDate lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String excerpt) {
        this.postExcerpt = excerpt;
    }

    public PubStatus getPubStatus() {
        return pubStatus;
    }

    public void setPubStatus(
            PubStatus pubStatus) {
        this.pubStatus = pubStatus;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(
            PostType postType) {
        this.postType = postType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.postId;
        hash = 97 * hash + this.userId;
        hash = 97 * hash + Objects.hashCode(this.postBody);
        hash = 97 * hash + Objects.hashCode(this.lastEditDate);
        hash = 97 * hash + (this.isPrivate ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.postTitle);
        hash = 97 * hash + Objects.hashCode(this.publishDate);
        hash = 97 * hash + Objects.hashCode(this.postExcerpt);
        hash = 97 * hash + Objects.hashCode(this.pubStatus);
        hash = 97 * hash + Objects.hashCode(this.postType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Post other = (Post) obj;
        if (this.postId != other.postId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (!Objects.equals(this.postBody, other.postBody)) {
            return false;
        }
        if (!Objects.equals(this.lastEditDate, other.lastEditDate)) {
            return false;
        }
        if (this.isPrivate != other.isPrivate) {
            return false;
        }
        if (!Objects.equals(this.postTitle, other.postTitle)) {
            return false;
        }
        if (!Objects.equals(this.publishDate, other.publishDate)) {
            return false;
        }
        if (!Objects.equals(this.postExcerpt, other.postExcerpt)) {
            return false;
        }
        if (this.pubStatus != other.pubStatus) {
            return false;
        }
        if (this.postType != other.postType) {
            return false;
        }
        return true;
    }

    
    
    
}
