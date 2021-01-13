package com.juviner.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private final LocalDateTime time;
    private final User user;
    private final String text;
    private final Thread thread;
    
    public Post(LocalDateTime time, User user, String text, Thread thread) {
        this.time = time;
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
    
    public LocalDateTime getTime() {
        return this.time;
    }
    
    public String getFormattedTime() {
        return this.time.format(DateTimeFormatter.ISO_DATE);
    }
}
