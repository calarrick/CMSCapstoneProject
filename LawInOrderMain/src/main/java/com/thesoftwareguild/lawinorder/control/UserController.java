/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesoftwareguild.lawinorder.control;

import com.thesoftwareguild.lawinorder.dao.LawInOrderDao;
import com.thesoftwareguild.lawinorder.dao.PostPublisher;
import com.thesoftwareguild.lawinorder.dto.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author calarrick
 */
@Controller
public class UserController {

    private final LawInOrderDao dao;
    private final PostPublisher pub;

    @Inject
    public UserController(LawInOrderDao dao, PostPublisher publisher) {
        this.dao = dao;
        this.pub = publisher;
    }
    

    @RequestMapping(value = "admin/user", method = RequestMethod.POST)
    @ResponseBody
    public User addUser(@RequestBody User user) {
            dao.addUser(user);
            return user;

        }


    

    @RequestMapping(value = "/admin/userlist", method = RequestMethod.GET)
    @ResponseBody
    //public Map<String, String> getUsers() {
    public List<String> getUsers() {

        Map<Integer, String> userMap = new HashMap<>();
        //Map<Integer, String> roleMap = new HashMap<>();

        dao.getAllUsers().stream().forEach(u -> {
            int uid = u.getUserId();
            String uname = u.getUsername();
            userMap.put(uid, uname);
        });
//        dao.getAllRoles().stream().forEach(r -> {
//            int rid = r.getRoleId();
//            String rname = r.getRoleName();
//            roleMap.put(rid, rname);
//        });

//        Map<String, String> userLister = new HashMap<>();
        
        List<String> userNameList = new ArrayList<>();
        

//        userMap.entrySet().stream().
//                forEach((userMatch) -> {
//                    String userName = userMatch.getValue();
//                    String roleName = roleMap.get(userMatch.getKey());
//                    userLister.put(userName, roleName);
//                });

//        return userLister;
        
        userNameList = dao.getAllUsers().stream().map(u -> u.getUsername())
                .collect(Collectors.toList());
        

        return userNameList;
        
    }
    
    @RequestMapping(value="/admin/singleuser")
    
    @ResponseBody
    public List<String> getSingleUser(){
        
        List<String> toReturn = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        toReturn.add(auth.getName());
        
        return toReturn;
    }
    
    

}
