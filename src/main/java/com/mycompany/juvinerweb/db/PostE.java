/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.juvinerweb.db;

import com.juviner.data.Post;
import com.juviner.data.Thread;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="posts")
public class PostE implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional=false, cascade = CascadeType.PERSIST)
    private ThreadE thread;
    @ManyToOne(optional=false, cascade = CascadeType.PERSIST)
    private UserE user;
    @Column(nullable=false)
    private String text;
    @Column(nullable=false)
    private Timestamp time;

    public PostE() { }
    
    public PostE(ThreadE thread, UserE user, String text) {
        this.thread = thread;
        this.user = user;
        this.text = text;
        this.time = new Timestamp(System.currentTimeMillis());
    }
    
    public int getId() {
        return this.id;
    }

    public UserE getUser() {
        return this.user;
    }

    public String getText() {
        return this.text;
    }

    public Timestamp getTime() {
        return this.time;
    }
    
    public ThreadE getThread() {
        return this.thread;
    }

    public Post toPost(boolean withThread, boolean withEmail, boolean withDescription, boolean withAvatar, boolean withGithub, boolean withCategory, boolean withSection) {
        Thread thread;
        if(withThread) {
            thread = this.thread.toThread(false, false, false, false, false, withCategory, withSection);
        } else {
            thread = null;
        }
        return new Post(this.time.toLocalDateTime(), this.user.toUser(withEmail, withDescription, withAvatar, withGithub), this.getText(), thread);
    }
}
