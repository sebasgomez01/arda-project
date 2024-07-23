package com.csgp.arda.domain;

import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String title;

    private String textContent;
     
    // relación con la entidad User para los posts
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    //@JsonBackReference
    private User user;

    // relación con la entidad User para los likes
    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
    private Set<User> likes;

    // relación con la entidad User para los dislikes
    @ManyToMany(mappedBy = "dislikedPosts", fetch = FetchType.LAZY)
    private Set<User> dislikes;

    // relación con la entidad User para los reposts
    @ManyToMany(mappedBy = "reposts", fetch = FetchType.LAZY)
    private Set<User> reposts;

    // establezco la relación con la entidad Comment para saber cuáles son los comentarios del post
    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")    
    private List<Comment> comments;  

    // Constructores
    public Post() {
    }

    public Post(String title, String textContent, User user) {
        super();
        this.title = title;
        this.textContent = textContent;
        this.user = user;
        this.likes = new HashSet<User>();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Set<User> getLikes() {
        return likes;
    }

}