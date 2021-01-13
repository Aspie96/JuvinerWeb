package com.juviner.juvinerweb.controllers;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class AvatarsController {
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