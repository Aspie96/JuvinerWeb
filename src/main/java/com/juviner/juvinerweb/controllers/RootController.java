package com.juviner.juvinerweb.controllers;

import com.juviner.data.Root;
import com.juviner.data.Section;
import com.juviner.data.User;
import com.juviner.juvinerweb.LoggedUser;
import com.juviner.juvinerweb.db.SectionDao;
import com.juviner.juvinerweb.db.SectionE;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/root")
class RootController {
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