/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.dto;

import java.util.Objects;

/**
 *
 * @author apprentice
 */
public class Tag {
    private int tagId;
    private String tagName;

    public int getTagId() {
        return tagId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.tagId;
        hash = 61 * hash + Objects.hashCode(this.tagName);
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
        final Tag other = (Tag) obj;
        if (this.tagId != other.tagId) {
            return false;
        }
        if (!Objects.equals(this.tagName, other.tagName)) {
            return false;
        }
        return true;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName.trim();
    }
}
