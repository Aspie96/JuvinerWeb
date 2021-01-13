/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerweb.controllers;

import com.juviner.data.User;
import com.juviner.juvinerweb.LoggedUser;
import com.juviner.juvinerweb.db.UserDao;
import com.juviner.juvinerweb.db.UserE;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;
    
    @GetMapping
    public String getUser(@AuthenticationPrincipal LoggedUser user, CsrfToken csrfToken, Model model) {
        model.addAttribute("_csrf", csrfToken);
        User us = userDao.findByUsername(user.getUsername()).get().toUser(true, true, true, true);
        model.addAttribute("user", us);
        model.addAttribute("session", us);
        System.out.println(us.getEmail());
        return "user_details_page";
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postUser(@AuthenticationPrincipal LoggedUser user, @RequestParam Map<String, String> body) {
        String description = body.get("description");
        UserE old = userDao.findByUsername(user.getUsername()).get();
        UserE newU = new UserE(old.getId(), old.getUsername(), description, old.getEmail(), old.getPassword(), old.getAvatar(), old.getGithub());
        userDao.save(newU);
        return "redirect:/user";
    }
    
    @GetMapping("/{username}")
    public String getUser(@AuthenticationPrincipal LoggedUser session, @PathVariable String username, Model model) {
        User current;
        if(session == null) {
            current = null;
        } else {
            current = session.getUser();
        }
        model.addAttribute("session", current);
        Optional<UserE> user = this.userDao.findByUsername(username);
        if(user.isPresent()) {
            model.addAttribute("user", user.get().toUser(false, true, true, true));
            return "user_page";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); 
        }
    }
}
