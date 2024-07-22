package com.csgp.arda.domain;

import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, updatable=false)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String role;
     
    // establezco la relación con la entidad Post
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")    
    private List<Post> posts;   

    // relación con la entidad Post para los posts likeados
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_liked_posts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> likedPosts;

    // relación con la entidad Post para los posts reposteados
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_reposts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> reposts;

    // establezco la relación con la entidad Comment
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")    
    private List<Comment> comments;  

    // Constructores
    public User() {

    }   
    
    public User(String name, String username, String password, String role) {
        super();
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.posts = new ArrayList<Post>();
        this.likedPosts = new HashSet<Post>();
    }

    // getters y setters 
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;        
    }
}
