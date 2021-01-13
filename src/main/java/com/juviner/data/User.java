package com.juviner.data;

public class User {
    private String username;
    private String email;
    private String description;
    private String avatar;
    private String github;
    
    public User() { }

    public User(String username, String email, String description, String avatar, String github) {
        this.username = username;
        this.email = email;
        this.description = description;
        this.avatar = avatar;
        this.github = github;
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
    
    public boolean hasGithub() {
        return this.github != null;
    }
    
    public String getGithub() {
        return this.github;
    }
}
