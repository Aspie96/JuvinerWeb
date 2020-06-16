/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.data;

/**
 *
 * @author Aspie96
 */
public class User {
    private final String username;
    private final String email;
    private final String description;

    public User(String username, String email, String description) {
        this.username = username;
        this.email = email;
        this.description = description;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public boolean hasEmail() {
        return this.email != null;
    }
    
    public boolean hasDescription() {
        return this.description != null;
    }
}
