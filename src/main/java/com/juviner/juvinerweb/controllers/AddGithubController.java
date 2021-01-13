/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerweb.controllers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.juviner.juvinerweb.LoggedUser;
import com.juviner.juvinerweb.db.UserDao;
import com.juviner.juvinerweb.db.UserE;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/add_github")
class AddGithubController {
    @Autowired
    private UserDao userDao;
    private Map<String, String> env = System.getenv();
    
    @GetMapping
    public String getAddGithub(Model model, CsrfToken csrfToken, @RequestParam String code) {
        model.addAttribute("_csrf", csrfToken);
        model.addAttribute("code", code);
        return "add_github_page";
    }
    
    @PostMapping
    public String postAddGithub(@RequestParam String code, @AuthenticationPrincipal LoggedUser lUser) throws IOException {
        String secret = env.get("GITHUB_SECRET");
        URL url = new URL("https://github.com/login/oauth/access_token");
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper request = new ObjectMapper();
        ObjectNode user = request.createObjectNode();
        user.put("code", code);
        user.put("client_id", "Iv1.f1dd9ba30d3451a9");
        user.put("client_secret", secret);
        user.put("accept", "json");
        String json = request.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptableMediaTypes = new ArrayList();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> loginResponse = restTemplate.exchange(url.toString(), HttpMethod.POST, entity, String.class);
        String answer = loginResponse.getBody();
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode rootNode = mapper.readTree(answer);
        String accessToken = rootNode.get("access_token").asText();
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        url = new URL("https://api.github.com/user");
        headers.set("Authorization", "token " + accessToken);
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<>(headers);
        ResponseEntity<String> dataResponse = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, String.class);
        answer = dataResponse.getBody();
        factory = new JsonFactory();
        mapper = new ObjectMapper(factory);
        rootNode = mapper.readTree(answer);
        String username = rootNode.get("login").asText();
        UserE usere = userDao.findByUsername(lUser.getUsername()).get();
        UserE newUserE = new UserE(usere.getId(), usere.getUsername(), usere.getDescription(), usere.getEmail(), usere.getPassword(), usere.getAvatar(), username);
        userDao.save(newUserE);
        System.out.println(username);
        return "redirect:/user";
    }
}