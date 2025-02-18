package com.csgp.arda.domain;

import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "users") // esto es porque la palabra user está reservada en PostgreSQL y no se puede tener una tabla con ese nombre
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, updatable=false)
    @JsonIgnore
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String username;
 
    // establezco la relación con la entidad Post
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")    
    //@JsonManagedReference
    private List<Post> posts;   

    // relación con la entidad Post para los posts likeados
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_liked_posts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> likedPosts;

    // relación con la entidad Post para los dislikes
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( 
        name = "user_disliked_posts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> dislikedPosts;

    // relación con la entidad Post para los posts reposteados
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_reposts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> reposts;

    // establezco la relación con la entidad Comment
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")    
    private List<Comment> comments;  

    // relación con la entidad Post para los comentarios likeados por el usuario
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_liked_comments",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<Comment> likedComments;

    // relación con la entidad Post para los comentarios dislikeados por el usuario
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_disliked_comments",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<Comment> dislikedComments;

    // Relación "causada por" con Notification
    @OneToMany(mappedBy = "causedBy")
    @JsonIgnore
    private Set<Notification> causedNotifications;

    // Relación "recibida por" con Notificacion
    @OneToMany(mappedBy = "receivedBy")
    @JsonIgnore
    private Set<Notification> receivedNotifications;

    // establezco relacion ManyToMany entre User y User para modelar los seguidores de un usuario
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_follower",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Token> tokens;

    // Constructores
    public User() {

    }   
    
    public User(String name, String username) {
        super();
        this.name = name;
        this.username = username;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<Post> getReposts() {
        return reposts;
    }

    public void setReposts(Set<Post> reposts) {
        this.reposts = reposts;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;        
    }

    public Set<Post> getDislikedPosts() {
        return dislikedPosts;
    }

    public void setDislikedPosts(Set<Post> dislikedPosts) {
        this.dislikedPosts = dislikedPosts;        
    }

    public Set<Comment> getLikedComments() {
        return likedComments;
    }

    public void setLikedComments(Set<Comment> likedComments) {
        this.likedComments = likedComments;        
    }

    public Set<Comment> getDislikedComments() {
        return dislikedComments;
    }

    public void setDislikedComments(Set<Comment> dislikedComments) {
        this.dislikedComments = dislikedComments;        
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }
}
