/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerweb;

import com.juviner.data.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

public class JwtBuilder {
    public String buildJwt(User user, Collection<? extends GrantedAuthority> authorities) {
        StringBuffer as = new StringBuffer();
        authorities.forEach(authority -> as.append(authority.getAuthority()).append(","));
        return Jwts.builder()
            .claim("authorities", as)//Configure user roles
            .setSubject(user.getUsername())
            .claim("user", user)
            .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, "sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123")
            .compact();
    }
}
