/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Category;
import com.mycompany.juvinerweb.db.CategoryDao;
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
@RequestMapping("/category")
class NewClass33331 {
    @Autowired
    private CategoryDao categoryDao;

    @GetMapping
    public String home(Model model) {
        Category category = this.categoryDao.findById(1).get().toCategory(true, false, true, false, false);
        model.addAttribute("category", category);
        return "category_page";
    }
}