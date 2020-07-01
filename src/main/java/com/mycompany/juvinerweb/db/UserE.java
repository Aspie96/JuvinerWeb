/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.db;

import com.juviner.data.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Aspie96
 */
@Entity
@Table(name="users")
public class UserE implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false)
    private String username;
    @Column(nullable=false)
    private String description;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String password;
    @Column(nullable=true)
    private String avatar;
    
    public UserE() { }
    
    public UserE(int id, String username, String description, String email, String password, String avatar) {
        this.id = id;
        this.description = description; this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }
    
    public UserE(String username, String description, String email, String password, String avatar) {
        this.description = description; this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }
    
    public int getId() {
        return this.id;
    }
    
    public User toUser(boolean withEmail, boolean withDescription, boolean withAvatar) {
        String email;
        String description;
        String avatar;
        if(withEmail) {
            email = this.email;
        } else {
            email = null;
        }
        if(withDescription) {
            description = this.description;
        } else {
            description = null;
        }
        if(withAvatar) {
            avatar = this.avatar;
        } else {
            avatar = null;
        }
        return new User(this.username, email, description, avatar);
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getAvatar() {
        return this.avatar;
    }
}
