/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juviner.data.User;
import com.mycompany.juvinerweb.AccountCredentials;
import com.mycompany.juvinerweb.JwtLoginFilter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author Aspie96
 */
public class ApiLogin extends JwtLoginFilter {
    public ApiLogin(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super("/api" + defaultFilterProcessesUrl, authenticationManager);
    }

    @Override
    public void respond(User user, String jwt, HttpServletRequest req, HttpServletResponse resp, Authentication authResult, AuthenticationException failed) throws IOException, ServletException {
        if(user != null) {
            if(req.getAttribute("req_header") != null) {
                resp.addHeader("Authentication", "token " + jwt);
            } else {
                Cookie cookie = new Cookie("auth", jwt);
                cookie.setPath("/");
                resp.addCookie(cookie);
            }
            resp.setContentType("application/json;charset=utf-8");
            try(PrintWriter out = resp.getWriter()) {
                out.write(new ObjectMapper().writeValueAsString(new ApiSuccessResponse("user", user)));
                out.flush();
            }
        } else {
            try(PrintWriter out = resp.getWriter()) {
                out.write(new ObjectMapper().writeValueAsString(new ApiFailureResponse("Username or password wrong.")));
                out.flush();
            }
        }
    }

    @Override
    public AccountCredentials getCredentials(HttpServletRequest req) throws IOException {
        JsonNode element = new ObjectMapper().readTree(req.getInputStream());
        if(element.has("header")) {
            req.setAttribute("req_header", true);
        }
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).treeToValue(element, AccountCredentials.class);
    }
}
