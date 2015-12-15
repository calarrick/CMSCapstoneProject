/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.dto;

import java.util.List;



/**
 *
 * @author apprentice
 */
public class StaticPage extends Post{
    
    //implements standard versions of members and methods from static Post class
    //w additions below
    
    private List<Integer> childPageIds;
    private List<Integer> menusIncludedIds;
    
    public List<Integer> getChildPageIds(){
        return childPageIds;
    }
    
    public void setChildPageIds(List<Integer> childPageIds){
        this.childPageIds = childPageIds;
    }
    
    public List<Integer> getMenusIncludedIds(){
        
        return menusIncludedIds;
        
    }
    
    @Override
    public PostType getPostType() {
        return PostType.STATIC_PAGE;
    }
    
    public void setMenusIncludedIds(List<Integer> menusIncludedIds){
        
        this.menusIncludedIds = menusIncludedIds;
    }
    
    
    
    
    
    
}

