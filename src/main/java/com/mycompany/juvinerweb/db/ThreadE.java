package com.mycompany.juvinerweb.db;

import com.juviner.data.Category;
import com.juviner.data.Post;
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

@Entity
@Table(name="threads")
public class ThreadE implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private CategoryE category;
    @Column(nullable=false)
    private String title;
    @OneToMany(mappedBy="thread")
    private List<PostE> posts;

    public ThreadE() { }

    public ThreadE(String title, CategoryE category) {
        this.title = title;
        this.category = category;
    }

    public int getId() {
        return this.id;
    }

    public CategoryE getCategory() {
        return this.category;
    }
    
    public String getTitle() {
        return this.title;
    }

    public List<PostE> getPosts() {
        return Collections.unmodifiableList(this.posts);
    }

    public Thread toThread(boolean withPosts, boolean withEmail, boolean withDescription, boolean withAvatar, boolean withGithub, boolean withCategory, boolean withSection) {
        List<Post> posts;
        Category category;
        if(withPosts) {
            posts = new ArrayList<>();
            this.posts.forEach(post -> posts.add(post.toPost(false, withEmail, withDescription, withAvatar, withGithub, false, false)));
        } else {
            posts = null;
        }
        if(withCategory) {
            category = this.category.toCategory(false, false, false, false, false, withSection, false, false);
        } else {
            category = null;
        }
        System.out.println(this.posts.size());
        return new Thread(this.id, this.title, this.posts.get(0).toPost(false, withEmail, withAvatar, withGithub, withDescription, withCategory, withSection), posts, category);
    }
}
