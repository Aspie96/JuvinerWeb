/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Thread {
    private int id;
    private final String title;
    private final Post firstPost;
    private final List<Post> posts;
    private final Category category;

    public Thread(int id, String title, Post firstPost, Collection<Post> posts, Category category) {
        this.title = title;
        this.firstPost = firstPost;
        if(posts == null) {
            this.posts = null;
        } else {
            this.posts = new ArrayList<>(posts);
        }
        this.category = category;
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean hasPosts() {
        return this.posts != null;
    }

    public List<Post> getPosts() {
        if(!this.hasPosts()) {
            return null;
        }
        return Collections.unmodifiableList(this.posts);
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public boolean hasCategory() {
        return this.category != null;
    }
    
    public boolean hasId() {
        return this.id != 0;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Post getFirstPost() {
        return this.firstPost;
    }
}
