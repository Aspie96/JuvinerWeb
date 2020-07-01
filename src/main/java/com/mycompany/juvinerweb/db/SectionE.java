/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.db;

import com.juviner.data.Category;
import com.juviner.data.Section;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Aspie96
 */
@Entity
@Table(name="section")
public class SectionE implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false)
    private String name;
    @OneToMany(mappedBy="section")
    private List<CategoryE> categories;

    public SectionE() { }
    
    public int getId() {
        return this.id;
    }
    
    public List<CategoryE> getCategories() {
        return Collections.unmodifiableList(this.categories);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Section toSection(boolean withCategories, boolean withThreads, boolean withPosts, boolean withEmail, boolean withDescription, boolean withAvatar) {
        List<Category> categories;
        if(withCategories) {
            categories = new ArrayList<>();
            this.categories.forEach(category -> categories.add(category.toCategory(false, withThreads, withPosts, false, withEmail, withDescription, withAvatar)));
        } else {
            categories = null;
        }
        return new Section(this.getName(), categories);
    }
}
