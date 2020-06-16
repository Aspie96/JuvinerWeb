/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Thread;
import com.mycompany.juvinerweb.db.ThreadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thread")
class NewClass3 {
    @Autowired
    private ThreadDao threadDao;

    @GetMapping
    public String home(Model model) {
        Thread thread = this.threadDao.findById(1).get().toThread(true, false, false, true, true);
        model.addAttribute("thread", thread);
        return "thread_page";
    }
}