package com.juviner.juvinerweb.controllers;

import com.juviner.juvinerweb.LoggedUser;
import com.juviner.juvinerweb.db.PostDao;
import com.juviner.juvinerweb.db.PostE;
import com.juviner.juvinerweb.db.ThreadDao;
import com.juviner.juvinerweb.db.UserDao;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/thread/{thread_id}/posts")
class PostsController {
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