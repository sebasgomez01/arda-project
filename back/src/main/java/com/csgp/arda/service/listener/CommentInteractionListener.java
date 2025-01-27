package com.csgp.arda.service.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.csgp.arda.domain.Comment;
import com.csgp.arda.domain.CommentRepository;
import com.csgp.arda.domain.Notification;
import com.csgp.arda.domain.NotificationRepository;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.domain.event.CommentInteractionEvent;
import com.csgp.arda.domain.event.PostInteractionEvent;

@Component
public class CommentInteractionListener {
    private final NotificationRepository notificactionRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentInteractionListener(NotificationRepository notificactionRepository, UserRepository userRepository,
    CommentRepository commentRepository) {
        this.notificactionRepository = notificactionRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @EventListener
    public void handleCommentInteraction(CommentInteractionEvent event) {
        switch (event.getType()) {
            case LIKE -> handleLike(event);
            case DISLIKE -> handleDislike(event);
            case REPOST -> System.out.println("repost");
            case COMMENT -> System.out.println("comment");
        }
    }

    private void handleLike(CommentInteractionEvent event) {
        User causedBy = userRepository.findById(event.getUserId()).get();

        Comment comment = commentRepository.findById(event.getCommentId()).get();
        User receivedBy = comment.getUser();

        String textContent = causedBy.getName() + " liked your comment"; 
        Notification notificacion = new Notification(textContent, causedBy, receivedBy, comment);
        notificactionRepository.save(notificacion);    

        System.out.println("Comment " + event.getCommentId() + " recibió un like de " + event.getUserId());
    }

    private void handleDislike(CommentInteractionEvent event) {
        
        User causedBy = userRepository.findById(event.getUserId()).get();
        Comment comment = commentRepository.findById(event.getCommentId()).get();
        User receivedBy = comment.getUser();

        String textContent = causedBy.getName() + " disliked your comment"; 
        Notification notificacion = new Notification(textContent, causedBy, receivedBy, comment);
        notificactionRepository.save(notificacion);    

        System.out.println("Comment " + event.getCommentId() + " recibió un dislike de " + event.getUserId());
    }

    
}
