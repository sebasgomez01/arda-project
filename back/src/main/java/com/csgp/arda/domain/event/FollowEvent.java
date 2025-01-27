package com.csgp.arda.domain.event;

import java.time.LocalDateTime;

import com.csgp.arda.domain.User;

public class FollowEvent {
    private User follower;
    private User toFollow;
    private LocalDateTime timestamp; // Fecha y hora de la interacción

    public FollowEvent(User follower, User toFollow) {
        this.follower = follower;
        this.toFollow = toFollow;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollower() {
        return follower;
    }

    public void setToFollow(User toFollow) {
        this.toFollow = toFollow;
    }

    public User getToFollow() {
        return toFollow;
    }
}
