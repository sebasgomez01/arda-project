package com.csgp.arda.service.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.csgp.arda.domain.Notification;
import com.csgp.arda.domain.NotificationRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.event.FollowEvent;
import com.csgp.arda.web.NotificationController;

@Component
public class FollowListener {
    private final NotificationRepository notificactionRepository;
    private final NotificationController notificationController;

    FollowListener(NotificationRepository notificactionRepository,
    NotificationController notificationController) {
        this.notificactionRepository = notificactionRepository;
        this.notificationController = notificationController;
    }   

    @EventListener
    public void handlePostInteraction(FollowEvent event) {
        
        User follower = event.getFollower();
        User toFollow = event.getToFollow();

        String textContent = follower.getUsername() + " starts follow you"; 
        Notification notificacion = new Notification(textContent, follower, toFollow);
        notificactionRepository.save(notificacion);    
        // mando la notificación al front
        notificationController.sendNotification(notificacion);
    }
}
