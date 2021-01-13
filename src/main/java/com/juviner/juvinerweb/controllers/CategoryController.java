/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerweb.controllers;

import com.juviner.data.Category;
import com.juviner.data.User;
import com.juviner.juvinerweb.LoggedUser;
import com.juviner.juvinerweb.db.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category/{id}")
class CategoryController {
    @Autowired
    private CategoryDao categoryDao;

    @GetMapping
    public String getCategory(@AuthenticationPrincipal LoggedUser user, @PathVariable int id, Model model) {
        User session;
        if(user == null) {
            session = null;
        } else {
            session = user.getUser();
        }
        model.addAttribute("session", session);
        Category category = this.categoryDao.findById(id).get().toCategory(true, false, false, true, false, true, false, false);
        model.addAttribute("category", category);
        return "category_page";
    }
}