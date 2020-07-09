/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.User;
import com.mycompany.juvinerweb.db.PostE;
import com.mycompany.juvinerweb.db.UserDao;
import com.mycompany.juvinerweb.db.UserE;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aspie96
 */
@Controller
@RequestMapping("/register")
class NewClass333311111111 {
    @Autowired
    private UserDao userDao;

    @GetMapping
    public String getRegister(Model model, CsrfToken csrfToken) {
        model.addAttribute("_csrf", csrfToken);
        return "register_page";
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postRegister(@RequestParam Map<String, String> body) {
        String password = body.get("password");
        String username = body.get("username");
        String email = body.get("email");
        String description = body.get("description");
        UserE user = new UserE(username, description, email, new BCryptPasswordEncoder().encode(password), null, null);
        userDao.save(user);
        return "redirect:/register_confirm";
    }
}