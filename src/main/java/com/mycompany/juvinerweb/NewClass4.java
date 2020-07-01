/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Root;
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
@RequestMapping("/new")
public class NewClass4 {
    @Autowired
    private ThreadDao threadDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private CategoryDao categoryDao;
    
    @GetMapping
    public String getThreadNew(@AuthenticationPrincipal LoggedUser user, CsrfToken csrfToken, Model model) {
        User session;
        if(user != null) {
            session = user.getUser();
        } else {
            session = null;
        }
        model.addAttribute("session", user);
        model.addAttribute("_csrf", csrfToken);
        List<Section> sections = new ArrayList<>();
        for(SectionE section : this.sectionDao.findAll()) {
            sections.add(section.toSection(true, false, false, false, false, false));
        }
        model.addAttribute("root", new Root("", sections));
        return "new_thread_page";
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postThreadNew(@AuthenticationPrincipal LoggedUser user, @RequestParam Map<String, String> body) {
        String title = body.get("title");
        String text = body.get("text");
        int category_id = Integer.parseInt(body.get("category_id"));
        ThreadE thread = threadDao.save(new ThreadE(title, categoryDao.findById(category_id).get()));
        int thread_id = thread.getId();
        PostE post = new PostE(thread, userDao.findByUsername(user.getUsername()).get(), text);
        postDao.save(post);
        return "redirect:/thread/" + thread_id;
    }
}
