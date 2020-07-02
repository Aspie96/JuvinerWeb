/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.User;
import com.mycompany.juvinerweb.db.PostE;
import com.mycompany.juvinerweb.db.UserDao;
import com.mycompany.juvinerweb.db.UserE;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Aspie96
 */
@Controller
class NewClass33331111111111 {
    @GetMapping("/avatars/{username}/{image}")
    public @ResponseBody byte[] getImage(@PathVariable String username) throws IOException {
        System.out.println(getClass().getResourceAsStream("D:\\Downloads\\avatars\\" + username));
        //return new FileInputStream("D:\\Downloads\\avatars\\" + username).readAllBytes();
        return null;
    }
    
    @GetMapping("/default_avatar")
    public @ResponseBody byte[] getImage() throws IOException {
        //return new FileInputStream("D:\\Downloads\\avatars\\default.jpg").readAllBytes();
        return null;
    }
}