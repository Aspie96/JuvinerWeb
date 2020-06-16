/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.data;

public class Post {
    private final User user;
    private final String text;
    private final Thread thread;
    
    public Post(User user, String text, Thread thread) {
        this.user = user;
        this.text = text;
        this.thread = thread;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public boolean hasText() {
        return this.text != null;
    }
    
    public String getText() {
        return this.text;
    }
    
    public Thread getThread() {
        return this.thread;
    }
    
    public boolean hasThread() {
        return this.thread != null;
    }
}
