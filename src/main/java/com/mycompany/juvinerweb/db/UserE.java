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
    
    public UserE() { }
    
    public int getId() {
        return this.id;
    }
    
    public User toUser(boolean withEmail, boolean withDescription) {
        String email;
        String description;
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
        return new User(this.username, email, description);
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
}
