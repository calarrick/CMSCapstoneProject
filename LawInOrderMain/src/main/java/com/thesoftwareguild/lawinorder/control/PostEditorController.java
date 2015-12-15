package com.thesoftwareguild.lawinorder.control;

import com.thesoftwareguild.lawinorder.dao.LawInOrderDao;
import com.thesoftwareguild.lawinorder.dao.PostPublisher;
import com.thesoftwareguild.lawinorder.dto.Post;
import com.thesoftwareguild.lawinorder.dto.Publication;
import com.thesoftwareguild.lawinorder.dto.Tag;
import java.util.List;
import javax.inject.Inject;
import org.jsoup.Jsoup;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping({"/"})
public class PostEditorController {

    private final LawInOrderDao dao;
    private final PostPublisher pub;

    @Inject
    public PostEditorController(LawInOrderDao dao, PostPublisher publisher) {
        this.dao = dao;
        this.pub = publisher;
    }

    
    //Returns admin page
    @RequestMapping(value = {"/control"}, method = RequestMethod.GET)
    public String adminHome(Model model) {

        return "control";
    }


    
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Publication addPublication(@RequestBody Publication newContent) {

        
        newContent.setPostBody(Jsoup.parse(newContent.getPostBody()).toString());

        
        //postPublisher will take in the 'full' object from a new publication and
        //process to dtos matched up against relational tables, and 
        //have dao add them to the database
        //will also 'rebuild' new publication from those new components (w/ any 
        //regularization that brings)

        return pub.processNewPublication(newContent);//returns 'processed' version of publication
 

    }
    
    
    @RequestMapping(value= "/toptags", method=RequestMethod.GET)
    @ResponseBody public List<Tag> getTop5Tags(){
        List<Tag> toTruncate = dao.getSortedTags();
        int upperIndex = Math.min(5, toTruncate.size());
        return toTruncate.subList(0, upperIndex);
    }
//    
//    @RequestMapping(value="/toptags", method=RequestMethod.GET)
//    @ResponseBody public List<Tag> getTop5Tags(){
//        List<Tag> toTruncate = dao.getSortedTags();
//        int upperIndex = Math.min(5, toTruncate.size());
//        return toTruncate.subList(0, upperIndex)
//    }


    
    
}
