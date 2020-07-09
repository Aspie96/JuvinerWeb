/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.api;

import com.juviner.data.Category;
import com.juviner.data.Root;
import com.juviner.data.Section;
import com.juviner.data.Thread;
import com.juviner.data.User;
import com.mycompany.juvinerweb.LoggedUser;
import com.mycompany.juvinerweb.db.CategoryDao;
import com.mycompany.juvinerweb.db.CategoryE;
import com.mycompany.juvinerweb.db.PostDao;
import com.mycompany.juvinerweb.db.PostE;
import com.mycompany.juvinerweb.db.SectionDao;
import com.mycompany.juvinerweb.db.ThreadDao;
import com.mycompany.juvinerweb.db.ThreadE;
import com.mycompany.juvinerweb.db.UserDao;
import com.mycompany.juvinerweb.db.UserE;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Api {
    @Autowired
    private ThreadDao threadDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private SectionDao sectionDao;

    @GetMapping("/threads/{thread_id}")
    public Object getThread(@PathVariable int thread_id) {
        Optional<ThreadE> thread = this.threadDao.findById(thread_id);
        if(thread.isPresent()) {
            return new ApiSuccessResponse("thread", thread.get().toThread(true, false, false, true, true, true, true));
        } else {
            return new ResponseEntity(new ApiFailureResponse("Thread not found"), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/users/{username}")
    public Object getUser(@PathVariable String username) {
        Optional<UserE> user = this.userDao.findByUsername(username);
        if(!user.isPresent()) {
            return new ResponseEntity(new ApiFailureResponse("Thread not found"), HttpStatus.NOT_FOUND);
        }
        return new ApiSuccessResponse("user", user.get().toUser(false, true, true, true));
    }
    
    @GetMapping("/categories/{id}")
    public Object getSection(@PathVariable int id) {
        Optional<CategoryE> category = this.categoryDao.findById(id);
        if(!category.isPresent()) {
            return new ResponseEntity(new ApiFailureResponse("Thread not found"), HttpStatus.NOT_FOUND);
        }
        return new ApiSuccessResponse("category", category.get().toCategory(true, false, false, false, false, true, false, false));
    }
    
    @GetMapping("/root")
    public Object getRoot() {
        List<Section> sections = new ArrayList<Section>();
        sectionDao.findAll().forEach(section -> sections.add(section.toSection(true, false, false, false, false, false, false)));
        Root root = new Root("Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullamco laboriosam, nisi ut aliquid ex ea commodi consequatur. Duis aute irure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", sections);
        return new ApiSuccessResponse("root", root);
    }
    
    @PostMapping(path="/threads/{thread_id}/posts", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Object postPost(@PathVariable int thread_id, @AuthenticationPrincipal LoggedUser user, @RequestBody LinkedHashMap body) {
        if(!body.containsKey("text")) {
            return new ResponseEntity(new ApiFailureResponse("text parameter missing"), HttpStatus.BAD_REQUEST);
        }
        String text = (String)body.get("text");
        System.out.println(threadDao.findById(thread_id));
        System.out.println(userDao.findByUsername(user.getUsername()));
        Optional<ThreadE> thread = threadDao.findById(thread_id);
        if(!thread.isPresent()) {
            return new ResponseEntity(new ApiFailureResponse("Thread not found"), HttpStatus.NOT_FOUND);
        }
        PostE post = new PostE(threadDao.findById(thread_id).get(), userDao.findByUsername(user.getUsername()).get(), text);
        return new ApiSuccessResponse("post", postDao.save(post).toPost(false, false, false, false, false, false, false));
    }
    
    @PostMapping(path="/threads", consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Object postThread(@AuthenticationPrincipal LoggedUser user, @RequestBody LinkedHashMap body) {
        if(!body.containsKey("text")) {
            return new ResponseEntity(new ApiFailureResponse("text parameter missing"), HttpStatus.BAD_REQUEST);
        }
        String text = (String)body.get("text");
        if(!body.containsKey("category_id")) {
            return new ResponseEntity(new ApiFailureResponse("category_id parameter is missing"), HttpStatus.BAD_REQUEST);
        }
        if(body.get("category_id").getClass() != Integer.class) {
            return new ResponseEntity(new ApiFailureResponse("category_id must be integer"), HttpStatus.BAD_REQUEST);
        }
        int category_id = (int)body.get("category_id");
        System.out.println(categoryDao.findById(category_id));
        System.out.println(userDao.findByUsername(user.getUsername()));
        String title = (String)body.get("title");
        Optional<CategoryE> category = categoryDao.findById(category_id);
        if(!category.isPresent()) {
            return new ResponseEntity(new ApiFailureResponse("category not found"), HttpStatus.NOT_FOUND);
        }
        ThreadE thread = threadDao.save(new ThreadE(title, category.get()));
        int thread_id = thread.getId();
        PostE post = new PostE(thread, userDao.findByUsername(user.getUsername()).get(), text);
        postDao.save(post);
        return new ApiSuccessResponse("thread", new Thread(thread_id, title, post.toPost(false, false, false, false, false, false, false), null, category.get().toCategory(false, false, false, false, false, false, false, false)));
    }
    
    @GetMapping("/user/details")
    public Object getDetails(@AuthenticationPrincipal LoggedUser user) {
        User u = this.userDao.findByUsername(user.getUsername()).get().toUser(true, true, true, true);
        return new ApiSuccessResponse("user", u);
    }
    
    @PostMapping("/user/details")
    public Object postDetails(@AuthenticationPrincipal LoggedUser user, @RequestBody LinkedHashMap body) {
        UserE old = this.userDao.findByUsername(user.getUsername()).get();
        UserE newU = new UserE(old.getId(), old.getUsername(), (String)body.get("description"), old.getEmail(), old.getPassword(), old.getAvatar(), old.getGithub());
        User u = this.userDao.save(newU).toUser(true, true, true, true);
        return new ApiSuccessResponse("user", u);
    }
}
