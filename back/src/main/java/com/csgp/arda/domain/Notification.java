package com.csgp.arda.domain;

import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String textContent;

    // Relación con el usuario que causó la notificación
    @ManyToOne
    @JoinColumn(name = "caused_by_id")
    private User causedBy;

    // Relación con el usuario que recibió la notificación
    @ManyToOne
    @JoinColumn(name = "received_by_id")
    private User receivedBy;

    // Relación con el Post asociado a la notificaión
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // Relación con el Comment asociado a la notificaión
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    
    // Constructores
    public Notification() {

    }

    public Notification(String textContent, User causedBy, User receivedBy, Post post) {
        this.textContent = textContent;
        this.causedBy = causedBy;
        this.receivedBy = receivedBy;
        this.post = post;
    }

    
    public Notification(String textContent, User causedBy, User receivedBy, Comment comment) {
        this.textContent = textContent;
        this.causedBy = causedBy;
        this.receivedBy = receivedBy;
        this.comment = comment;
    }

    public Notification(String textContent, User causedBy, User receivedBy) {
        this.textContent = textContent;
        this.causedBy = causedBy;
        this.receivedBy = receivedBy;
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
