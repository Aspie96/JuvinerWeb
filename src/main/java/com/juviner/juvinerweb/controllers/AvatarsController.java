package com.juviner.juvinerweb.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class AvatarsController {
    @GetMapping("/avatars/{username}/{image}")
    public @ResponseBody byte[] getImage(@PathVariable String username) throws IOException {
        System.out.println(getClass().getResourceAsStream("D:\\Downloads\\avatars\\" + username));
        return IOUtils.toByteArray(new ClassPathResource("./avatars/" + username).getInputStream());
    }
    
    @GetMapping("/default_avatar")
    public @ResponseBody byte[] getImage() throws IOException {
        return IOUtils.toByteArray(new ClassPathResource("./avatars/default.jpg").getInputStream());
    }
}