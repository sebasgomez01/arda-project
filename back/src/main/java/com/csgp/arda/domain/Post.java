package com.csgp.arda.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String title;

    private String textContent;

    private String imagePath;

    // relación con la entidad User para los posts
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonIgnore
    //@JsonBackReference
    private User user;

    // relación con la entidad User para los likes
    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> likes;

    // relación con la entidad User para los dislikes
    @ManyToMany(mappedBy = "dislikedPosts", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> dislikes;

    // relación con la entidad User para los reposts
    @ManyToMany(mappedBy = "reposts", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> reposts;

    // establezco la relación con la entidad Comment para saber cuáles son los comentarios del post
    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")    
    @JsonIgnore
    private List<Comment> comments;  

    // Constructores
    public Post() {
    }

    public Post(String title, String textContent, User user, String imagePath) {
        super();
        this.title = title;
        this.textContent = textContent;
        this.user = user;
        this.imagePath = imagePath;
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

    public void setDislikes(Set<User> dislikes) {
        this.dislikes = dislikes;
    }

    public Set<User> getDislikes() {
        return dislikes;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    } 
    
    public void setReposts(Set<User> reposts) {
        this.reposts = reposts;
    }

    public Set<User> getReposts() {
        return reposts;
    }
}

