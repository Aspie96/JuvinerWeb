/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.User;
import com.mycompany.juvinerweb.db.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Aspie96
 */
@Controller
@RequestMapping("/user")
class NewClass3333 {
    @Autowired
    private UserDao userDao;

    @GetMapping
    public String home(Model model) {
        User user = this.userDao.findById(1).get().toUser(false, true);
        model.addAttribute("user", user);
        return "user_page";
    }
}