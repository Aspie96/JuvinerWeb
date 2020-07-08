/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.User;
import com.mycompany.juvinerweb.db.UserDao;
import com.mycompany.juvinerweb.db.UserE;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aspie96
 */
@Controller
@RequestMapping("/user/{username}")
class NewClass3333 {
    @Autowired
    private UserDao userDao;

    @GetMapping
    public String home(@AuthenticationPrincipal LoggedUser session, @PathVariable String username, Model model) {
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