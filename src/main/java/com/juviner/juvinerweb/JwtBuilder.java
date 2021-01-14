package com.juviner.juvinerweb;

import com.juviner.data.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;

public class JwtBuilder {
    public String buildJwt(User user, Collection<? extends GrantedAuthority> authorities) {
        StringBuffer as = new StringBuffer();
        authorities.forEach(authority -> as.append(authority.getAuthority()).append(","));
        return Jwts.builder()
            .claim("authorities", as)//Configure user roles
            .setSubject(user.getUsername())
            .claim("user", user)
            .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000))
            .signWith(SignatureAlgorithm.HS512, "sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123sang123")
            .compact();
    }
}
