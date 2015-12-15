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
import com.thesoftwareguild.lawinorder.exception.ResourceNotFoundException;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author calarrick
 */
@Controller
@RequestMapping({"/"})
public class SinglePostPageController {

    private final LawInOrderDao dao;
    private final PostPublisher pub;

    @Inject
    public SinglePostPageController(LawInOrderDao dao, PostPublisher publisher) {
        this.dao = dao;
        this.pub = publisher;
    }

    @RequestMapping(value = "/{postUrl}", method = RequestMethod.GET)
    public ModelAndView viewSingle(@PathVariable("postUrl") String postUrl)
            throws ResourceNotFoundException {
        {

            Publication post;

            try {
                post = pub.publicationBuilder(dao.getStaticPageByUrl(postUrl));

            } catch (NullPointerException e) {

                try {
                    post = pub.publicationBuilder(dao.getPostByUrl(postUrl));

                } catch (NullPointerException ex) {

                    throw new ResourceNotFoundException(postUrl);

                }

            }

            if (post.getPubStatus().equals(PubStatus.PUBLISH)) {

                return new ModelAndView("single", "post", post);
            } else {
                throw new ResourceNotFoundException(postUrl);
            }
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleResourceNotFoundException(
            ResourceNotFoundException ex
    ) {

        String message = ex.getMessage();

        return new ModelAndView("resourcenotfound", "message", ex);

    }

}
