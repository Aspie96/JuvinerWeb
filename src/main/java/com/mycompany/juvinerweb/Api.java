/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Category;
import com.juviner.data.Thread;
import com.juviner.data.User;
import com.mycompany.juvinerweb.db.CategoryDao;
import com.mycompany.juvinerweb.db.ThreadDao;
import com.mycompany.juvinerweb.db.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/thread")
    public ApiResponse getThread() {
        Thread thread = this.threadDao.findById(1).get().toThread(true, false, false, true, true);
        return new ApiSuccessResponse("thread", thread);
    }
    
    @GetMapping("/user")
    public ApiResponse getUser() {
        User user = this.userDao.findById(1).get().toUser(false, true);
        return new ApiSuccessResponse("user", user);
    }
    
    @GetMapping("/category")
    public ApiResponse getSection() {
        Category category = this.categoryDao.findById(1).get().toCategory(true, false, true, false, false);
        return new ApiSuccessResponse("category", category);
    }
}
