/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.db;

import com.juviner.data.Post;
import com.juviner.data.Thread;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="posts")
public class PostE implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional=false)
    private ThreadE thread;
    @ManyToOne(optional=false)
    private UserE user;
    @Column(nullable=false)
    private String text;

    public PostE() { }
    
    public int getId() {
        return this.id;
    }

    public UserE getUser() {
        return this.user;
    }

    public String getText() {
        return this.text;
    }

    public ThreadE getThread() {
        return this.thread;
    }

    public Post toPost(boolean withThread, boolean withEmail, boolean withDescription, boolean withCategory, boolean withSection) {
        Thread thread;
        if(withThread) {
            thread = this.thread.toThread(false, false, false, withCategory, withSection);
        } else {
            thread = null;
        }
        return new Post(this.user.toUser(withEmail, withDescription), this.getText(), thread);
    }
}
