/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.db;

import com.juviner.data.Category;
import com.juviner.data.Section;
import com.juviner.data.Thread;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Aspie96
 */
@Entity
@Table(name="categories")
public class CategoryE implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional=false)
    private SectionE section;
    @OneToMany(mappedBy="category")
    private List<ThreadE> threads;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String description;
    
    public CategoryE() { }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<ThreadE> getThreads() {
        return Collections.unmodifiableList(this.threads);
    }
    
    public Category toCategory(boolean withCDescription, boolean withAvatar, boolean withThreads, boolean withPosts, boolean withSection, boolean withEmail, boolean withDescription) {
        List<Thread> threads;
        if(withThreads) {
            threads = new ArrayList<>();
            this.threads.forEach(thread -> threads.add(thread.toThread(withPosts, withEmail, withDescription, withAvatar, false, false)));
        } else {
            threads = null;
        }
        Section section;
        if(withSection) {
            section = this.getSection().toSection(false, false, false, false, false, false);
        } else {
            section = null;
        }
        return new Category(this.id, this.getName(), this.description, threads, section);
    }
    
    public SectionE getSection() {
        return this.section;
    }
}
