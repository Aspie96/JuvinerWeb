/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb;

import com.mycompany.juvinerweb.api.ApiAuthenticationEntryPoint;
import com.mycompany.juvinerweb.api.ApiLogin;
import com.juviner.data.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 *
 * @author Aspie96
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class WebSecurityConfig {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtFilter jwtFilter = new JwtFilter();
    
    @Configuration
    @Order(1)
    public class ApiConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/threads/**/posts").authenticated()
                .antMatchers("/api/threads").authenticated()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/**").permitAll()
                .and()
                .addFilterAt(new ApiLogin("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new ApiAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }
    
    @Configuration
    public class WebConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/new").authenticated()
                .antMatchers("/user/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterAt(new JwtLoginFilter("/login", authenticationManager()) {
                    @Override
                    public void respond(User user, String jwt, HttpServletRequest req, HttpServletResponse resp, Authentication authResult, AuthenticationException failed) throws IOException, ServletException {
                        if(user != null) {
                            resp.addCookie(new Cookie("auth", jwt));
                            resp.sendRedirect("/");
                        } else {
                            resp.sendRedirect("login?error");
                        }
                    }

                    @Override
                    public AccountCredentials getCredentials(HttpServletRequest req) throws IOException {
                        return new AccountCredentials(req.getParameter("username"), req.getParameter("password"), false);
                    }
                }, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")).accessDeniedPage("/login")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
}
