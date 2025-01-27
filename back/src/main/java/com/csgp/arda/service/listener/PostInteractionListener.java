package com.csgp.arda.service.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.csgp.arda.domain.Notification;
import com.csgp.arda.domain.NotificationRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.event.PostInteractionEvent;

@Component
public class PostInteractionListener {
    
    private final NotificationRepository notificactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostInteractionListener(NotificationRepository notificactionRepository, UserRepository userRepository, PostRepository postRepository) {
        this.notificactionRepository = notificactionRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @EventListener
    public void handlePostInteraction(PostInteractionEvent event) {
        switch (event.getType()) {
            case LIKE -> handleLike(event);
            case DISLIKE -> handleDislike(event);
            case REPOST -> handleRepost(event);
            case COMMENT -> handleRepost(event);
        }
    }

    private void handleLike(PostInteractionEvent event) {
        User causedBy = userRepository.findById(event.getUserId()).get();

        Post post = postRepository.findById(event.getPostId()).get();
        User receivedBy = post.getUser();

        String textContent = causedBy.getName() + " liked your post"; 
        Notification notificacion = new Notification(textContent, causedBy, receivedBy, post);
        notificactionRepository.save(notificacion);    

        System.out.println("Post " + event.getPostId() + " recibió un like de " + event.getUserId());
    }

    private void handleDislike(PostInteractionEvent event) {
        User causedBy = userRepository.findById(event.getUserId()).get();
        Post post = postRepository.findById(event.getPostId()).get();
        User receivedBy = post.getUser();

        String textContent = causedBy.getName() + " disliked your post"; 
        Notification notificacion = new Notification(textContent, causedBy, receivedBy, post);
        notificactionRepository.save(notificacion);    

        System.out.println("Post " + event.getPostId() + " recibió un dislike de " + event.getUserId());
    }

    private void handleRepost(PostInteractionEvent event) {
        User causedBy = userRepository.findById(event.getUserId()).get();
        Post post = postRepository.findById(event.getPostId()).get();
        User receivedBy = post.getUser();

        String textContent = causedBy.getName() + " repost your post"; 
        Notification notificacion = new Notification(textContent, causedBy, receivedBy, post);
        notificactionRepository.save(notificacion);    
        System.out.println("Post " + event.getPostId() + " fue repostado por " + event.getUserId());
    }
}