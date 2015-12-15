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
import static com.thesoftwareguild.lawinorder.dto.PostType.POST;
import com.thesoftwareguild.lawinorder.dto.PubStatus;
import static com.thesoftwareguild.lawinorder.dto.PubStatus.DRAFT;
import static com.thesoftwareguild.lawinorder.dto.PubStatus.PUBLISH;
import com.thesoftwareguild.lawinorder.dto.Publication;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.jsoup.*;

/**
 *
 * @author apprentice
 */

public class PostPublisherTest {

    LawInOrderDao dao;
    PostPublisherImpl publisher;

    Publication newContent1;
    Publication newContent2;
    Publication newContent3;

    //PostPublisher publisher = new PostPublisherImpl(newContent, dao);
    User user1;
    User user2;
    User user3;

    Tag tag1;
    Tag tag2;
    Tag tag3;

    PostRelational post1;
    PostRelational post2;
    PostRelational post3;

    Comment comment1;
    Comment comment2;
    Comment comment3;

    StaticPage page1;
    StaticPage page2;
    StaticPage page3;

    ApplicationContext ctx;

    public PostPublisherTest() {
    }

    @Before
    public void setUp() {

        ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("dao", LawInOrderDao.class);
        publisher = ctx.getBean("publisher", PostPublisherImpl.class);

        //publisher = new PostPublisherImpl(dao);
        newContent1 = new Publication();
        newContent2 = new Publication();
        newContent3 = new Publication();

        tag1 = new Tag();
        tag1.setTagName(" test");

        tag2 = new Tag();
        tag2.setTagName("tag ");

        tag3 = new Tag();
        tag3.setTagName(" sample  ");

        comment1 = new Comment();
        comment1.
                setCommentBody("This is an comment on the Internet, pay it no heed");
        comment1.setCommentDate(LocalDate.of(2015, Month.DECEMBER, 1));
        comment1.setPostId(1);
        comment1.setUserId(25);

        comment2 = new Comment();
        comment2.
                setCommentBody("This is an comment on the Internet, pay it no heed");
        comment2.setCommentDate(LocalDate.of(2015, Month.DECEMBER, 1));
        comment2.setPostId(1);
        comment2.setUserId(25);

        comment3 = new Comment();
        comment3.
                setCommentBody("This is an comment on the Internet, pay it no heed");
        comment3.setCommentDate(LocalDate.of(2015, Month.DECEMBER, 1));
        comment3.setPostId(1);
        comment3.setUserId(30);

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);

        newContent1.
                setPostBody("<p>Contrary to popular belief, Lorem Ipsum is not simply random text. "
                        + "It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. "
                        + "Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, "
                        + "consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. "
                        + "Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written "
                        + "in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum "
                        + "dolor sit amet..\", comes from a line in section 1.10.32.</p></p> "
                        + "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by "
                        + "Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.");
        newContent1.setPostTitle("This is a Title");
        newContent1.setAuthor("Author McAuthor");
        newContent1.setPublishDateString("2015-12-01");
        newContent1.setLastEditDateString("2015-12-01");

        String[] tags = new String[3];
        tags[0] = tag1.getTagName();
        tags[1] = tag2.getTagName();
        tags[2] = tag3.getTagName();

        newContent1.setTags(tags);
        newContent1.setPostType(POST);
        newContent1.setPubStatus(PUBLISH);
        newContent1.setComments(commentList);
        newContent1.setIsPrivate(false);

        newContent2.
                setPostBody("<p>This is another test. Has a short post body.</p>");
        newContent2.setPostTitle("Hello Again, Unit Test");
        newContent2.setPublishDateString("2015-03-05");
        newContent2.setLastEditDateString("2015-01-01");
        String[] tags2 = new String[4];
        tags2[0] = tag1.getTagName();
        tags2[1] = tag2.getTagName();
        tags2[2] = tag3.getTagName();
        tags2[3] = "This is a new tag.";

        newContent2.setTags(tags2);
        newContent2.setAuthor("Author McAuthor");
        newContent2.setPostType(POST);
        newContent2.setPubStatus(DRAFT);
        newContent2.setIsPrivate(false);

        newContent3.
                setPostBody("<p>This is another test. Has a short post body.</p>");
        newContent3.setPostTitle("Hello Again, Unit Test");
        newContent3.setPublishDateString("2015-03-05");
        newContent3.setLastEditDateString("2015-01-01");
        newContent3.setTags(tags);
        newContent3.setAuthor("Testy Testerson");
        newContent3.setPostType(POST);
        newContent3.setPubStatus(DRAFT);
        newContent3.setIsPrivate(false);

        user1 = new User();
        user1.setUsername("Author McAuthor");
        user1.setEmail("author@lawinorder.com");
        user1.setPassword("123abcNotSecureAtAll");

        user2 = new User();
        user2.setUsername("Testy Testerson");
        user2.setEmail("testy@lawinorder.com");
        user2.setPassword("storedinplaintextyayayay");
        dao.addUser(user2);

        user3 = new User();
        user3.setUsername("Commenty Commenter");
        user3.setEmail("troll@troll.com");
        user3.setPassword("hi");
        dao.addUser(user3);

        

        post3 = new PostRelational();
        post3.setPostTitle("This is Post Three");
        post3.
                setPostBody("<p>This is a third dummy post with some <strong>HTML</strong></p>");
        post3.setUserId(0);
        post3.setIsPrivate(true);
        post3.setPostExcerpt("<p>This is a third $hellip;</p>");
        post3.setPubStatus(PubStatus.PUBLISH);
        post3.setPostType(PostType.STATIC_PAGE);
        post3.setPublishDate((LocalDate.of(2015, Month.SEPTEMBER, 10)));
        post3.setLastEditDate(LocalDate.of(2015, Month.DECEMBER, 2));

        JdbcTemplate cleanup = ctx.getBean("jdbcTemplate", JdbcTemplate.class);

        cleanup.update("DELETE FROM tags_posts");
        cleanup.update("DELETE FROM tags");
        cleanup.update("DELETE FROM posts_urls");
        cleanup.update("DELETE FROM posts");
        cleanup.update("DELETE FROM comments");
cleanup.update("DELETE FROM users_roles");
        cleanup.update("DELETE FROM users");
        cleanup.update("DELETE FROM roles");
        

    }

    @After
    public void tearDown() {

        ((ClassPathXmlApplicationContext) ctx).close();
    }

    /**
     * Test of processNewPublication method, of class PostPublisherImpl.
     */
    @Test
    public void testProcessNewPublicationReturn() {

        System.out.println("processNewPublication");
        dao.addUser(user1);
        dao.addUser(user2);

        Publication newContent4 = publisher.processNewPublication(newContent1);
        String nc1Parse = (Jsoup.parse(newContent1.getPostBody())).
                select("p:eq(0)").toString();
        String nc4Parse = (Jsoup.parse(newContent4.getPostBody())).
                select("p:eq(0)").toString();

        //targetted elements of processed newContent are the same as those
        //of inputted newContent (not all 'equal' b/c processed appropriately)
        Assert.assertEquals(newContent1.getAuthor(), newContent4.getAuthor());
        Assert.
                assertEquals(nc1Parse, nc4Parse);

        Assert.
                assertEquals(newContent1.getTags().length, newContent4.getTags().length);

        Assert.assertEquals(newContent1.getPublishDateString(), newContent4.
                getPublishDate().toString());
    }

    @Test
    public void testProcessNewPublicationPostWrite() {

        dao.addUser(user1);
        dao.addUser(user2);

        System.out.println("processNewPublication check post output");

        Publication newContent4 = publisher.processNewPublication(newContent2);

        Post post4 = dao.getPostById(newContent4.getPostId());

        Assert.assertEquals(newContent4.getPostBody(), post4.getPostBody());
        //body of write-able post equals body of 'processed' newContent

    }

    @Test
    public void testProcessNewPublicationUserExtraction() {

        dao.addUser(user1);
        dao.addUser(user2);
        dao.addUser(user3);

        System.out.println("processNewPublication check user extraction");

        newContent1 = publisher.processNewPublication(newContent1);
        newContent2 = publisher.processNewPublication(newContent2);
        newContent3 = publisher.processNewPublication(newContent3);

        User author1 = dao.getUserByUsername(newContent1.getAuthor()).get(0);
        User author2 = dao.getUserByUsername(newContent2.getAuthor()).get(0);
        User author3 = dao.getUserByUsername(newContent3.getAuthor()).get(0);

        Assert.assertNotEquals(user1, author3);
        Assert.assertEquals(user1, author2);
        Assert.assertEquals(user1, author1);

    }

    @Test
    public void testProcessNewPublicationTagExtractionNewTag() {

        dao.addUser(user1);
        dao.addUser(user2);
        dao.addUser(user3);

        System.out.println("processNewPublication tag extraction new");

        newContent1 = publisher.processNewPublication(newContent1);

        List<Tag> allTags = dao.getAllTags();

        newContent2 = publisher.processNewPublication(newContent2);
        newContent3 = publisher.processNewPublication(newContent3);

        List<Tag> allTagsAfter = dao.getAllTags();

        Assert.assertEquals(4, allTagsAfter.size());
        Assert.assertNotEquals(allTags.size(), allTagsAfter.size());

    }

    @Test
    public void testProcessNewPublicationTagExtractionExistingTag() {

        dao.addUser(user1);
        dao.addUser(user2);
        dao.addUser(user3);

        System.out.println("processNewPublication tag extraction existing");

        Tag tag4 = new Tag();
        tag4.setTagName("test");

        Tag tag5 = new Tag();
        tag5.setTagName("tag");

        Tag tag6 = new Tag();
        tag6.setTagName("sample");

        dao.addTag(tag4);
        dao.addTag(tag5);
        dao.addTag(tag6);

        newContent1 = publisher.processNewPublication(newContent1);

        List<Tag> allTags = dao.getAllTags();

        newContent2 = publisher.processNewPublication(newContent2);
        newContent3 = publisher.processNewPublication(newContent3);

        List<Tag> allTagsAfter = dao.getAllTags();

        Assert.assertEquals(4, allTagsAfter.size());
        Assert.assertNotEquals(allTags.size(), allTagsAfter.size());

    }

    @Test
    public void testPublicationBuilder() {

        dao.addUser(user1);
        dao.addUser(user2);

        
        
        post1 = new PostRelational();
        post1.setPostTitle("This is Post One");
        post1.
                setPostBody("<p>This is a dummy post with some <strong>HTML</strong></p>");
        post1.setIsPrivate(false);
        post1.setPostExcerpt("<p>This is a dummy post with some <strong>HTML</strong></p>");
        post1.setPubStatus(PubStatus.DRAFT);
        post1.setPostType(PostType.POST);
        post1.setPublishDate((LocalDate.of(2015, Month.JUNE, 11)));
        post1.setLastEditDate(LocalDate.now());

        post2 = new PostRelational();
        post2.setPostTitle("This is Post Two");
        post2.
                setPostBody("<p>This is another dummy post with some <strong>HTML</strong></p>");
        post2.setUserId(1);
        post2.setIsPrivate(false);
        post2.setPostExcerpt("<p>This is another dummy post with some <strong>HTML</strong></p>");
        post2.setPubStatus(PubStatus.PUBLISH);
        post2.setPostType(PostType.POST);
        post2.setPublishDate((LocalDate.of(2015, Month.NOVEMBER, 20)));
        post2.setLastEditDate(LocalDate.of(2015, Month.DECEMBER, 1));
        
        post1.setUserId(user1.getUserId());
        post2.setUserId(user2.getUserId());
        dao.addPost(post1);
        dao.addPost(post2);
        
        Publication newContent4 = new Publication();
        Publication newContent5 = new Publication();

        newContent4.
                setPostBody("<p>This is a dummy post with some <strong>HTML</strong></p>");
        newContent4.setPostTitle("This is Post One");
        newContent4.setPublishDateString("2015-06-11");
        newContent4.setLastEditDateString("2015-08-15");
        //newContent4.setTags(tags);
        newContent4.setAuthor("Author McAuthor");
        newContent4.setPostType(PostType.POST);
        newContent4.setPubStatus(PubStatus.DRAFT);
        newContent4.setIsPrivate(false);
        
                
        newContent4 = publisher.processNewPublication(newContent4);
        
        newContent1 = publisher.publicationBuilder(post1);
                       
        Assert.assertEquals(newContent4.getPostBody(), newContent1.getPostBody());
        Assert.assertEquals(newContent4.getPostType(), newContent1.getPostType());
        Assert.assertEquals(newContent4.getUserId(), newContent1.getUserId());
        Assert.assertEquals(newContent4.getIsPrivate(), newContent1.getIsPrivate());
        Assert.assertEquals(newContent4.getPublishDate(), newContent1.getPublishDate());
        Assert.assertEquals(newContent4.getLastEditDate(), newContent1.getLastEditDate());
        Assert.assertEquals(newContent4.getPostExcerpt(), newContent1.getPostExcerpt());
        Assert.assertEquals(newContent4.getPubStatus(), newContent1.getPubStatus());


    }

    
    @Test
    public void testPublicationBuilderFirstDuplicate(){
        
        dao.addUser(user1);
        dao.addUser(user2);
        
        
        Publication pub1 = publisher.processNewPublication(newContent1);
        newContent2.setPostTitle(newContent1.getPostTitle());
        
        Publication pub2 = publisher.processNewPublication(newContent2);
        
        
        
        Assert.assertEquals(pub1.getPostTitle() + "_1", pub2.getPostTitle());
        
                
    }
    
    @Test
    public void testPublicationBuilderMultipleDuplicateTitles(){
        
        dao.addUser(user1);
        dao.addUser(user2);
        
        
        Publication pub1 = publisher.processNewPublication(newContent1);
        newContent2.setPostTitle(newContent1.getPostTitle());
        
        Publication pub2 = publisher.processNewPublication(newContent2);
        Publication pub3 = publisher.processNewPublication(newContent2);
        Publication pub4 = publisher.processNewPublication(newContent2);
        Publication pub5 = publisher.processNewPublication(newContent2);
        Publication pub6 = publisher.processNewPublication(newContent2);
        Publication pub7 = publisher.processNewPublication(newContent2);
        Publication pub8 = publisher.processNewPublication(newContent2);
        Publication pub9 = publisher.processNewPublication(newContent2);
        Publication pub10 = publisher.processNewPublication(newContent2);
        Publication pub11 = publisher.processNewPublication(newContent2);
        
        
        Assert.assertEquals(pub1.getPostTitle() + "_1", pub2.getPostTitle());
        Assert.assertEquals(pub1.getPostTitle() + "_9", pub10.getPostTitle());
        Assert.assertEquals(pub1.getPostTitle() + "_10", pub11.getPostTitle());
        
        
        
        
    }
    
    
}
