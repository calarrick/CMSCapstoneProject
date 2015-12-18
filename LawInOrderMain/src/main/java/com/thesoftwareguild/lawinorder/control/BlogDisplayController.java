/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.control;

import com.thesoftwareguild.lawinorder.dao.LawInOrderDao;
import com.thesoftwareguild.lawinorder.dao.PostPublisher;
import com.thesoftwareguild.lawinorder.dto.PubStatus;
import com.thesoftwareguild.lawinorder.dto.Publication;
import com.thesoftwareguild.lawinorder.dto.Role;
import com.thesoftwareguild.lawinorder.dto.Tag;
import com.thesoftwareguild.lawinorder.exception.ResourceNotFoundException;
import com.thesoftwareguild.lawinorder.dto.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author calarrick
 */
@Controller
@RequestMapping({"/"})
public class BlogDisplayController {

    private final LawInOrderDao dao;
    private final PostPublisher pub;

    //SET TO FALSE TO DISABLE RESET FROM FRONT END

    private boolean resetOn = true;


    @Inject
    public BlogDisplayController(LawInOrderDao dao, PostPublisher publisher) {
        this.dao = dao;
        this.pub = publisher;

        //temporary hard-coded user and role
        //using this on *first* run of project in your build
        //will provide these
        //shouldn't need anymore with ability to create user on front end
//    User user1 = new User();
//    Role role1 = new Role();
//    user1.setUsername("Author McAuthor");
//    user1.setEmail("author@author.com");
//    user1.setPassword("password");
//    role1.setRoleName("ROLE_ADMIN");
//    dao.addUser(user1);
//    dao.addRole(role1);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainSiteDisplay() {
        return "home";

    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    @ResponseBody
    public List<Publication> getPosts() {

        return dao.getAllPosts().stream().filter(p -> p.getPubStatus().equals(PubStatus.PUBLISH))
                .map(p -> pub.publicationBuilder(p))
                .collect(Collectors.toList());
//delivers all post-connected content. 
//may want streamlined methods where only want info
//from main post table
    }


    

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String cleanDB() {
        if (resetOn) {
            dao.cleanDB();
        }
        return "index";
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPost(@PathVariable("id") int id, @RequestBody Publication updateContent){
      
        pub.processNewPublication(id, updateContent);
        
    }
    
    @RequestMapping(value="/post/{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") int id){
        
        
        
                pub.removePostPageFromActive(id);
        
            }
    
    
    
    
    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Publication getSinglePost(@PathVariable("id") int id) {
//        List<Post> r = new ArrayList<>();
//        r.add(dao.getPostById(id));
//        return r;
    
        try {
            return pub.publicationBuilder(dao.getPostById(id));
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException();
        }
        //not sure if this is better solution versus two possible alternatives of 
        //setting the underlying program logic to return a "null" post or of 
        //allowing Tomcat to return the null pointer exc to go to the client 
        //(which has the advantage
        //of revealing a stack trace but seams less 'RESTful' and decoupled, esp
        //for a server-client interaction 'in the wild' 

        //so I'm inclined to think 404 probably is actually 'right' from client
        //contract perspective as most likely explanation of any bad query here
        //thoughts?
    }

    @RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Publication getSinglePageAjax(@PathVariable("id") int id) {


        try {
            return pub.publicationBuilder(dao.getStaticPageByPostId(id));
        } catch (NullPointerException e) {
            throw new ResourceNotFoundException();
        }
        //as above. 404 is likely the most appropriate communication to the client of
        //the underlying cause of any failure of this method that would throw a null pointer
        //on the publicationBuilder method call


    }

    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    @ResponseBody
    public List<Publication> getPages() {
        return dao.getAllStaticPages().stream().map(p -> pub.
                publicationBuilder(p))
                .collect(Collectors.toList());
        //definitely want to let Spring/Tomcat generate/handle exception in their default 
        //manner on these, as any failure would be indicative of very unexpected
        //bad behavior somewhere in the application (like a database server out of 
        //commission or something)
    }

    @RequestMapping(value = "/tag/{tag}", method = RequestMethod.GET)
    @ResponseBody
    public List<Publication> getPostsByTag(@PathVariable("tag") String tag) {

        Tag queryBy = dao.getTagByName(tag);

        return dao.getPostsByTag(queryBy.getTagId()).stream().map(p -> pub.
                publicationBuilder(p))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/posts/author", method = RequestMethod.GET)
    @ResponseBody
    public List<Publication> getAuthorPosts() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = dao.getUserByUsername(auth.getName()).get(0);
        int userId = user.getUserId();

        return dao.getDraftsByUserId(userId).stream().map(p -> pub.publicationBuilder(p))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/posts/admin", method = RequestMethod.GET)
    @ResponseBody
    public List<Publication> getPostsPendingForAdmin() {

        return dao.getAllPosts().stream()
                //.filter(p -> p.getPubStatus().equals(PubStatus.DRAFT))
                .map(p -> pub.publicationBuilder(p))
                .collect(Collectors.toList());
    }

}
