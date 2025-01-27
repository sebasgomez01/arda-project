package com.csgp.arda.domain.event;

import java.time.LocalDateTime;

public class PostInteractionEvent {
    // Enum para definir los tipos de interacción
    public enum InteractionType {
        LIKE,
        DISLIKE,
        REPOST,
        COMMENT
    }

    private Long postId; // ID del post
    private Long userId; // ID del usuario que interactúa
    private InteractionType type; // LIKE, DISLIKE, REPOST, COMMENT
    private LocalDateTime timestamp; // Fecha y hora de la interacción

    public PostInteractionEvent() {

    }

    public PostInteractionEvent(Long postId, Long userId, InteractionType type) {
        this.postId = postId;
        this.userId = userId;
        this.type = type;
    }

    // Getters y setters
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    public InteractionType getType() {
        return type;
    }

    public void setType(InteractionType type) {
        this.type = type;
    }

    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
