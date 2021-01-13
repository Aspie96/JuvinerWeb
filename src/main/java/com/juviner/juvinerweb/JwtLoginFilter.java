/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juviner.data.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public abstract class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {
    private JwtBuilder jwtBuilder;
    
    public JwtLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        jwtBuilder = new JwtBuilder();
        setAuthenticationManager(authenticationManager);
    }
    
    public abstract AccountCredentials getCredentials(HttpServletRequest req) throws IOException;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException, IOException, ServletException {
        AccountCredentials credentials = getCredentials(req);
        String user = credentials.getUsername();
        String password = credentials.getPassword();
        System.out.println(user);
        System.out.println(password);
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user, password));
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        LoggedUser details = (LoggedUser)authResult.getPrincipal();
        String jwt = jwtBuilder.buildJwt(details.getUser(), authorities);
        this.respond(details.getUser(), jwt, req, resp, authResult, null);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException failed) throws IOException, ServletException {
        this.respond(null, null, req, resp, null, failed);
    }
    
    public abstract void respond(User user, String jwt, HttpServletRequest req, HttpServletResponse resp, Authentication authResult, AuthenticationException failed) throws IOException, ServletException;
}
