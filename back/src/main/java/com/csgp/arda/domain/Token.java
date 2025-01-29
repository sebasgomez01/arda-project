package com.csgp.arda.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "is_logged_out")
    private boolean isLoggedOut;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token() {}

    public Token(String accessToken, boolean loggedOut, User user) {
        this.accessToken = accessToken;
        //this.refreshToken = refreshToken;
        this.isLoggedOut = loggedOut;
        this.user = user;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public boolean getIsLoggedOut() {
        return isLoggedOut;
    }

    public void setisLoggedOut(boolean isLoggedOut) {
        this.isLoggedOut = isLoggedOut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}