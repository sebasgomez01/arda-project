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


@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String textContent;
    
    // relación con la entidad User para identificar al usuario que hizo el comentario
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    
    // relación con la entidad Post para identificar a qué post pertenece el comentario
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="post_id")
    private Post post;

    // relación con otro comentario para modelar las respuestas a un comentario
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replies;

    // relación con la entidad User para los likes del comentario
    @ManyToMany(mappedBy = "likedComments", fetch = FetchType.LAZY)
    private Set<User> likes;

    // relación con la entidad User para los dislikes del comentario
    @ManyToMany(mappedBy = "dislikedComments", fetch = FetchType.LAZY)
    private Set<User> dislikes;

    // Constructores
    public Comment() {
    }

    public Comment(String textContent, User user, Post post) {
        super();
        this.textContent = textContent;
        this.user = user;    
        this.post = post;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    /*
    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Set<User> getLikes() {
        return likes;
    }
    */
}