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
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String textContent;

    // establezco relación ManyToMany entre User y Notification
    @ManyToMany(mappedBy = "notifications", fetch = FetchType.LAZY)
    private Set<User> users;
    
    // Constructores
    public Notification() {

    }

    public Notification(String textContent) {
        this.textContent = textContent;
    }

    // Getters y setters
    Long getId() {
        return this.id;
    }

    String getTextContent() {
        return this.getTextContent();
    }

    void setId(Long id) {
        this.id = id;
    } 

    void setTextContent(String textContent) {
        this.textContent = textContent;
    }

}
