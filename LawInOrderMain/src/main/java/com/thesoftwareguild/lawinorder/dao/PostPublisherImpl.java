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
import static com.thesoftwareguild.lawinorder.dto.PostType.STATIC_PAGE;
import com.thesoftwareguild.lawinorder.dto.PubStatus;
import com.thesoftwareguild.lawinorder.dto.Publication;
import com.thesoftwareguild.lawinorder.dto.StaticPage;
import com.thesoftwareguild.lawinorder.dto.Tag;
import com.thesoftwareguild.lawinorder.dto.User;
import java.time.LocalDate;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.*;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author calarrick
 */
public class PostPublisherImpl implements PostPublisher {

    private Post post;
    private User user;
    private List<Comment> comments;
    private List<Tag> tagsList;
    private String[] postTags;
    private Publication newContent;
    LawInOrderDao dao;

    //for use by Spring core DI configuration
    public void setDao(LawInOrderDao dao) {
        this.dao = dao;
    }

    @Override
    public Publication processNewPublication(Publication newContent) {

        this.newContent = newContent;
        
        if (newContent.getPostType().equals(POST)) {
            this.post = new PostRelational();
        }

        if (newContent.getPostType().equals(STATIC_PAGE)) {
            this.post = new StaticPage();

        }

        processPostUser();
        makeNewPostEntry();
        processPostTags();

        Publication processedContent = rebuildViewObject();

        return processedContent;

    }

    @Override
    public Publication processNewPublication(int id, Publication updateContent) {

        newContent = updateContent;
        
        
        
//        if (!newContent.getPostType().toString().equals(newContent.getOldPostType())){
//            
//            if(newContent.getPostType().equals(POST)){
//            
//                dao.deleteStaticPage(id);
//        }
//            else if (newContent.getPostType().equals(STATIC_PAGE)){
//                
//                dao.deletePost(id);
//                
//            }
//        }
        
        
        processPostUser();

        makeNewPostEntry(id);
        processExistingPostTags();

        Publication processedContent = rebuildViewObject();
        return processedContent;

    }

    @Override
    public Publication publicationBuilder(Post post) {

        this.post = post;

        return rebuildViewObject();
    }

    private void processPostUser() {

        String author = newContent.getAuthor();
        List<User> userMatched = dao.getUserByUsername(author);
        //implement above once do method in place
        //should be more efficient to do via query

//        List<User> userMatched = dao.getAllUsers().stream()
//                .filter(u -> u.getUsername().equals(author))
//                .collect(Collectors.toList());
        if (userMatched.size() > 0) {
//            post.setUserId(userMatched.get(0).getUserId());
            //assumes only one matched user

            user = userMatched.get(0);
            post.setUserId(user.getUserId());
        }
    }

    private void processExistingPostTags() {

        //avoids "multiplying" post tag lists when an edit is re-submitted
        //this does allow editor to add or remove tags from a post
        if (post.getPostType().equals(STATIC_PAGE)) {

            dao.deleteTagsPageEntry(post.getPostId());
        } else {
            dao.deleteTagsPostEntry(post.getPostId());
        }

        processPostTags();

    }

    private void processPostTags() {

        postTags = newContent.getTags();

        if (postTags != null && !((postTags[0]).equals(""))) {
            List<Tag> allTags = dao.getAllTags();
            //tagsList = new ArrayList<>();

            for (String newTag : postTags) {

                newTag = Jsoup.clean(newTag.trim(), Whitelist.simpleText());
                boolean tagMatch = false;
                int i = 0;

                //pull whole list of existing tags for site. For each tag in the new
                //post it iterates through whole tag list looking for match.
                while (tagMatch == false && i < postTags.length) {
                    i++;
                    for (Tag oldTag : allTags) {
                        if (oldTag.getTagName().equals(newTag)) {
                            tagMatch = true;

                            if (newContent.getPostType().equals(PostType.POST)) {
                                dao.
                                        addTagsPostEntry(oldTag.getTagId(), post.
                                                getPostId());
                            } else if (newContent.getPostType().
                                    equals(PostType.STATIC_PAGE)) {
                                dao.
                                        addTagsPageEntry(oldTag.getTagId(), post.
                                                getPostId());
                            }

                        }
                    }

                }

                if (tagMatch == false) {

                    Tag tag = new Tag();

                    tag.setTagName(newTag);

                    dao.addTag(tag);
                    //creates new tag

                    //matches post or page to it
                    if (newContent.getPostType().equals(PostType.POST)) {
                        dao.addTagsPostEntry(tag.getTagId(), post.getPostId());
                    } else if (newContent.getPostType().
                            equals(PostType.STATIC_PAGE)) {
                        dao.addTagsPageEntry(tag.getTagId(), post.getPostId());
                    }
                }
                //make sure this works with enforcement of unique tags

            }
        }

    }

    //Checks to see if first there are any static pages with given title, and then regular posts.
    private boolean isNewTitle(String newTitle) {

        Post postCheck;

        
        postCheck = dao.getStaticPageByTitle(newTitle);

        if (postCheck != null) {
            return false;
        } else {
            
            postCheck = dao.getPostByTitle(newTitle);
        }

        return postCheck == null;

    }

    private void makeNewPostEntry() {

        post.setPostTitle(Jsoup.clean(newContent.getPostTitle(), Whitelist.
                simpleText()));
        String newTitle = post.getPostTitle();

        //check if same-named post exists
        int i = 1;
        Boolean isNewTitle = isNewTitle(newTitle);

        //safely rename as necessary
        while (!isNewTitle) {
            newTitle = (post.getPostTitle() + "_" + i + "");
            isNewTitle = isNewTitle(newTitle);
            i++;
        }
        post.setPostTitle(newTitle);

        post.setPublishDate(LocalDate.
                parse(newContent.getPublishDateString(), ISO_LOCAL_DATE));

        post.setPostBody(Jsoup.clean(newContent.getPostBody(), Whitelist.
                relaxed()));
        post.setPostExcerpt(prepExcerpt(Jsoup.clean(newContent
                .getPostBody(), Whitelist.basic())));

        post.setLastEditDate(LocalDate.now());
        post.setIsPrivate(newContent.getIsPrivate());
        post.setPubStatus(newContent.getPubStatus());

        post.setPostType(newContent.getPostType());

        if (post.getPostType().equals(POST)) {
            dao.addPost(post);
        } else if (post.getPostType().equals(STATIC_PAGE)) {
            dao.addStaticPage(post);
        }
        
        
        
        
        
        

    }

    private void makeNewPostEntry(int id) {
        
        post.setPostTitle(Jsoup.clean(newContent.getPostTitle(), Whitelist.
                simpleText()));
        

        post.setPublishDate(LocalDate.
                parse(newContent.getPublishDateString(), ISO_LOCAL_DATE));

        post.setPostBody(Jsoup.clean(newContent.getPostBody(), Whitelist.
                relaxed()));
        post.setPostExcerpt(prepExcerpt(Jsoup.clean(newContent
                .getPostBody(), Whitelist.basic())));

        post.setLastEditDate(LocalDate.now());
        post.setIsPrivate(newContent.getIsPrivate());
        post.setPubStatus(newContent.getPubStatus());

        post.setPostType(newContent.getPostType());

        if (post.getPostType().equals(POST)) {
            dao.updatePost(post);
        } else if (post.getPostType().equals(STATIC_PAGE)) {
            dao.updateStaticPage(post);
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

//        Post titleChecker = null;
//        post.setPostId(id);
//        post.setPostType(newContent.getPostType());
//
//        post.setPostTitle(Jsoup.clean(newContent.getPostTitle(), Whitelist.
//                simpleText()));
//        String newTitle = post.getPostTitle();
//
//        
//        post.setPubStatus(newContent.getPubStatus());
//        
//
//        //check for previous title of any post or page with this postId
//        if (post.getPostType().equals(STATIC_PAGE)) {
//            titleChecker = dao.getStaticPageByPostId(id);
//        } else if (post.getPostType().equals(POST)) {
//            titleChecker = dao.getPostById(id);
//        }
//
//        //check if same-named post exists that is NOT the post
//        //being updated
//        //matters if user changes title of post to something else 
//        //that already exists. Want to prevent that without requiring
//        //change to title of actual post being edited.
//        if (titleChecker != null) {
//            if (!post.getPostTitle().equals(titleChecker.getPostTitle())) {
//
//                int i = 1;
//                Boolean isNewTitle = isNewTitle(newTitle);
//
//                //safely rename as necessary
//                while (!isNewTitle) {
//                    newTitle = (post.getPostTitle() + "_" + i + "");
//                    isNewTitle = isNewTitle(newTitle);
//                    i++;
//                }
//            }
//        }
//
//        post.setPostTitle(newTitle);
//
//        post.setPublishDate(LocalDate.
//                parse(newContent.getPublishDateString(), ISO_LOCAL_DATE));
//
//        post.setPostBody(Jsoup.clean(newContent.getPostBody(), Whitelist.
//                relaxed()));
//        post.setPostExcerpt(prepExcerpt(Jsoup.clean(newContent
//                .getPostBody(), Whitelist.basic())));
//
//        post.setLastEditDate(LocalDate.now());
//        post.setIsPrivate(newContent.getIsPrivate());
//        
//
//        post.setPostType(newContent.getPostType());
//
//        
//        if (post.getPostType().equals(STATIC_PAGE)) {
//
//            Post postOld = dao.getPostById(post.getPostId());
//
//            if (postOld != null) {
//                //still need to see if update will be valid SQL
//                Post existsCheck = dao.getStaticPageByPostId(id);
//                if (existsCheck != null){
//                    dao.updateStaticPage(post);
//                    }
//                else {
//                    dao.addStaticPage(post);
//                    }
//                dao.deletePost(id);
//                
//                
//            } else if (post.getPostType().equals(POST)) {
//
//                Post pageOld = dao.getStaticPageByPostId(id);
//                if (pageOld != null) {
//                    Post existsCheck = dao.getPostById(id);
//                    if (existsCheck != null){
//                        dao.updatePost(post);
//                    }
//                    else {
//                        dao.addPost(post);
//                    }
//                    
//                    dao.deleteStaticPage(id);
//                }
//
//            }
//
//        }
//        

    }

    private String prepExcerpt(String body) {

        String excerpt;
        String excerptRough;
        String excerpt1;
        String excerpt2;

        if (body.length() > 575) {
            excerpt1 = body.substring(0, 575);
            excerpt2 = body.substring(576, 776);
            String[] split = excerpt2.split("</p>");
            //want to ensure no mid-word split
            excerpt2 = split[0] + "&hellip; ";
            excerptRough = excerpt1 + excerpt2;
            excerpt = Jsoup.parse(excerptRough).toString();
        } else {
            excerpt = body;
        }

        return excerpt;

    }

    private Publication rebuildViewObject() {

        Publication rebuiltPublication = new Publication();

        rebuiltPublication.setPostId(post.getPostId());
        rebuiltPublication.setPostTitle(post.getPostTitle());

        rebuiltPublication.setUserId(post.getUserId());
        rebuiltPublication
                .setAuthor(dao.getUserById(rebuiltPublication.getUserId()).
                        getUsername());

        rebuiltPublication.setPostBody(post.getPostBody());

        if (post.getPostExcerpt().isEmpty() || post.getPostExcerpt() == null) {

            rebuiltPublication.setPostExcerpt(prepExcerpt(post.getPostBody()));
        } else {
            rebuiltPublication.setPostExcerpt(post.getPostExcerpt());
        }

        rebuiltPublication.setPublishDate(post.getPublishDate());
        rebuiltPublication.setLastEditDate(post.getLastEditDate());

        rebuiltPublication.setIsPrivate(post.getIsPrivate());

        rebuiltPublication.setPubStatus(post.getPubStatus());
        rebuiltPublication.setPostType(post.getPostType());

        if (post.getPostType().equals(PostType.STATIC_PAGE)) {
            rebuiltPublication.setPostUrl(dao.getUrlByPageId(rebuiltPublication.
                    getPostId()));
        } else {

            rebuiltPublication.setPostUrl(dao.getUrlByPostId(rebuiltPublication.
                    getPostId()));

        }

        List<Tag> tagList = new ArrayList<>();
        if (post.getPostType().equals(PostType.POST)) {
            tagList = dao.getTagsFromPost(post.getPostId());
        } else if (post.getPostType().equals(PostType.STATIC_PAGE)) {
            tagList = dao.getTagsFromPage(post.getPostId());
        }

        String[] rebuiltTags = new String[tagList.size()];

        for (int i = 0; i < tagList.size(); i++) {
            rebuiltTags[i] = tagList.get(i).getTagName().trim();
        }

        rebuiltPublication.setTags(rebuiltTags);

        return rebuiltPublication;
    }

    @Override
    public void removePostPageFromActive(int id) {

        Post trashPost = dao.getPostById(id);
        trashPost.setPubStatus(PubStatus.DELETE);

        dao.updatePost(post);

    }

}
