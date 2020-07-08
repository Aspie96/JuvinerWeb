/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.User;
import com.mycompany.juvinerweb.db.UserDao;
import com.mycompany.juvinerweb.db.UserE;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Aspie96
 */
@Controller
@RequestMapping("/avatar")
class NewClass333311111 {
    @Autowired
    private UserDao userDao;
    
    @GetMapping
    public String getAvatar(@AuthenticationPrincipal LoggedUser user, CsrfToken csrfToken, Model model) {
        User session = user.getUser();
        model.addAttribute("_csrf", csrfToken);
        model.addAttribute("session", session);
        return "avatar_page";
    }
    
    @PostMapping
    public String postAvatar(HttpServletResponse response, @AuthenticationPrincipal LoggedUser user, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Files.copy(file.getInputStream(), Paths.get("D:\\Downloads\\avatars\\" + user.getUsername()), StandardCopyOption.REPLACE_EXISTING);
        UserE u = userDao.findByUsername(user.getUsername()).get();
        UserE newU = new UserE(u.getId(), u.getUsername(), u.getDescription(), u.getEmail(), u.getPassword(), file.getOriginalFilename(), u.getGithub());
        userDao.save(newU);
        String jwt = new JwtBuilder().buildJwt(newU.toUser(true, true, true, true), user.getAuthorities());
        System.out.println(newU.toUser(true, true, true, true).getAvatar());
        response.addCookie(new Cookie("auth", jwt));
        User session = user.getUser();
        model.addAttribute("session", session);
        model.addAttribute("success", true);
        return "avatar_page";
    }
}