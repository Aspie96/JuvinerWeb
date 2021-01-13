/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerweb.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Aspie96
 */
@Controller
@RequestMapping("/login")
class LoginController {
    @GetMapping
    public String getLogin(CsrfToken csrfToken, Model model) {
        model.addAttribute("_csrf", csrfToken);
        return "login_page";
    }
}