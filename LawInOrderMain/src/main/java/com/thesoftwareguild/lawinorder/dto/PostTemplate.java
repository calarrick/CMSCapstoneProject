/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.dto;

import java.time.LocalDate;

/**
 *
 * @author apprentice
 */
public interface PostTemplate {

    public void setPostId(int id);

    public int getPostId();

    public String getPostBody();

    public void setPostBody(String postBody);

    public void setPostTitle(String postTitle);

    public String getPostTitle();

    public void setPostType(PostType type);

    public PostType getPostType();

    public int getUserId();

    public void setUserId(int id);

    public boolean getIsPrivate();

    public void setIsPrivate(boolean priv);

    public LocalDate getPublishDate();

    public LocalDate getLastEditDate();

    public void setPublishDate(LocalDate date);

    public void setLastEditDate(LocalDate editDate);

    public void setPostExcerpt(String excerpt);

    public String getPostExcerpt();

    public void setPubStatus(PubStatus status);

    public PubStatus getPubStatus();

//    public void setTagsAsString(String tags);
//    
//    public String getTagsAsString();
//    
//    public String[] getTags();
//    
//    public void setTags(String[] tags);
    //    public String getAuthor();
//    
//    public void setAuthor(String author);
}
