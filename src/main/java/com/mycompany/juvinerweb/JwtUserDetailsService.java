/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.juviner.data.User;
import com.mycompany.juvinerweb.db.UserDao;
import com.mycompany.juvinerweb.db.UserE;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    
    @Override
    public LoggedUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserE> user = userDao.findByUsername(username);
        System.out.println(username);
        if(user.isPresent()) {
            List<GrantedAuthority> auths = new ArrayList<>();
            auths.add(new SimpleGrantedAuthority("user"));
            return new LoggedUser(user.get().toUser(true, true, true, true), user.get().getPassword(), auths);
        }
        throw new UsernameNotFoundException("User " + user + " not found.");
    }
}
