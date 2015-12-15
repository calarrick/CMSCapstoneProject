/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.dao;

import com.thesoftwareguild.lawinorder.dto.Comment;
import com.thesoftwareguild.lawinorder.dto.Post;
import com.thesoftwareguild.lawinorder.dto.Role;
import com.thesoftwareguild.lawinorder.dto.Tag;
import com.thesoftwareguild.lawinorder.dto.User;
import com.thesoftwareguild.lawinorder.dto.StaticPage;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface LawInOrderDao {
    
    public User addUser (User user);
    public void deleteUser (int userId);
    public User updateUser (User user);
    public User getUserById (int userId);
    public List<User> getAllUsers();
    public List<User> getUserByUsername(String username);

    public Comment addComment (Comment comment);
    public void deleteComment (int commentId);
    public Comment updateComment (Comment comment);
    public Comment getCommentById (int commentId);
    public List<Comment> getCommentsByUserId (int userId);
    public List<Comment> getAllComments();
    
    //some of these may ultimately be moved to private/helper methods internal to implementation
    //therefore out of interface
    //but agree stating them here initially is good way to set out the class
  
    public Tag addTag (Tag tag);
    public void deleteTag (int tagId);
    public Tag updateTag (Tag tag);
    public Tag getTagById (int tagId);
    public List<Tag> getAllTags(); 
    public List<Tag> getTagsFromPost(int postId);
    public List<Tag> getTagsFromPage(int pageId);
    public Tag getTagByName(String tagName);
    
    public void addTagsPostEntry(int tagId, int postId);
    public void addTagsPageEntry(int tagId, int pageId);
    public void deleteTagsPostEntry(int pageId);
    public void deleteTagsPageEntry(int pageId);
  
    public Role addRole (Role role);
    public void deleteRole (int roleId);
    public Role updateRole (Role role);
    public Role getRoleById(int roleId);
    public List<Role> getAllRoles();
    
    public void addUserToRole(User user, Role role);
    public List<Role> getRolesByUserId(int userId);
    public List<User> getUsersByRoleId(int roleId);
    
    public Post addPost (Post post);
    public Post getPostByTitle(String title);
    public Post getPostByUrl(String url);
    public void deletePost (int postId);
    public Post updatePost (Post post);
    public Post getPostById (int postId);
    public List<Post> getPostsByUserId (int userId);
    public List<Post> getAllPosts();
    public List<Post> getPostsByTag(int tagId);
    public List<Post> getDraftsByUserId(int userId);
    
    //MUST user the base-class type here (Post not StaticPage)
    public Post addStaticPage (Post staticPage);
    public Post getStaticPageByTitle (String title);
    public Post getStaticPageByUrl(String url);
    public void deleteStaticPage (int postId);
    public Post updateStaticPage  (Post post);
    public Post getStaticPageByPostId (int postId);
    public List<Post> getStaticPagesByUserId (int userId);
   
    
    public List<StaticPage> getAllStaticPages();
    
    public void cleanDB();
    public List<Tag> getSortedTags();
    

    public String getUrlByPostId(int postId);
    public String getUrlByPageId(int pageId);
    
    public List<Post> getPendingList();
    public List<Post> getPendingListViewById(int userId);
}
