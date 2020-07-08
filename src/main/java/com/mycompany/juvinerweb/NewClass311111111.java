/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Root;
import com.juviner.data.Section;
import com.juviner.data.Thread;
import com.juviner.data.User;
import com.mycompany.juvinerweb.db.SectionDao;
import com.mycompany.juvinerweb.db.SectionE;
import com.mycompany.juvinerweb.db.ThreadDao;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping("/root")
class NewClass311111111 {
    @Autowired
    private SectionDao sectionDao;

    @GetMapping
    public String home(@AuthenticationPrincipal LoggedUser user, Model model) {
        User session;
        if(user != null) {
            session = user.getUser();
        } else {
            session = null;
        }
        model.addAttribute("session", session);
        List<Section> sections = new ArrayList<>();
        for(SectionE section : this.sectionDao.findAll()) {
            sections.add(section.toSection(true, false, false, false, false, false, false));
        }
        model.addAttribute("root", new Root("Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", sections));
        return "home_page";
    }
}