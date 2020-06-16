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
    private final String title;
    private final List<Post> posts;
    private final Category category;

    public Thread(String title, Collection<Post> posts, Category category) {
        this.title = title;
        if(posts == null) {
            this.posts = null;
        } else {
            this.posts = new ArrayList<>(posts);
        }
        this.category = category;
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
}
