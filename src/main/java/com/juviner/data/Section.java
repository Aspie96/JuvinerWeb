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

public class Section {
    private final String name;
    private final List<Category> categories;

    public Section(String name, Collection<Category> categories) {
        this.name = name;
        if(categories == null) {
            this.categories = null;
        } else {
            this.categories = new ArrayList<>(categories);
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean hasCategories() {
        return this.categories != null;
    }
    
    public List<Category> getCategories() {
        if(!this.hasCategories()) {
            return null;
        }
        return Collections.unmodifiableList(this.categories);
    }
}
