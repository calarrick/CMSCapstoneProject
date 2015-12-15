/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.dao;

import com.thesoftwareguild.lawinorder.dto.Comment;
import com.thesoftwareguild.lawinorder.dto.Post;
import com.thesoftwareguild.lawinorder.dto.PostRelational;
import com.thesoftwareguild.lawinorder.dto.PostType;
import com.thesoftwareguild.lawinorder.dto.PubStatus;
import com.thesoftwareguild.lawinorder.dto.Role;
import com.thesoftwareguild.lawinorder.dto.StaticPage;
import com.thesoftwareguild.lawinorder.dto.Tag;
import com.thesoftwareguild.lawinorder.dto.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author apprentice
 */
public class LawInOrderDaoDbImpl implements LawInOrderDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//USERS
    private static final String SQL_INSERT_USER
            = "insert into users (username, email, password) values (?, ?, ?) ";
    private static final String SQL_DELETE_USER
            = "delete from users where user_id = ?";
    private static final String SQL_UPDATE_USER
            = "UPDATE users SET username=?, email=?, password=? "
            + " WHERE user_id=?";
    private static final String SQL_SELECT_USER
            = "select * from users where user_id = ?";
    private static final String SQL_SELECT_ALL_USERS
            = "select * from users";
    private static final String SQL_SELECT_USER_BY_USERNAME
            = "SELECT * FROM users WHERE username = ? ";
    private static final String SQL_SELECT_USERS_BY_POST_ID
            = "select users.user_id, us.username "
            + "from posts inner join users "
            + "on posts.user_id = users.user_id "
            + "where posts.user_id = ?";
//COMMENTS
    private static final String SQL_INSERT_COMMENT
            = "insert into comments "
            + "(user_id, comment_body, comment_date, post_id) values (?, ?, ?, ?)";
    private static final String SQL_DELETE_COMMENT
            = "delete * from comments where comment_id = ?";
    private static final String SQL_UPDATE_COMMENT
            = "update comments set user_id=?, comment_body=?, comment_date=?, post_id=? where comment_id=?";
    private static final String SQL_SELECT_COMMENT
            = "select * from comments where comment_id=?";
    private static final String SQL_SELECT_ALL_COMMENTS
            = "select * from comments";
    private static final String SQL_SELECT_COMMENTS_BY_USER_ID
            = "select * from comments where user_id=?";

//ROLES
    private static final String SQL_INSERT_ROLE
            = "insert into roles (role_name) values (?)";
    private static final String SQL_DELETE_ROLE
            = "delete from roles where role_id = ?";
    private static final String SQL_UPDATE_ROLE
            = "update roles set role_name = ? where role_id = ? ";
    private static final String SQL_SELECT_ROLE
            = "Select * from roles where role_id = ?";
    private static final String SQL_SELECT_ALL_ROLES
            = "select * from roles";
//TAGS
    private static final String SQL_INSERT_TAG
            = "insert into tags (tag_name) values (?)";
    private static final String SQL_DELETE_TAG
            = "delete from tags where tag_id = ?";
    private static final String SQL_UPDATE_TAG
            = "update tags set tag_name = ? where tag_id = ? ";
    private static final String SQL_SELECT_TAG
            = "Select * from tags where tag_id = ?";
    private static final String SQL_SELECT_ALL_TAGS
            = "select * from tags";
    private static final String SQL_SELECT_TAG_BY_NAME
            = "select * from tags where tag_name=?";
//POSTS
    private static final String SQL_INSERT_POST
            = "insert into posts "
            + "(title, user_id, post_body, post_date, post_excerpt, last_edit_date, "
            + "is_private, pub_status, post_type) values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

    private static final String SQL_DELETE_POST
            = "delete from posts where post_id = ?";

    private static final String SQL_DELETE_TAGS_POST_ON_POST
            = "delete from tags_posts where post_id = ?";

    private static final String SQL_DELETE_POSTS_URLS_ON_POST
            = "delete from posts_urls where post_id = ?";

// STATIC PAGES
    private static final String SQL_UPDATE_POST
            = "update posts set title=?, user_id=?, post_body=?, post_date=?, post_excerpt=?, "
            + "last_edit_date=?, is_private=?, pub_status=?, post_type=? "
            + "where post_id=?";
    private static final String SQL_SELECT_POST
            = "select * from posts where post_id=? ";
    private static final String SQL_SELECT_ALL_POSTS
            = "select * from posts ORDER BY post_date DESC";
    private static final String SQL_SELECT_POST_BY_TITLE
            = "SELECT * FROM posts WHERE title = ?";
    private static final String SQL_SELECT_POST_BY_URL
            = "SELECT p.* FROM posts p INNER JOIN posts_urls pu ON p.post_id = pu.post_id WHERE pu.url_text = ?";
    private static final String SQL_SELECT_POSTS_BY_USER_ID
            = "select * from posts where user_id=?";
    private static final String SQL_SELECT_POSTS_BY_TAG
            = "select p.post_id, p.title, p.user_id, p.post_body, p.post_date, p.post_excerpt, p.last_edit_date, "
            + "p.is_private, p.pub_status, p.post_type "
            + "from posts as p inner join tags_posts on p.post_id=tags_posts.post_id "
            + "where tags_posts.tag_id=?";
    private static final String SQL_GET_POST_URL_FROM_POST_ID
            = "SELECT url_text FROM posts_urls WHERE post_id = ? ";

//Tags_Posts
    private static final String SQL_INSERT_TAGS_POSTS
            = "insert into tags_posts " 
            + "(tag_id, post_id) values(? ,?)";
    
    private static final String SQL_DELETE_TAGS_PAGE_ON_PAGE
            = "delete from tags_pages where page_id = ?";

//**********************************************************
//*****************USER METHODS*****************************
//**********************************************************
//**********************************************************
    private static final String SQL_INSERT_USERS_ROLES
            = "insert into users_roles (role_id, user_id) values(?, ?)";
    
    private static final String SQL_INSERT_AUTHORITIES
            = "insert into authorities (username, authority) values (?, ?)";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User addUser(User user) {
        jdbcTemplate.update(SQL_INSERT_USER,
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
        user.setUserId(jdbcTemplate.
                queryForObject("select LAST_INSERT_ID()", Integer.class));
//
//jdbcTemplate.update("SQL_INSERT_AUTHORITIES", user.getUsername(), user.getRole());
//
//        jdbcTemplate.update(SQL_INSERT_USERS_ROLES, jdbcTemplate.queryForObject(
//                "select role_id from roles where role_name =(\"" + user.getRole() + "\"", Integer.class), user.getUserId());
        return user;
    }

    @Override
    public void deleteUser(int userId) {    

        jdbcTemplate.update(SQL_DELETE_USER, userId);
    }

    
        private static final String SQL_UPDATE_AUTHORITIES
            = "UPDATE authorities SET username=?"
            + " WHERE username=?";
    
    @Override
    public User updateUser(User user) {

        jdbcTemplate.update(SQL_UPDATE_USER,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getUserId());
        return user;
    }

    @Override
    public User getUserById(int userId) {
        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_USER, new UserMapper(), userId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<User> getUserByUsername(String username) {
        return jdbcTemplate.
                query(SQL_SELECT_USER_BY_USERNAME, new UserMapper(), username);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_SELECT_ALL_USERS, new UserMapper());
    }

//****************************************************************
//*******************ROLE METHODS*********************************
//****************************************************************
//****************************************************************
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Role addRole(Role role) {
        jdbcTemplate.update(SQL_INSERT_ROLE, role.getRoleName());
        role.setRoleId(jdbcTemplate.
                queryForObject("select LAST_INSERT_ID()", Integer.class));
        return role;
    }

    @Override
    public void deleteRole(int roleId) {
        jdbcTemplate.update(SQL_DELETE_ROLE, roleId);
    }

    @Override
    public Role updateRole(Role role) {
        jdbcTemplate.update(SQL_UPDATE_ROLE,
                role.getRoleName(),
                role.getRoleId());
        return role;
    }

    @Override
    public Role getRoleById(int roleId) {
        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_ROLE, new RoleMapper(), roleId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Role> getAllRoles() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ROLES, new RoleMapper());
    }

    @Override
    public List<Role> getRolesByUserId(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//********************************************************    
//****************COMMENT METHODS*************************
//********************************************************    
//********************************************************    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Comment addComment(Comment comment) {
        jdbcTemplate.update(SQL_INSERT_COMMENT,
                comment.getUserId(),
                comment.getCommentBody(),
                comment.getCommentDate(),
                comment.getPostId());
        comment.setCommentId(jdbcTemplate.
                queryForObject("select LAST_INSERT_ID()", Integer.class));
        return comment;
    }

    @Override
    public void deleteComment(int commentId) {
        jdbcTemplate.update(SQL_DELETE_COMMENT, commentId);
    }

    @Override
    public Comment updateComment(Comment comment) {
        jdbcTemplate.update(SQL_UPDATE_COMMENT,
                comment.getUserId(),
                comment.getCommentBody(),
                comment.getCommentDate(),
                comment.getPostId(),
                comment.getCommentId());
        return comment;
    }

    @Override
    public Comment getCommentById(int commentId) {
        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_COMMENT, new CommentMapper(), commentId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Comment> getCommentsByUserId(int userId) {
        List<Comment> cList = jdbcTemplate.
                query(SQL_SELECT_COMMENTS_BY_USER_ID, new CommentMapper(), userId);
        return cList;
    }

    @Override
    public List<Comment> getAllComments() {
        List<Comment> cList = jdbcTemplate.
                query(SQL_SELECT_ALL_COMMENTS, new CommentMapper());
        return cList;
    }

//************************************************************    
//*******************TAG METHODS******************************
//************************************************************    
//************************************************************    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Tag addTag(Tag tag) {
        jdbcTemplate.update(SQL_INSERT_TAG,
                tag.getTagName());
        tag.setTagId(jdbcTemplate.
                queryForObject("select LAST_INSERT_ID()", Integer.class));
        return tag;
    }

    @Override
    public void deleteTag(int tagId) {
        jdbcTemplate.update(SQL_DELETE_TAG, tagId);
    }

    @Override
    public Tag updateTag(Tag tag) {
        jdbcTemplate.update(SQL_UPDATE_TAG, tag.getTagName(),
                tag.getTagId());
        return tag;
    }

    @Override
    public Tag getTagById(int tagId) {
        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_TAG, new TagMapper(), tagId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Tag getTagByName(String tagName) {
        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_TAG_BY_NAME, new TagMapper(), tagName);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return jdbcTemplate.query(SQL_SELECT_ALL_TAGS, new TagMapper());
    }

    private static final String SQL_SELECT_TAGS_SORTED_BY_USE
            = "SELECT tag_id, tag_name FROM "
            + "(SELECT tag_name, tags.tag_id as tag_id, COUNT(*) AS c FROM "
            + "tags INNER JOIN tags_posts ON tags.tag_id = tags_posts.tag_id "
            + "inner join posts on tags_posts.post_id = posts.post_id where posts.pub_status = \"PUBLISH\""
            + "GROUP BY tag_name) AS tagtally ORDER BY c DESC";

    @Override
    public List<Tag> getSortedTags() {
        return jdbcTemplate.
                query(SQL_SELECT_TAGS_SORTED_BY_USE, new TagMapper());
    }

    private static final String SQL_SELECT_TAGS_BY_POST_ID
            = "select tags.tag_id, tags.tag_name from "
            + "tags inner join tags_posts on "
            + "tags.tag_id=tags_posts.tag_id "
            + "where tags_posts.post_id = ?";

    @Override
    public List<Tag> getTagsFromPost(int postId) {
        return jdbcTemplate.
                query(SQL_SELECT_TAGS_BY_POST_ID, new TagMapper(), postId);
    }

    private static final String SQL_SELECT_TAGS_BY_PAGE_ID
            = "select tags.tag_id, tags.tag_name from "
            + "tags inner join tags_pages on "
            + "tags.tag_id = tags_pages.tag_id "
            + "where tags_pages.page_id = ?";

    @Override
    public List<Tag> getTagsFromPage(int postId) {
        return jdbcTemplate.
                query(SQL_SELECT_TAGS_BY_PAGE_ID, new TagMapper(), postId);
    }

//***********************************************************    
//********************POST METHODS***************************
//***********************************************************    
//***********************************************************    
    @Override
    public Post addPost(Post post) {

//calls helper methods for main post insertion and for inserting into 
        //the post/url match table
        //leave this structure in place on merging
        if (post.getClass().getName().equals("StaticPage")) {

            addStaticPage((StaticPage) post);

        } else {

            post = insertMainPost(post);
            insertPostUrlMatch(post);

        }
        return post;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private Post insertMainPost(Post post) {

        int privateFlag = 0;
        if (post.getIsPrivate()) {
            privateFlag = 1;
        }
        jdbcTemplate.update(SQL_INSERT_POST,
                post.getPostTitle(),
                post.getUserId(),
                post.getPostBody(),
                Date.valueOf(post.getPublishDate()),
                post.getPostExcerpt(),
                Date.valueOf(post.getLastEditDate()),
                privateFlag,
                post.getPubStatus().toString(),
                post.getPostType().toString());

        post.setPostId(jdbcTemplate.
                queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return post;
    }

    private static final String SQL_INSERT_POST_URL_MATCH
            = "INSERT into posts_urls (post_id, url_text) VALUES (?, ?) ";

    private void insertPostUrlMatch(Post post) {

        int postId = post.getPostId();

        String postUrl = post.getPostTitle().toLowerCase()
                .replaceAll("\\s", "-");

        jdbcTemplate.update(SQL_INSERT_POST_URL_MATCH,
                post.getPostId(),
                postUrl);

    }
    
    private void updatePostUrlMatch(Post post) {
        
        int postId = post.getPostId();
        
        String postUrl = post.getPostTitle().toLowerCase()
                .replaceAll("\\s", "-");
        
        String urlCheck = jdbcTemplate.
                    queryForObject(SQL_GET_POST_URL_FROM_POST_ID,
                            new Integer[]{postId}, String.class);
        
        if (postUrl.equals(urlCheck)){
            return;
        }
        else {
           
        
        insertPostUrlMatch(post);
        
        
                }
    }

    @Override
    public void deletePost(int postId) {

        jdbcTemplate.update(SQL_DELETE_TAGS_POST_ON_POST, postId);
        jdbcTemplate.update(SQL_DELETE_POSTS_URLS_ON_POST, postId);
        jdbcTemplate.update(SQL_DELETE_POST, postId);

    }

    
    @Override
    public Post updatePost(Post post) {
        
        int postId = post.getPostId();
        jdbcTemplate.update(SQL_UPDATE_POST, 
                post.getPostTitle(), 
                post.getUserId(),
                post.getPostBody(), 
                java.sql.Date.valueOf(post.getPublishDate()),
                post.getPostExcerpt(), 
                java.sql.Date.valueOf(post.getLastEditDate()),
                post.getIsPrivate(), 
                post.getPubStatus().toString(), 
                post.getPostType().toString(), 
                post.getPostId());
        
        updatePostUrlMatch(post);
        
        return post;
    }

    @Override
    public Post getPostById(int postId) {
        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_POST, new PostRelationalMapper(), postId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Post getPostByTitle(String title) {
        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_POST_BY_TITLE, new PostRelationalMapper(), title);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Post> getPostsByUserId(int userId) {
        return jdbcTemplate.
                query(SQL_SELECT_POSTS_BY_USER_ID, new PostRelationalMapper(), userId);
    }

    @Override
    public List<Post> getAllPosts() {

        return jdbcTemplate.
                query(SQL_SELECT_ALL_POSTS, new PostRelationalMapper());
    }

    @Override
    public List<Post> getPostsByTag(int tagId) {
        return jdbcTemplate.
                query(SQL_SELECT_POSTS_BY_TAG, new PostRelationalMapper(), tagId);
    }
    
    
    private static final String SQL_SELECT_DRAFTS_BY_USER_ID=
            "select * from posts where pub_status='DRAFT' and user_id=?";
    
    public List<Post> getDraftsByUserId(int userId) {
        return jdbcTemplate.query(SQL_SELECT_DRAFTS_BY_USER_ID, new PostRelationalMapper(), userId);
    }
    
    
    

//***************************************************************************************
//*****************USERS_ROLES, TAGS_POSTS/TAGS_PAGES METHODS****************************
//***************************************************************************************
//***************************************************************************************
    @Override
    public void addUserToRole(User user, Role role) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTagsPostEntry(int tagId, int postId) {
        jdbcTemplate.update(SQL_INSERT_TAGS_POSTS,
                tagId,
                postId);
    }

    private static final String SQL_INSERT_TAGS_PAGES
            = "insert into tags_pages (tag_id, page_id) values(? ,?)";

    @Override
    public void addTagsPageEntry(int tagId, int pageId) {
        jdbcTemplate.update(SQL_INSERT_TAGS_PAGES, tagId, pageId);
    }
    
    
    
    @Override
    public void deleteTagsPostEntry(int pageId) {
        
        jdbcTemplate.update(SQL_DELETE_TAGS_POST_ON_POST, pageId);
        
            }

    @Override
    public void deleteTagsPageEntry(int pageId) {
        jdbcTemplate.update(SQL_DELETE_TAGS_PAGE_ON_PAGE, pageId);
    }

    @Override
    public List<User> getUsersByRoleId(int roleId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUrlByPostId(int postId) {
        try {
            String postUrl = jdbcTemplate.
                    queryForObject(SQL_GET_POST_URL_FROM_POST_ID,
                            new Integer[]{postId}, String.class);
            return postUrl;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Post getPostByUrl(String url) {

        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_POST_BY_URL, new PostRelationalMapper(), url);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    //***************************************************************************
    //******************************STATIC PAGES*********************************
    //***************************************************************************
    //***************************************************************************
    //Adding static pages to table/url table
    private static final String SQL_INSERT_STATIC_PAGE
            = "insert into static_pages (title, user_id, post_body, post_date, post_excerpt, "
            + "last_edit_date, is_private, pub_status) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public StaticPage addStaticPage(Post staticPage) {
//this method must call private methods to insert main table and 
        //to insert page/url mapper table

        staticPage = insertStaticPage((StaticPage) staticPage);
        insertStaticPageUrlMatch(staticPage);

        return (StaticPage) staticPage;

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private StaticPage insertStaticPage(StaticPage staticPage) {

        //This currently does nothing
        int privateFlag = 0;
        if (staticPage.getIsPrivate()) {
            privateFlag = 1;
        }

        jdbcTemplate.update(SQL_INSERT_STATIC_PAGE,
                staticPage.getPostTitle(),
                staticPage.getUserId(),
                staticPage.getPostBody(),
                staticPage.getPublishDate().toString(),
                staticPage.getPostExcerpt(),
                staticPage.getLastEditDate().toString(),
                staticPage.getIsPrivate(),
                staticPage.getPubStatus().toString());

        staticPage.setPostId(jdbcTemplate.
                queryForObject("select LAST_INSERT_ID()", Integer.class));

        return staticPage;
    }

    private static final String SQL_INSERT_STATIC_PAGE_URL_MATCH
            = "INSERT into pages_urls (post_id, url_text) VALUES (?, ?) ";

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    private void insertStaticPageUrlMatch(Post post) {

        int postId = post.getPostId();

        String postUrl = post.getPostTitle().toLowerCase()
                .replaceAll("\\s", "-");
        //NOTE this is still very crude placeholder and breaks on a wide range of characters and 
        //punctuation that could occur in a post title. Needs to be replaced by 
        //a library method of some sort OR by a better reqular-expression-based
        //parser of our own creation

        jdbcTemplate.update(SQL_INSERT_STATIC_PAGE_URL_MATCH,
                post.getPostId(),
                postUrl);

    }

    private final String SQL_DELETE_STATIC_PAGE
            = "delete from static_pages where post_id = ?";

    @Override
    public void deleteStaticPage(int postId) {
        jdbcTemplate.update(SQL_DELETE_TAGS_PAGE_ON_PAGE, postId);
        jdbcTemplate.update(SQL_DELETE_POSTS_URLS_ON_POST, postId);
        jdbcTemplate.update(SQL_DELETE_STATIC_PAGE, postId);
    }

    private static final String SQL_UPDATE_STATIC_PAGE
            = "update static_pages set title=?, user_id=?, post_body=?, post_date=?, post_excerpt=?, "
            + "last_edit_date=?, is_private=?, pub_status=?, post_type=? "
            + "where post_id=?";
    
    @Override
    public Post updateStaticPage(Post post) {
        
        int postId = post.getPostId();
        jdbcTemplate.update(SQL_UPDATE_STATIC_PAGE, 
                post.getPostTitle(), 
                post.getUserId(),
                post.getPostBody(), 
                java.sql.Date.valueOf(post.getPublishDate()),
                post.getPostExcerpt(), 
                java.sql.Date.valueOf(post.getLastEditDate()),
                post.getIsPrivate(), 
                post.getPubStatus().toString(), 
                post.getPostType().toString(),
                post.getPostId());
        
        updateStaticPageUrlMatch(post);
        
        return post;
    
    
    
    }
    
    
    private void updateStaticPageUrlMatch(Post post) {
        
        int postId = post.getPostId();
        
        String postUrl = post.getPostTitle().toLowerCase()
                .replaceAll("\\s", "-");
        
        String urlCheck = jdbcTemplate.
                    queryForObject(SQL_GET_POST_URL_FROM_POST_ID,
                            new Integer[]{postId}, String.class);
        
        if (!postUrl.equals(urlCheck)){
            
        insertStaticPageUrlMatch(post);
        
        
    }}
    

    private static final String SQL_SELECT_STATIC_PAGES
            = "Select * from static_pages";

    private static final String SQL_SELECT_ALL_STATIC_PAGES
            = "select * from static_pages";

    @Override
    public List<StaticPage> getAllStaticPages() {
        return jdbcTemplate.
                query(SQL_SELECT_ALL_STATIC_PAGES, new StaticPageMapper());
    }

    private static final String SQL_SELECT_STATIC_PAGE
            = "select * from static_pages where post_id = ?";

    @Override
    public Post getStaticPageByPostId(int postId) {
        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_STATIC_PAGE, new StaticPageMapper(), postId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private static final String SQL_SELECT_STATIC_PAGE_BY_URL
            = "SELECT sp.* FROM static_pages sp INNER JOIN pages_urls pu ON sp.post_id = pu.post_id WHERE pu.url_text = ?";

    @Override
    public Post getStaticPageByUrl(String url) {

        try {
            return jdbcTemplate.
                    queryForObject(SQL_SELECT_STATIC_PAGE_BY_URL, new StaticPageMapper(), url);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    private static final String SQL_STATIC_PAGE_BY_TITLE
            = "SELECT * FROM static_pages WHERE title = ?";

    @Override
    public StaticPage getStaticPageByTitle(String title) {

        try {
            return jdbcTemplate.
                    queryForObject(SQL_STATIC_PAGE_BY_TITLE, new StaticPageMapper(), title);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public List<Post> getStaticPagesByUserId(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    private static final String SQL_SELECT_URL_BY_PAGE_ID
            = "select url_text from pages_urls where post_id=?";

    @Override
    public String getUrlByPageId(int postId) {

        try {

            String postUrl = jdbcTemplate.
                    queryForObject(SQL_SELECT_URL_BY_PAGE_ID, new Integer[]{postId}, String.class);
            return postUrl;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    //**************************************************************************
    //*******************PENDING LIST*******************************************
    //**************************************************************************
    //**************************************************************************
    private static final String SQL_SELECT_PENDING_LIST
            = "select * from posts where pub_status = 'DRAFT' order by post_date asc";

    @Override
    public List<Post> getPendingList() {

        return jdbcTemplate.query(SQL_SELECT_PENDING_LIST, new PostRelationalMapper());
    }

    private static final String SQL_SELECT_PENDING_POSTS_BY_USER_ID
            = "select * from posts where pub_status = 'DRAFT' and user_id =? order by post_date asc";

    @Override
    public List<Post> getPendingListViewById(int userId) {
        return jdbcTemplate.query(SQL_SELECT_PENDING_POSTS_BY_USER_ID, new PostRelationalMapper(), userId);
    }

    

//***************************************************************************
//********************MAPPERS AND HELPER METHODS*****************************
//***************************************************************************
//***************************************************************************
    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

    private static class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setRoleId(rs.getInt("role_id"));
            role.setRoleName(rs.getString("role_name"));
            return role;
        }
    }

    private static class TagMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet rs, int i) throws SQLException {
            Tag tag = new Tag();
            tag.setTagId(rs.getInt("tag_id"));
            tag.setTagName(rs.getString("tag_name"));
            return tag;
        }
    }

    private static class CommentMapper implements RowMapper<Comment> {

        @Override
        public Comment mapRow(ResultSet rs, int i) throws SQLException {
            Comment comment = new Comment();
            comment.setCommentId(rs.getInt("comment_id"));
            comment.setCommentBody(rs.getString("comment_body"));
            comment.setCommentDate(rs.getDate("comment_date").toLocalDate());
            comment.setUserId(rs.getInt("user_id"));
            comment.setPostId(rs.getInt("post_id"));
            return comment;
        }
    }

    private static class PostRelationalMapper implements RowMapper<Post> {

        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            Post post = new PostRelational();
            post.setPostId(rs.getInt("post_id"));
            post.setPostTitle(rs.getString("title"));
            post.setUserId(rs.getInt("user_id"));
            post.setPostBody(rs.getString("post_body"));
            post.setLastEditDate(rs.getDate("last_edit_date").toLocalDate());
            post.setIsPrivate(rs.getBoolean("is_private"));
            post.setPublishDate(rs.getDate("post_date").toLocalDate());
            post.setPostExcerpt(rs.getString("post_excerpt"));
            post.setPubStatus(PubStatus.valueOf(rs.getString("pub_status")));
            post.setPostType(PostType.valueOf(rs.getString("post_type")));
            return post;
        }
    }

    private static class StaticPageMapper implements RowMapper<StaticPage> {

        @Override
        public StaticPage mapRow(ResultSet rs, int i) throws SQLException {
            StaticPage staticPage = new StaticPage();
            staticPage.setPostId(rs.getInt("post_id"));
            staticPage.setPostTitle(rs.getString("title"));
            staticPage.setUserId(rs.getInt("user_id"));
            staticPage.setPostBody(rs.getString("post_body"));
            staticPage.setLastEditDate(rs.getDate("last_edit_date").
                    toLocalDate());
            staticPage.setIsPrivate(rs.getBoolean("is_private"));
            staticPage.setPublishDate(rs.getDate("post_date").toLocalDate());
            staticPage.setPostExcerpt(rs.getString("post_excerpt"));
            staticPage.setPubStatus(PubStatus.valueOf(rs.getString("pub_status")));

            return staticPage;
        }
    }

    @Override
    public void cleanDB() {
        jdbcTemplate.update("delete from posts_urls");
        jdbcTemplate.update("delete from tags_posts");
        jdbcTemplate.update("delete from tags");
        jdbcTemplate.update("delete from users_roles");
        jdbcTemplate.update("delete from roles");
        jdbcTemplate.update("delete from static_pages");
        jdbcTemplate.update("delete from comments");
        jdbcTemplate.update("delete from posts");
        jdbcTemplate.update("delete from users");
        jdbcTemplate.update("delete from authorities");

        Role adminRole = new Role();
        adminRole.setRoleName("ROLE_ADMIN");
        addRole(adminRole);
        
        Role authorRole = new Role();
        authorRole.setRoleName("ROLE_AUTHOR");
        addRole(authorRole);
        
        User user1 = new User();
        user1.setUsername("test");
        user1.setEmail("test@test.com");
        user1.setPassword("test");
        user1.setRole("ROLE_ADMIN");
        
       addUser(user1);
       
       User user2 = new User();
       user2.setUsername("testauthor");
       user2.setEmail("test2@test2.com");
       user2.setPassword("test");
       user2.setRole("ROLE_AUTHOR");
       
       addUser(user2);
    }

}
