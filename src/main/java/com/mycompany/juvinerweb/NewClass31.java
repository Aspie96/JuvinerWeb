/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Thread;
import com.mycompany.juvinerweb.db.PostDao;
import com.mycompany.juvinerweb.db.PostE;
import com.mycompany.juvinerweb.db.ThreadDao;
import com.mycompany.juvinerweb.db.UserDao;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/thread/{thread_id}/posts")
class NewClass31 {
    @Autowired
    private PostDao postDao;
    @Autowired
    private ThreadDao threadDao;
    @Autowired
    private UserDao userDao;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postPost(@PathVariable int thread_id, @AuthenticationPrincipal LoggedUser user, @RequestParam Map<String, String> body) {
        String text = body.get("text");
        System.out.println(threadDao.findById(thread_id));
        System.out.println(userDao.findByUsername(user.getUsername()));
        PostE post = new PostE(threadDao.findById(thread_id).get(), userDao.findByUsername(user.getUsername()).get(), text);
        postDao.save(post);
        return "redirect:/thread/" + thread_id;
    }
}