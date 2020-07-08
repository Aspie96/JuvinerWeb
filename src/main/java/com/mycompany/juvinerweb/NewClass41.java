/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Section;
import com.juviner.data.User;
import com.mycompany.juvinerweb.db.CategoryDao;
import com.mycompany.juvinerweb.db.PostDao;
import com.mycompany.juvinerweb.db.PostE;
import com.mycompany.juvinerweb.db.SectionDao;
import com.mycompany.juvinerweb.db.SectionE;
import com.mycompany.juvinerweb.db.ThreadDao;
import com.mycompany.juvinerweb.db.ThreadE;
import com.mycompany.juvinerweb.db.UserDao;
import com.mycompany.juvinerweb.db.UserE;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class NewClass41 {
    @Autowired
    private UserDao userDao;
    
    @GetMapping
    public String getThreadNew(@AuthenticationPrincipal LoggedUser user, CsrfToken csrfToken, Model model) {
        model.addAttribute("_csrf", csrfToken);
        User us = userDao.findByUsername(user.getUsername()).get().toUser(true, true, true, true);
        model.addAttribute("user", us);
        model.addAttribute("session", us);
        System.out.println(us.getEmail());
        return "user_details_page";
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postThreadNew(@AuthenticationPrincipal LoggedUser user, @RequestParam Map<String, String> body) {
        String description = body.get("description");
        UserE old = userDao.findByUsername(user.getUsername()).get();
        UserE newU = new UserE(old.getId(), old.getUsername(), description, old.getEmail(), old.getPassword(), old.getAvatar(), old.getGithub());
        userDao.save(newU);
        return "redirect:/user";
    }
}
