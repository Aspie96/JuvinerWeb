/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.Thread;
import com.mycompany.juvinerweb.db.ThreadDao;
import com.mycompany.juvinerweb.db.ThreadE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Aspie96
 */
@Controller
@RequestMapping("/new_thread")
class NewClass333311 {
    @Autowired
    private ThreadDao threadDao;

    @GetMapping
    public String getNewThread() {
        return "new_thread";
    }
}