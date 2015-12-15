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
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
//import junit.framework.Assert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author apprentice
 */
public class LawInOrderDaoTest {

    LawInOrderDao dao;

    User user1;
    User user2;
    User user3;
    User user4;

    Tag tag1;
    Tag tag2;
    Tag tag3;

    Role admin;
    Role author;

    PostRelational post1;
    PostRelational post2;
    PostRelational post3;
    PostRelational post4;

    Comment comment1;
    Comment comment2;
    Comment comment3;
    Comment comment4;

    StaticPage page1;
    StaticPage page2;
    StaticPage page3;

    ApplicationContext ctx;

    public LawInOrderDaoTest() {
    }

    @Before
    public void setUp() {

        ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("dao", LawInOrderDao.class);

        user1 = new User();
        user1.setUsername("Author McAuthor");
        user1.setEmail("author@lawinorder.com");
        user1.setPassword("123abcNotSecureAtAll");
        user1.setRole("Administrator");

        user2 = new User();
        user2.setUsername("Testy Testerson");
        user2.setEmail("testy@lawinorder.com");
        user2.setPassword("storedinplaintextyayayay");
        user2.setRole("Author");

        user3 = new User();
        user3.setUsername("Commenty Commenter");
        user3.setEmail("troll@troll.com");
        user3.setPassword("hi");
        user3.setRole("Author");

        user4 = new User();
        user4.setUsername("Sample_Commenty Commenter");
        user4.setEmail("sample_troll@troll.com");
        user4.setPassword("sample_hi");
        user4.setRole("Administrator");

        tag1 = new Tag();
        tag1.setTagName("test");

        tag2 = new Tag();
        tag2.setTagName("tag");

        tag3 = new Tag();
        tag3.setTagName("sample");

        post1 = new PostRelational();
        post1.setPostTitle("This is Post One");
        post1.setPostBody("<p>This is a dummy post with some <strong>HTML</strong></p>");
        post1.setUserId(0);
        post1.setIsPrivate(false);
        post1.setPostExcerpt("<p>This is a $hellip;</p>");
        post1.setPubStatus(PubStatus.DRAFT);
        post1.setPostType(PostType.POST);
        post1.setPublishDate((LocalDate.of(2015, Month.JUNE, 11)));
        post1.setLastEditDate(LocalDate.of(2015, Month.AUGUST, 15));

        post2 = new PostRelational();
        post2.setPostTitle("This is Post Two");
        post2.setPostBody("<p>This is another dummy post with some <strong>HTML</strong></p>");
        post2.setUserId(1);
        post2.setIsPrivate(false);
        post2.setPostExcerpt("<p>This is another $hellip;</p>");
        post2.setPubStatus(PubStatus.PUBLISH);
        post2.setPostType(PostType.POST);
        post2.setPublishDate((LocalDate.of(2015, Month.NOVEMBER, 20)));
        post2.setLastEditDate(LocalDate.of(2015, Month.DECEMBER, 1));

        post3 = new PostRelational();
        post3.setPostTitle("This is Post Three");
        post3.setPostBody("<p>This is a third dummy post with some <strong>HTML</strong></p>");
        post3.setUserId(0);
        post3.setIsPrivate(true);
        post3.setPostExcerpt("<p>This is a third $hellip;</p>");
        post3.setPubStatus(PubStatus.PUBLISH);
        post3.setPostType(PostType.STATIC_PAGE);
        post3.setPublishDate((LocalDate.of(2015, Month.SEPTEMBER, 10)));
        post3.setLastEditDate(LocalDate.of(2015, Month.DECEMBER, 2));

        comment1 = new Comment();
        comment1.setCommentBody("This is an comment on the Internet, pay it no heed");
        comment1.setCommentDate(LocalDate.of(2015, Month.DECEMBER, 1));
        comment1.setPostId(comment1.getPostId());
        comment1.setUserId(comment1.getUserId());

        comment2 = new Comment();
        comment2.setCommentBody("This is an comment on the Internet, pay it no heed");
        comment2.setCommentDate(LocalDate.of(2015, Month.DECEMBER, 1));
        comment2.setPostId(comment2.getPostId());
        comment2.setUserId(comment2.getUserId());

        comment3 = new Comment();
        comment3.setCommentBody("This is an comment on the Internet, pay it no heed");
        comment3.setCommentDate(LocalDate.of(2015, Month.DECEMBER, 1));
        comment3.setPostId(comment3.getPostId());
        comment3.setUserId(comment3.getUserId());

        page1 = new StaticPage();
        page1.setPostTitle("This is Post (STATIC PAGE) One");
        page1.setPostBody("<p>This is a dummy post (STATIC PAGE) with some <strong>HTML</strong></p>");
        page1.setUserId(0);
        page1.setIsPrivate(false);
        page1.setPostExcerpt("<p>This is a $hellip;</p>");
        page1.setPubStatus(PubStatus.DRAFT);
        page1.setPostType(PostType.POST);
        page1.setPublishDate((LocalDate.of(2015, Month.JULY, 10)));
        page1.setLastEditDate(LocalDate.of(2015, Month.AUGUST, 10));

        page2 = new StaticPage();
        page2.setPostTitle("This is Post (STATIC PAGE) One");
        page2.setPostBody("<p>This is a dummy post (STATIC PAGE) with some <strong>HTML</strong></p>");
        page2.setUserId(0);
        page2.setIsPrivate(false);
        page2.setPostExcerpt("<p>This is a $hellip;</p>");
        page2.setPubStatus(PubStatus.DRAFT);
        page2.setPostType(PostType.POST);
        page2.setPublishDate((LocalDate.of(2015, Month.JANUARY, 1)));
        page2.setLastEditDate(LocalDate.of(2015, Month.SEPTEMBER, 1));

        page3 = new StaticPage();
        page3.setPostTitle("This is Post (STATIC PAGE) One");
        page3.setPostBody("<p>This is a dummy post (STATIC PAGE) with some <strong>HTML</strong></p>");
        page3.setUserId(0);
        page3.setIsPrivate(false);
        page3.setPostExcerpt("<p>This is a $hellip;</p>");
        page3.setPubStatus(PubStatus.DRAFT);
        page3.setPostType(PostType.POST);
        page3.setPublishDate((LocalDate.of(2015, Month.MARCH, 1)));
        page3.setLastEditDate(LocalDate.of(2015, Month.APRIL, 1));

        admin = new Role();
        admin.setRoleName("Administrator");

        author = new Role();
        author.setRoleName("Author");

        JdbcTemplate cleanup = ctx.getBean("jdbcTemplate", JdbcTemplate.class);

        cleanup.update("DELETE FROM tags_posts");
        cleanup.update("DELETE FROM tags");
        cleanup.update("DELETE FROM posts_urls");
        cleanup.update("DELETE FROM posts");
        cleanup.update("DELETE FROM comments");
        cleanup.update("DELETE FROM users_roles");
        cleanup.update("DELETE FROM authorities");
        cleanup.update("DELETE FROM users");
        cleanup.update("DELETE FROM roles");
        
    }

    @After
    public void tearDown() {

    }

    /**
     * Test of addUser method, of class LawInOrderDao.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        dao.addRole(admin);
        dao.addRole(author);
        dao.addUser(user1);
        dao.addUser(user2);

        User user5 = dao.getUserById(user1.getUserId());
        User user4 = dao.getUserById(user2.getUserId());

        assertEquals(user1, user5);

        assertEquals(user2, user4);
        ((ClassPathXmlApplicationContext) ctx).close();

    }

    /**
     * Test of deleteUser method, of class LawInOrderDao.
     */
    @Test
    public void testAddGetDeleteUser() {

        System.out.println("deleteUser");

        dao.addRole(admin);
        dao.addRole(author);

        User user5 = new User();
        user5.setUsername("sampleUsername");
        user5.setEmail("sample@sample.com");
        user5.setPassword("root");
        user5.setRole("Author");

        dao.addUser(user5);

        User user6 = new User();
        user6.setUsername("sampleUsername");
        user6.setEmail("sample@sample.com");
        user6.setPassword("root");
        user6.setRole("Author");

        dao.addUser(user6);

        User fromDb = dao.getUserById(user5.getUserId());

        assertEquals(fromDb.getUserId(), user5.getUserId());
        assertEquals(fromDb.getEmail(), user5.getEmail());
        assertEquals(fromDb.getPassword(), user5.getPassword());

        dao.deleteUser(user5.getUserId());

        assertNull(dao.getUserById(user5.getUserId()));
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of updateUser method, of class LawInOrderDao.
     */
    @Test
    public void testUpdateUser() {

        dao.addRole(admin);
        dao.addRole(author);
        dao.addUser(user1);
        dao.addUser(user2);

        user1.setUsername("Owner McAuthor");

        dao.updateUser(user1);

        Assert.assertEquals(user1, dao.getUserById(user1.getUserId()));
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of getUserById method, of class LawInOrderDao.
     */
    @Test
    public void testGetUserById() {

        dao.addRole(admin);
        dao.addRole(author);

        dao.addUser(user1);
        dao.addUser(user2);
        dao.addUser(user3);

        User user5 = dao.getUserById(user1.getUserId());
        User user4 = dao.getUserById(user3.getUserId());

        Assert.assertEquals(user1, user5);
        Assert.assertEquals(user4, user3);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of getAllUsers method, of class LawInOrderDao.
     */
    @Test
    public void testGetAllUsers() {

        dao.addRole(admin);
        dao.addRole(author);

        dao.addUser(user1);
        dao.addUser(user2);
        dao.addUser(user3);

        List<User> inMemUsers = new ArrayList<>();
        inMemUsers.add(user1);
        inMemUsers.add(user2);
        inMemUsers.add(user3);

        List<User> retrievedUsers = new ArrayList<>();
        retrievedUsers.add(dao.getUserById(user1.getUserId()));
        retrievedUsers.add(dao.getUserById(user2.getUserId()));
        retrievedUsers.add(dao.getUserById(user3.getUserId()));

        Assert.assertEquals(inMemUsers, retrievedUsers);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of addComment method, of class LawInOrderDao.
     */
//    @Test
//    public void testAddComment() {
//
//        dao.addUser(user1);
//        dao.addPost(post1);
//
//        dao.addUser(user2);
//        dao.addPost(post2);
//
//        comment1.setPostId(post1.getPostId());
//        comment1.setUserId(user1.getUserId());
//
//        dao.addComment(comment1);
//        dao.addComment(comment2);
//
//        comment2.setPostId(post2.getPostId());
//        comment2.setUserId(user2.getUserId());
//
//        Assert.assertEquals(comment1, comment2);
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of deleteComment method, of class LawInOrderDao.
     */
//
//    public void testDeleteComment() {
//
//        dao.addComment(comment1);
//        dao.addComment(comment2);
//
//        List<Comment> memComments = new ArrayList<>();
//        memComments.add(comment1);
//        memComments.add(comment2);
//
//        dao.deleteComment(comment1.getCommentId());
//
//        List<Comment> dbComments = new ArrayList<>();
//        dbComments.add(dao.getCommentById(comment1.getCommentId()));
//        dbComments.add(dao.getCommentById(comment2.getCommentId()));
//
//        Assert.assertNotEquals(memComments.size(), dbComments.size());
//        Assert.assertNull(dao.getCommentById(comment1.getCommentId()));
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of updateComment method, of class LawInOrderDao.
     */
//    @Test
//    public void testUpdateComment() {
//
//        dao.addComment(comment1);
//        dao.addComment(comment3);
//
//        String newBody = "Now this is a nice comment";
//        comment1.setCommentBody(newBody);
//
//        dao.updateComment(comment1);
//
//        Assert.assertEquals(newBody, comment1.getCommentBody());
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of getCommentById method, of class LawInOrderDao.
     */
//    @Test
//    public void testGetCommentById() {
//
//        dao.addComment(comment1);
//        dao.addComment(comment2);
//        dao.addComment(comment3);
//        dao.addComment(comment4);
//        dao.addUser(user1);
//        dao.addUser(user2);
//        dao.addUser(user3);
//
//        User user4 = new User();
//
//        comment1.setUserId(comment1.getCommentId());
//        comment2.setUserId(comment2.getCommentId());
//        comment3.setUserId(comment3.getCommentId());
//
//        Assert.assertEquals(comment1, comment2);S
//        Assert.assertEquals(user4, user3);
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of getCommentsByUserId method, of class LawInOrderDao.
     */
//    @Test
//    public void testGetCommentsByUserId() {
//
//        dao.addComment(comment1);
//        dao.addComment(comment3);
//        dao.addComment(comment2);
//
//        comment1.setUserId(25);
//        comment2.setUserId(25);
//        comment3.setUserId(30);
//
//        List<Comment> withIdTwentyFive = dao.getCommentsByUserId(comment1.getUserId());
//
//        Assert.assertEquals(2, withIdTwentyFive.size());
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of getAllComments method, of class LawInOrderDao.
     */
//    @Test
//    public void testGetAllComments() {
//
//        dao.addComment(comment1);
//        dao.addComment(comment2);
//        dao.addComment(comment3);
//
//        List<Comment> inMemComments = new ArrayList<>();
//        inMemComments.add(comment1);
//        inMemComments.add(comment2);
//        inMemComments.add(comment3);
//
//        List<Comment> retrievedComments = new ArrayList<>();
//        retrievedComments.add(dao.getCommentById(comment1.getCommentId()));
//        retrievedComments.add(dao.getCommentById(comment2.getCommentId()));
//        retrievedComments.add(dao.getCommentById(comment3.getCommentId()));
//
//        Assert.assertEquals(inMemComments, retrievedComments);
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of addTag method, of class LawInOrderDao.
     */
    @Test
    public void testAddTag() {
        System.out.println("addTag");

        dao.addTag(tag1);
        dao.addTag(tag2);

        Tag tag3 = dao.getTagById(tag1.getTagId());
        Tag tag4 = dao.getTagById(tag2.getTagId());

        assertEquals(tag1, tag3);
        assertEquals(tag2, tag4);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of deleteTag method, of class LawInOrderDao.
     */
    @Test
    public void testDeleteTag() {
        System.out.println("deleteTag");

        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addRole(admin);
        dao.addRole(author);

        List<Tag> memTags = new ArrayList<>();
        memTags.add(tag1);
        memTags.add(tag2);

        List<Tag> retrievedTags = new ArrayList<>();
        memTags.add(dao.getTagById(tag1.getTagId()));
        memTags.add(dao.getTagById(tag2.getTagId()));

        dao.deleteTag(tag1.getTagId());

        Assert.assertNotEquals(memTags.size(), retrievedTags.size());
        Assert.assertNull(dao.getTagById(tag1.getTagId()));
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of updateTag method, of class LawInOrderDao.
     */
    @Test
    public void testUpdateTag() {
        System.out.println("updateTag");

        System.out.println("updateTag");

        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addRole(admin);
        dao.addRole(author);

        tag1.setTagName("SampleTag");

        dao.updateTag(tag1);

        Assert.assertEquals(tag1, dao.getTagById(tag1.getTagId()));
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of getTagById method, of class LawInOrderDao.
     */
    @Test
    public void testGetTagById() {
        System.out.println("getTagById");

        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addRole(admin);
        dao.addRole(author);

        Tag tag5 = dao.getTagById(tag1.getTagId());
        Tag tag4 = dao.getTagById(tag2.getTagId());

        Assert.assertEquals(tag1, tag5);
        Assert.assertEquals(tag4, tag2);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of getAllTags method, of class LawInOrderDao.
     */
    @Test
    public void testGetAllTags() {
        System.out.println("getAllTags");

        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addTag(tag3);
        dao.addRole(admin);
        dao.addRole(author);

        List<Tag> inMemTags = new ArrayList<>();
        inMemTags.add(tag1);
        inMemTags.add(tag2);
        inMemTags.add(tag3);

        List<Tag> retrievedTags = new ArrayList<>();
        retrievedTags.add(dao.getTagById(tag1.getTagId()));
        retrievedTags.add(dao.getTagById(tag2.getTagId()));
        retrievedTags.add(dao.getTagById(tag3.getTagId()));

        Assert.assertEquals(inMemTags, retrievedTags);
        ((ClassPathXmlApplicationContext) ctx).close();
    }

//    /**
//     * Test of getTagsFromPost method, of class LawInOrderDao.
//     */
//    @Test
//    public void testGetTagsFromPost() {
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of addRole method, of class LawInOrderDao.
     */
//    @Test
//    public void testAddRole() {
//        System.out.println("addRole");
//        dao.addRole(role1);
//        dao.addRole(role2);
//
//        Role role3 = dao.getRoleById(role1.getRoleId());
//        Role role4 = dao.getRoleById(role2.getRoleId());
//
//        assertEquals(role1, role3);
//        assertEquals(role2, role4);
//
//        ((ClassPathXmlApplicationContext) ctx).close();
//
//    }
//    /**
//     * Test of deleteRole method, of class LawInOrderDao.
//     */
//    @Test
//    public void testDeleteRole() {
//        System.out.println("deleteRole");
//
//        dao.addRole(role1);
//        dao.addRole(role2);
//
//        List<Role> userRoles = new ArrayList<>();
//        userRoles.add(role1);
//        userRoles.add(role2);
//
//        List<Role> dbRoles = new ArrayList<>();
//        userRoles.add(dao.getRoleById(role1.getRoleId()));
//        userRoles.add(dao.getRoleById(role2.getRoleId()));
//
//        dao.deleteRole(role1.getRoleId());
//
//        Assert.assertNotEquals(userRoles.size(), dbRoles.size());
//        Assert.assertNull(dao.getRoleById(role1.getRoleId()));
//
//        ((ClassPathXmlApplicationContext) ctx).close();
//
//    }
//
//    /**
//     * Test of updateRole method, of class LawInOrderDao.
//     */
//    @Test
//    public void testUpdateRole() {
//        System.out.println("updateRole");
//
//        dao.addRole(role1);
//        dao.addRole(role2);
//
//        role1.setRoleName("Editor");
//
//        dao.updateRole(role1);
//
//        Assert.assertEquals(role1, dao.getRoleById(role1.getRoleId()));
//
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
//
//    /**
//     * Test of getRoleById method, of class LawInOrderDao.
//     */
//    @Test
//    public void testGetRoleById() {
//        System.out.println("getRoleById");
//
//        dao.addRole(role1);
//        dao.addRole(role2);
//
//        Role role5 = dao.getRoleById(role1.getRoleId());
//        Role role4 = dao.getRoleById(role2.getRoleId());
//
//        Assert.assertEquals(role1, role5);
//        Assert.assertEquals(role4, role2);
//
//        ((ClassPathXmlApplicationContext) ctx).close();
//
//    }
//
//    /**
//     * Test of getAllRoles method, of class LawInOrderDao.
//     */
//    @Test
//    public void testGetAllRoles() {
//        System.out.println("getAllRoles");
//
//        dao.addRole(role1);
//        dao.addRole(role2);
//        dao.addRole(role3);
//
//        List<Role> inMemRoles = new ArrayList<>();
//        inMemRoles.add(role1);
//        inMemRoles.add(role2);
//        inMemRoles.add(role3);
//
//        List<Role> retrievedRoles = new ArrayList<>();
//        retrievedRoles.add(dao.getRoleById(role1.getRoleId()));
//        retrievedRoles.add(dao.getRoleById(role2.getRoleId()));
//        retrievedRoles.add(dao.getRoleById(role3.getRoleId()));
//
//        Assert.assertEquals(inMemRoles, retrievedRoles);
//
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    @Test
    public void testGetPostByTitle() {
        dao.addRole(admin);
        dao.addRole(author);
        dao.addUser(user1);
        dao.addUser(user2);

        post1.setUserId(user1.getUserId());
        post2.setUserId(user2.getUserId());
        post3.setUserId(user1.getUserId());

        dao.addPost(post1);
        dao.addPost(post2);
        dao.addPost(post3);

        PostRelational post4 = (PostRelational) dao.getPostByTitle(post1.getPostTitle());

        Assert.assertEquals(post1.getPostBody(), post4.getPostBody());

        ((ClassPathXmlApplicationContext) ctx).close();

    }

    /**
     * Test of addPost method, of class LawInOrderDao.
     */
//    @Test
//    public void testAddPost() {
//
//        dao.addPost(post1);
//        dao.addPost(post2);
//
//        Post post3 = dao.getPostById(post1.getPostId());
//        Post post4 = dao.getPostById(post2.getPostId());
//
//        assertEquals(post1, post3);
//        assertEquals(post2, post4);
//
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of deletePost method, of class LawInOrderDao.
     */
    @Test
    public void testDeletePost() {

        dao.addRole(admin);
        dao.addRole(author);

        dao.addUser(user1);

        post1.setUserId(user1.getUserId());
        post2.setUserId(user1.getUserId());

        dao.addPost(post1);
        dao.addPost(post2);

        List<Post> memPosts = new ArrayList<>();
        memPosts.add(post1);
        memPosts.add(post2);

        Assert.assertNotNull(dao.getPostById(post1.getPostId()));

        dao.deletePost(post1.getPostId());

        List<Post> dbPosts = new ArrayList<>();
        dbPosts.add(dao.getPostById(post1.getPostId()));
        dbPosts.add(dao.getPostById(post2.getPostId()));

        //Assert.assertNotEquals(memPosts.size(), dbPosts.size());
        Assert.assertNull(dao.getPostById(post1.getPostId()));
        ((ClassPathXmlApplicationContext) ctx).close();
    }

//    /**
//     * Test of updatePost method, of class LawInOrderDao.
//     */
    @Test
    public void testUpdatePost() {

    }
//
//    /**
//     * Test of getPostById method, of class LawInOrderDao.
//     */
//    @Test
//    public void testGetPostById() {
//        
//        dao.addPost(post1);
//        dao.addPost(post2);
//
//        Post post4 = dao.getPostById(post1.getPostId());
//        Post post5 = dao.getPostById(post2.getPostId());
//
//        Assert.assertEquals(post1, post4);
//        Assert.assertEquals(post2, post5);
//        
//        ((ClassPathXmlApplicationContext)ctx).close();
//    }
    /**
     * Test of getPostsByUserId method, of class LawInOrderDao.
     */
//    @Test
//    public void testGetPostsByUserId() {
//      
//         // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//        ((ClassPathXmlApplicationContext)ctx).close();
//    }
    /**
     * Test of getAllPosts method, of class LawInOrderDao.
     */
//    @Test
//    public void testGetAllPosts() {
//        
//        
//         // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//       ((ClassPathXmlApplicationContext)ctx).close();
//    }
    /**
     * Test of getPostsByTag method, of class LawInOrderDao.
     */
//    @Test
//    public void testGetPostsByTag() {
//       
//         // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//        ((ClassPathXmlApplicationContext)ctx).close();
//    }
//
    /**
     * Test of addStaticPage method, of class LawInOrderDao.
     */
//    @Test
//    public void testAddStaticPage() {
//
//        dao.addPost(post1);
//        dao.addPost(post2);
//
//        Post post3 = dao.getPostById(post1.getPostId());
//        Post post4 = dao.getPostById(post2.getPostId());
//
//        assertEquals(post1, post3);
//        assertEquals(post2, post4);
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of deleteStaticPage method, of class LawInOrderDao.
     */
//    @Test
//    public void testDeleteStaticPage() {
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of updateStaticPage method, of class LawInOrderDao.
     */
//    @Test
//    public void testUpdateStaticPage() {
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//     (
//
//    (ClassPathXmlApplicationContext)ctx).close();
//    /**
//     * Test of getStaticPageByPostId method, of class LawInOrderDao.
//     */
//    @Test
//    public void testGetStaticPageByPostId() {
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//        ((ClassPathXmlApplicationContext) ctx).close();
//    }
    /**
     * Test of getStaticPagesByUserId method, of class LawInOrderDao.
     */
//    @Test
//    public void testGetStaticPagesByUserId() {
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//        ((ClassPathXmlApplicationContext) ctx).close();
//        @Test
//        public void testGetUserByUsername
//        
//            () {
//
//        dao.addUser(user1);
//
//            User user4 = dao.getUserByUsername("Author McAuthor").get(0);
//
//            Assert.assertEquals(user1, user4);
//            ((ClassPathXmlApplicationContext) ctx).close();
//        }
//        @Test
//        public void testAddStaticPage() {
//            dao.addStaticPage(page1);
//            dao.addStaticPage(page2);
//            
//            StaticPage page3 = dao.getStaticPageByPostId(page1.getPostId());
//            StaticPage page4 = dao.getStaticPageByPostId(page2.getPostId());((ClassPathXmlApplicationContext)ctx).close();
//        }
//        //    @Test
////    public void testAddPost() {
////        
////        dao.addPost(post1);
////        dao.addPost(post2);        
////
////        Post post3 = dao.getPostById(post1.getPostId());
////        Post post4 = dao.getPostById(post2.getPostId());
////
////        assertEquals(post1, post3);
////        assertEquals(post2, post4);
////        
////       ((ClassPathXmlApplicationContext)ctx).close();
////    }
    //TESTS FOR PENDING LIST
    @Test
    public void testPendingListMethods() {

        dao.addRole(admin);
        dao.addRole(author);

        dao.addUser(user1);
        dao.addUser(user2);

        List<User> userList = dao.getAllUsers();
        assertEquals(userList.size(), 2);

        int user1ID = userList.get(0).getUserId();
        int user2ID = userList.get(1).getUserId();

        post1 = new PostRelational();
        post1.setPostTitle("This is Post One");
        post1.setPostBody("<p>This is a dummy post with some <strong>HTML</strong></p>");
        post1.setUserId(user1ID);
        post1.setIsPrivate(false);
        post1.setPostExcerpt("<p>This is a $hellip;</p>");
        post1.setPubStatus(PubStatus.DRAFT);
        post1.setPostType(PostType.POST);
        post1.setPublishDate((LocalDate.of(2015, Month.JUNE, 11)));
        post1.setLastEditDate(LocalDate.of(2015, Month.AUGUST, 15));

        post2 = new PostRelational();
        post2.setPostTitle("This is Post Two");
        post2.setPostBody("<p>This is another dummy post with some <strong>HTML</strong></p>");
        post2.setUserId(user2ID);
        post2.setIsPrivate(false);
        post2.setPostExcerpt("<p>This is another $hellip;</p>");
        post2.setPubStatus(PubStatus.PUBLISH);
        post2.setPostType(PostType.POST);
        post2.setPublishDate((LocalDate.of(2015, Month.NOVEMBER, 20)));
        post2.setLastEditDate(LocalDate.of(2015, Month.DECEMBER, 1));

        post3 = new PostRelational();
        post3.setPostTitle("This is Post Three");
        post3.setPostBody("<p>This is a third dummy post with some <strong>HTML</strong></p>");
        post3.setUserId(user1ID);
        post3.setIsPrivate(true);
        post3.setPostExcerpt("<p>This is a third $hellip;</p>");
        post3.setPubStatus(PubStatus.DRAFT);
        post3.setPostType(PostType.POST);
        post3.setPublishDate((LocalDate.of(2015, Month.SEPTEMBER, 10)));
        post3.setLastEditDate(LocalDate.of(2015, Month.DECEMBER, 2));

        post4 = new PostRelational();
        post4.setPostTitle("This is Post Four");
        post4.setPostBody("<p>This is a fourth dummy post with some <strong>HTML</strong></p>");
        post4.setUserId(user2ID);
        post4.setIsPrivate(true);
        post4.setPostExcerpt("<p>This is a fourth $hellip;</p>");
        post4.setPubStatus(PubStatus.DRAFT);
        post4.setPostType(PostType.POST);
        post4.setPublishDate((LocalDate.of(2015, Month.JUNE, 10)));
        post4.setLastEditDate(LocalDate.of(2015, Month.DECEMBER, 2));

        dao.addPost(post1);
        dao.addPost(post2);
        dao.addPost(post3);
        dao.addPost(post4);

        List<Post> expected = new ArrayList<>();

        expected.add(post4);
        expected.add(post1);
        expected.add(post3);

        List<Post> result = dao.getPendingList();

        assertEquals(expected, result);

        expected = new ArrayList<>();

        expected.add(post1);
        expected.add(post3);

        result = dao.getPendingListViewById(user1ID);

        assertEquals(expected, result);

    }

}
