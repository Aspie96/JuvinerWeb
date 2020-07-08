/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Thread;
import com.mycompany.juvinerweb.db.ThreadDao;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thread/{thread_id}")
class NewClass3 {
    @Autowired
    private ThreadDao threadDao;

    @GetMapping
    public String home(@PathVariable int thread_id, CsrfToken csrfToken, Authentication authentication, Model model) {
        LoggedUser user;
        if(authentication != null) {
            user = (LoggedUser)authentication.getPrincipal();
        } else {
            user = null;
        }
        Thread thread = this.threadDao.findById(thread_id).get().toThread(true, false, false, true, true, true, true);
        model.addAttribute("thread", thread);
        model.addAttribute("session", user);
        model.addAttribute("_csrf", csrfToken);
        System.out.println(user);
        return "thread_page";
    }
}