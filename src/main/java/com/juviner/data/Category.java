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
    private final String name;
    private final List<Thread> threads;
    private final Section section;
    
    public Category(String name, Collection<Thread> threads, Section section) {
        this.name = name;
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
}
