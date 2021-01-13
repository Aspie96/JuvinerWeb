/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juviner.data.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        String jwtToken = null;
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for(Cookie cookie : req.getCookies()) {
                if(cookie.getName().equals("auth")) {
                    jwtToken = cookie.getValue();
                }
            }
        }
        if(jwtToken == null) {
            String authentication = req.getHeader("Authentication");
            if(authentication != null) {
                jwtToken = authentication.substring("token ".length());
            }
        }
        if(jwtToken != null) {
            ObjectMapper objectMapper = new ObjectMapper();
        Claims claims = Jwts.parserBuilder().deserializeJsonWith(new JacksonDeserializer(objectMapper)).setSigningKey("sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123").build().parseClaimsJws(jwtToken.replace("Bearer",""))
                .getBody();
            User user = objectMapper.convertValue(claims.get("user"), User.class);
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String)claims.get("authorities"));
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(new LoggedUser(user, null, authorities), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        try {
            filterChain.doFilter(req, servletResponse);
        } catch (ServletException ex) {
            Logger.getLogger(JwtFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
