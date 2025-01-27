package com.csgp.arda.domain.event;

import java.time.LocalDateTime;

import com.csgp.arda.domain.event.PostInteractionEvent.InteractionType;

public class CommentInteractionEvent {

    private Long commentId; // ID del comment
    private Long userId; // ID del usuario que interactúa
    private InteractionType type; // LIKE, DISLIKE
    private LocalDateTime timestamp; // Fecha y hora de la interacción

    public CommentInteractionEvent() {}

    public CommentInteractionEvent(Long commentId, Long userId, InteractionType type) {
        this.commentId = commentId;
        this.userId = userId;
        this.type = type;
    }

    // Getters y setters
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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
