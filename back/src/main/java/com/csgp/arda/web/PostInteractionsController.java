package com.csgp.arda.web;

import com.csgp.arda.service.StorageFileNotFoundException;
import com.csgp.arda.service.StorageService;
import com.csgp.arda.service.JwtService;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.domain.event.PostInteractionEvent;
import com.csgp.arda.domain.event.PostInteractionEvent.InteractionType;

import java.io.IOException;
import java.util.Set;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

//@RestController
//@RequestMapping("/api/posts") // esta anotación no se puede usar con @RepositoryRestController al mismo tiempo 
@RepositoryRestController // para no anular las peticiones manejadas por Spring Data Rest
public class PostInteractionsController {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository; 
    private final JwtService jwtService;
    private final ApplicationEventPublisher eventPublisher;

    public PostInteractionsController(PostRepository postRepository, UserRepository userRepository, 
    JwtService jwtService, ApplicationEventPublisher eventPublisher) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.eventPublisher = eventPublisher;
    }

    private void deleteDislikePost(User user, Post post) {
        Set<Post> dislikedPosts = user.getDislikedPosts();
        if(dislikedPosts.contains(post)) {
            dislikedPosts.remove(post);
            user.setDislikedPosts(dislikedPosts);

            Set<User> dislikeUsers = post.getDislikes();
            dislikeUsers.remove(user);
            post.setDislikes(dislikeUsers);
        }
    }

    private void deleteLikedPost(User user, Post post) {
        Set<Post> likedPosts = user.getLikedPosts();
        if(likedPosts.contains(post)) {
            likedPosts.remove(post);
            user.setLikedPosts(likedPosts);

            Set<User> likeUsers = post.getLikes();
            likeUsers.remove(user);
            post.setLikes(likeUsers);
        }
    }

    private void deleteRepost(User user, Post post) {
        Set<Post> reposts = user.getReposts();
        if(reposts.contains(post)) {
            reposts.remove(post);
            user.setReposts(reposts);

            Set<User> repostUsers = post.getReposts();
            repostUsers.remove(user);
            post.setReposts(repostUsers);
        } 
    }

    private HttpHeaders buildURI(UriComponentsBuilder uriBuilder, Long id) {
        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/posts/{id}")
                                    .buildAndExpand(id)
                                    .toUri());
        
        return headers;
    }


    @PatchMapping("/posts/like/{postId}")
    public ResponseEntity<Post> likePost(@PathVariable Long postId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        
        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        User user = userRepository.findByUsername(username);
        // obtengo el post de la base de datos
        Post post = postRepository.findById(postId).get();

        // agrego el usuario a los likes del post
        Set<User> updatedPostLikes = post.getLikes();
        updatedPostLikes.add(user);
        post.setLikes(updatedPostLikes); 

        // agrego el post a los likes del usuario
        Set<Post> updatedPostLiked = user.getLikedPosts();
        updatedPostLiked.add(post);
        user.setLikedPosts(updatedPostLiked);

        // si el usuario le dió dislike antes, quito el dislike
        deleteDislikePost(user, post);

        // Guardar ambos en la base de datos
        postRepository.save(post);
        userRepository.save(user);

        // publico el evento para generar la notificación asociada
        eventPublisher.publishEvent(new PostInteractionEvent(post.getId(), user.getId(), InteractionType.LIKE));

        System.out.println("El controlador ha sido llamado");

        HttpHeaders headers = buildURI(uriBuilder, postId);

        // Devolver el código de estado 200 con la URI del recurso 
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }

    @PatchMapping("/posts/delete-like/{postId}")
    public ResponseEntity<Post> deleteLikePost(@PathVariable Long postId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        
        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        
        User user = userRepository.findByUsername(username);
        
        // obtengo el post de la base de datos
        Post post = postRepository.findById(postId).get();

        // elimino el like
        deleteLikedPost(user, post);
        
        // Guardar ambos en la base de datos
        postRepository.save(post);
        userRepository.save(user);

        System.out.println("El controlador ha sido llamado");

        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = buildURI(uriBuilder, postId);
        // Devolver el código de estado 200 con la URI del recurso 
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }

    @PatchMapping("/posts/dislike/{postId}")
    public ResponseEntity<Post> dislikePost(@PathVariable Long postId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        
        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        
        User user = userRepository.findByUsername(username);
        
        // obtengo el post de la base de datos
        Post post = postRepository.findById(postId).get();

        // agrego el usuario a los dislikes del post
        Set<User> updatedPostDislikes = post.getDislikes();
        updatedPostDislikes.add(user);
        post.setDislikes(updatedPostDislikes); 

        // agrego el post a los dislikes del usuario
        Set<Post> updatedDislikedPosts = user.getDislikedPosts();
        updatedDislikedPosts.add(post);
        user.setDislikedPosts(updatedDislikedPosts);

        // si el usuario le dió like antes, quito el like
        deleteLikedPost(user,post);
        
        // Guardar ambos en la base de datos
        postRepository.save(post);
        userRepository.save(user);

        // publico el evento para generar la notificación asociada
        eventPublisher.publishEvent(new PostInteractionEvent(post.getId(), user.getId(), InteractionType.DISLIKE));

        System.out.println("El controlador ha sido llamado");

        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = buildURI(uriBuilder, postId);
        // Devolver el código de estado 200 con la URI del recurso 
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }

    @PatchMapping("/posts/delete-dislike/{postId}")
    public ResponseEntity<Post> deleteDislikePost(@PathVariable Long postId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        
        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        
        User user = userRepository.findByUsername(username);
        
        // obtengo el post de la base de datos
        Post post = postRepository.findById(postId).get();

        // elimino el dislike
        deleteDislikePost(user, post);
        
        // Guardar ambos en la base de datos
        postRepository.save(post);
        userRepository.save(user);

        System.out.println("El controlador ha sido llamado");

        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = buildURI(uriBuilder, postId);
        // Devolver el código de estado 200 con la URI del recurso 
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }

    @PatchMapping("/posts/repost/{postId}")
    public ResponseEntity<Post> repost(@PathVariable Long postId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        
        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        
        User user = userRepository.findByUsername(username);
        
        // obtengo el post de la base de datos
        Post post = postRepository.findById(postId).get();

        // agrego el usuario a los resposts del post
        Set<User> updatedReposts = post.getReposts();
        updatedReposts.add(user);
        post.setReposts(updatedReposts); 

        // agrego el post a los reposts del usuario
        Set<Post> updatedUserReposts = user.getReposts();
        updatedUserReposts.add(post);
        user.setReposts(updatedUserReposts);
        
        // Guardar ambos en la base de datos
        postRepository.save(post);
        userRepository.save(user);

        // publico el evento para generar la notificación asociada
        eventPublisher.publishEvent(new PostInteractionEvent(post.getId(), user.getId(), InteractionType.REPOST));

        System.out.println("El controlador ha sido llamado");

        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = buildURI(uriBuilder, postId);
        // Devolver el código de estado 200 con la URI del recurso 
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }
    
    @PatchMapping("/posts/delete-repost/{postId}")
    public ResponseEntity<Post> deleteRepost(@PathVariable Long postId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {

        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        
        User user = userRepository.findByUsername(username);
        
        // obtengo el post de la base de datos
        Post post = postRepository.findById(postId).get();

        // elimino el repost
        deleteRepost(user, post);
        
        // Guardar ambos en la base de datos
        postRepository.save(post);
        userRepository.save(user);

        System.out.println("El controlador ha sido llamado");

        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = buildURI(uriBuilder, postId);
        // Devolver el código de estado 200 con la URI del recurso 
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Post> deletePost(@PathVariable Long postId, UriComponentsBuilder uriBuilder, HttpServletRequest request) {

        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        
        User user = userRepository.findByUsername(username);
        
        // obtengo el post de la base de datos
        Post post = postRepository.findById(postId).get();

        // elimino el post de todas las tablas join antes de eliminarlo definitivamente 
        deleteDislikePost(user, post);
        deleteLikedPost(user, post);
        deleteRepost(user, post);
    
        // elimino el post 
        postRepository.deleteById(postId);

        System.out.println("MI CONTROLADOR PARA ELIMINAR UN POST FUE LLAMADO!!!!!!!**********************************************");

        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = buildURI(uriBuilder, postId);
        // Devolver el código de estado 200 con la URI del recurso 
        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }
    
}
