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

/**
 *
 * @author Aspie96
 */
public class Category {
    private final int id;
    private final String name;
    private final String description;
    private final List<Thread> threads;
    private final Section section;
    
    public Category(int id, String name, String description, Collection<Thread> threads, Section section) {
        this.id = id;
        this.name = name;
        this.description = description;
        if(threads == null) {
            this.threads = null;
        } else {
            this.threads = new ArrayList<>(threads);
        }
        this.section = section;
    }
    
    public String getName() {
        return this.name;
    }

    public boolean hasThreads() {
        return this.threads != null;
    }

    public List<Thread> getThreads() {
        if(!this.hasThreads()) {
            return null;
        }
        return Collections.unmodifiableList(this.threads);
    }
    
    public boolean hasSection() {
        return this.section != null;
    }
    
    public Section getSection() {
        return this.section;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getDescription() {
        return this.description;
    }
}
