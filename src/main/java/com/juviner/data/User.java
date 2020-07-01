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
    private String username;
    private String email;
    private String description;
    private String avatar;
    
    public User() { }

    public User(String username, String email, String description, String avatar) {
        this.username = username;
        this.email = email;
        this.description = description;
        this.avatar = avatar;
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
    
    public String getAvatar() {
        return this.avatar;
    }
}
