package com.csgp.arda.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String textContent;
    
    // relación con la entidad User para identificar al usuario que hizo el comentario
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    //@JsonIgnore
    private User user;

    
    // relación con la entidad Post para identificar a qué post pertenece el comentario
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    // relación con otro comentario para modelar las respuestas a un comentario
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnore
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    @JsonIgnore
    private List<Comment> replies;

    // relación con la entidad User para los likes del comentario
    @ManyToMany(mappedBy = "likedComments", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> likes;

    // relación con la entidad User para los dislikes del comentario
    @ManyToMany(mappedBy = "dislikedComments", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> dislikes;

    // Relación con Notification
    @OneToMany(mappedBy = "comment")
    @JsonIgnore
    private Set<Notification> notifications;
    
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

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
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
}