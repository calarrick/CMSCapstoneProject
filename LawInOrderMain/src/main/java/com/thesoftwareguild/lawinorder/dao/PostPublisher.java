/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.dao;

import com.thesoftwareguild.lawinorder.dto.Post;
import com.thesoftwareguild.lawinorder.dto.Publication;

/**
 *
 * @author calarrick
 */
public interface PostPublisher {
    
    //two-way translation between table-derived post objects and 
    //view-oriented post 'publications' that include
    //their author names, tags, comments, etc. 
    
    //this means any endpoint or method that calls for
    //a 'publication' or list of them is effectively
    //doing eager querying and instantiation
    
    //work directly with query for PostRelational or StaticPost if 
    //you need only information in native post tables
    
    public Publication processNewPublication(Publication newContent);
    
    public Publication processNewPublication(int id, Publication updateContent);
    
    public Publication publicationBuilder(Post post);

    public void removePostPageFromActive(int id);

    
    
}
