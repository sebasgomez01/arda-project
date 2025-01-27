package com.csgp.arda.web;

import com.csgp.arda.service.JwtService;
import com.csgp.arda.domain.Comment;
import com.csgp.arda.domain.CommentRepository;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.domain.event.CommentInteractionEvent;
import com.csgp.arda.domain.event.PostInteractionEvent;
import com.csgp.arda.domain.event.PostInteractionEvent.InteractionType;

import java.util.Set;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.servlet.http.HttpServletRequest;

@RepositoryRestController 
public class CommentInteractionsController {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository; 
    private final JwtService jwtService;
    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CommentInteractionsController(CommentRepository commentRepository, UserRepository userRepository, 
    JwtService jwtService, PostRepository postRepository, ApplicationEventPublisher eventPublisher) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.postRepository = postRepository;
        this.eventPublisher = eventPublisher;
    }   

    private void deleteDislikedComment(User user, Comment comment) {
        Set<Comment> dislikedComments = user.getDislikedComments();
        if(dislikedComments.contains(comment)) {
            dislikedComments.remove(comment);
            user.setDislikedComments(dislikedComments);

            Set<User> dislikeUsers = comment.getDislikes();
            dislikeUsers.remove(user);
            comment.setDislikes(dislikeUsers);
        }
    }

    private void deleteLikedComment(User user, Comment comment) {
        Set<Comment> likedComments = user.getLikedComments();
        if(likedComments.contains(comment)) {
            likedComments.remove(comment);
            user.setLikedComments(likedComments);

            Set<User> likeUsers = comment.getLikes();
            likeUsers.remove(user);
            comment.setLikes(likeUsers);
        }
    }

    private HttpHeaders buildURI(UriComponentsBuilder uriBuilder, Long id) {
        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/comments/{id}")
                                    .buildAndExpand(id)
                                    .toUri());
        
        return headers;
    }

    @PatchMapping("/comments/like/{commentId}")
    private ResponseEntity<Comment> likeComment(@PathVariable Long commentId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {

        // obtengo el usuario autenticado 
        String username = jwtService.getAuthUser(request);
        User user = userRepository.findByUsername(username);

        // obtengo el comentario a actualizar
        Comment comment = commentRepository.findById(commentId).get();

        // agrego el user al los likes de comment
        Set<User> updatedLikes = comment.getLikes();
        updatedLikes.add(user);
        comment.setLikes(updatedLikes);

        // agrego el comment a los likedComments de user 
        Set<Comment> updatedLikedComments = user.getLikedComments();
        updatedLikedComments.add(comment);
        user.setLikedComments(updatedLikedComments);

        // si el usuario dió dislike antes, lo quito
        deleteDislikedComment(user, comment);

        // guardo ambos en la base de datos 
        userRepository.save(user);
        commentRepository.save(comment);

        // publico el evento para generar la notificación asociada
        eventPublisher.publishEvent(new CommentInteractionEvent(comment.getId(), user.getId(), InteractionType.LIKE));

        HttpHeaders headers = buildURI(uriBuilder, commentId);

        return new ResponseEntity<>(comment, headers, HttpStatus.OK);
    }

    @PatchMapping("/comments/delete-like/{commentId}")
    ResponseEntity<Comment> deleteLikeComment(@PathVariable Long commentId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        // obtengo el usuario autenticado 
        String username = jwtService.getAuthUser(request);
        User user = userRepository.findByUsername(username);

        // obtengo el comentario a actualizar
        Comment comment = commentRepository.findById(commentId).get();

        // elimino el like
        deleteLikedComment(user, comment);

        // guardo ambos en la base de datos 
        userRepository.save(user);
        commentRepository.save(comment);

        HttpHeaders headers = buildURI(uriBuilder, commentId);

        return new ResponseEntity<>(comment, headers, HttpStatus.OK);
    }

    @PatchMapping("/comments/dislike/{commentId}")
    private ResponseEntity<Comment> dislikeComment(@PathVariable Long commentId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {

        // obtengo el usuario autenticado 
        String username = jwtService.getAuthUser(request);
        User user = userRepository.findByUsername(username);

        // obtengo el comentario a actualizar
        Comment comment = commentRepository.findById(commentId).get();

        // agrego el user al los dislikes de comment
        Set<User> updatedDislikes = comment.getDislikes();
        updatedDislikes.add(user);
        comment.setDislikes(updatedDislikes);

        // agrego el comment a los dislikedComments de user 
        Set<Comment> updatedDislikedComments = user.getDislikedComments();
        updatedDislikedComments.add(comment);
        user.setDislikedComments(updatedDislikedComments);

        // si el usuario dió like antes, lo quito
        deleteLikedComment(user, comment);

        // guardo ambos en la base de datos 
        userRepository.save(user);
        commentRepository.save(comment);

        // publico el evento para generar la notificación asociada
        eventPublisher.publishEvent(new CommentInteractionEvent(comment.getId(), user.getId(),InteractionType.DISLIKE));

        HttpHeaders headers = buildURI(uriBuilder, commentId);

        return new ResponseEntity<>(comment, headers, HttpStatus.OK);
    }

    @PatchMapping("/comments/delete-dislike/{commentId}")
    ResponseEntity<Comment> deleteDislikeComment(@PathVariable Long commentId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        // obtengo el usuario autenticado 
        String username = jwtService.getAuthUser(request);
        User user = userRepository.findByUsername(username);

        // obtengo el comentario a actualizar
        Comment comment = commentRepository.findById(commentId).get();

        // elimino el dislike
        deleteDislikedComment(user, comment);

        // guardo ambos en la base de datos 
        userRepository.save(user);
        commentRepository.save(comment);

        HttpHeaders headers = buildURI(uriBuilder, commentId);

        return new ResponseEntity<>(comment, headers, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    ResponseEntity<Comment> deleteComment(@PathVariable Long commentId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        // obtengo el usuario autenticado 
        String username = jwtService.getAuthUser(request);
        User user = userRepository.findByUsername(username);

        // obtengo el comentario a actualizar
        Comment comment = commentRepository.findById(commentId).get();

        // lo elimino de las JOINS tables antes de eliminarlo definitivamente
        deleteLikedComment(user, comment);
        deleteDislikedComment(user, comment);

        commentRepository.deleteById(commentId);

        HttpHeaders headers = buildURI(uriBuilder, commentId);

        return new ResponseEntity<>(comment, headers, HttpStatus.OK);

    } 
}
