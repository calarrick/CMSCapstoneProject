/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.dto;

import java.util.List;

/**
 *
 * @author calarrick
 */
public class Publication extends Post {

    private String author;
    private String tagsAsString;
    private String[] tags;
    private String postTypeString;
    private String publishDateString;
    private String lastEditDateString;
    private String pubStatusString;
    private String postUrl;
    private String oldPostType;

    private List<Comment> comments;
    private int userId;

    public String getAuthor() {

        return author;

    }

    public void setAuthor(String author) {

        this.author = author;
    }

    
    
    public void setTagsAsString(String tagsAsString) {
        this.tagsAsString = tagsAsString;
    }

    public String getTagsAsString() {
        return tagsAsString;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getPostTypeString() {
        return postTypeString;
    }

    public void setPostTypeString(String postTypeString) {
        this.postTypeString = postTypeString;
    }

    public String getPublishDateString() {
        return publishDateString;
    }

    public void setPublishDateString(String publishDateString) {
        this.publishDateString = publishDateString;
    }

    public String getLastEditDateString() {
        return lastEditDateString;
    }

    public void setLastEditDateString(String lastEditDateString) {
        this.lastEditDateString = lastEditDateString;
    }

    public String getPubStatusString() {
        return pubStatusString;
    }

    public void setPubStatusString(String pubStatusString) {
        this.pubStatusString = pubStatusString;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(
            List<Comment> comments) {
        this.comments = comments;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getOldPostType() {
        return oldPostType;
    }

    public void setOldPostType(String oldPostType) {
        this.oldPostType = oldPostType;
    }

    
    

    
}
