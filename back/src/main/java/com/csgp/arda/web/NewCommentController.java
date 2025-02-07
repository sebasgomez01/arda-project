package com.csgp.arda.web;


import com.csgp.arda.service.JwtService;
import com.csgp.arda.domain.Comment;
import com.csgp.arda.domain.CommentRepository;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.domain.event.PostInteractionEvent;
import com.csgp.arda.domain.event.PostInteractionEvent.InteractionType;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.servlet.http.HttpServletRequest;

//@RequestMapping("/api/posts") // esta anotación no se puede usar con @RepositoryRestController al mismo tiempo 
@RepositoryRestController // para sobreescribir y no anular el resto de las peticiones manejadas por Spring Data Rest
public class NewCommentController {
    
    private final CommentRepository commentRepository;
    private final UserRepository userRepository; 
    private final JwtService jwtService;
    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    public NewCommentController(CommentRepository commentRepository, UserRepository userRepository, 
    JwtService jwtService, PostRepository postRepository, ApplicationEventPublisher eventPublisher) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.postRepository = postRepository;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        
        
        System.out.println("\n \n \n \n **************** MI CONTROLADOR PARA CREAR UN NUEVO COMENTARIO FUE LLAMADOO ++++++++++++++++ \n \n \n \n \n ");
        // obtengo el usuario autenticado utilizando el token
        System.out.println(comment);

        String username = jwtService.getAuthUser(request);
        
        System.out.println(username);

        User user = userRepository.findByUsername(username);
        System.out.println(user);
        // obtengo el post correspondiente
        Post post = postRepository.findById(comment.getPost().getId()).get();
        System.out.println(post);
        // Crear el nuevo comentario
        Comment createdComment = new Comment(comment.getTextContent(), user, post);
        
        // Guardar el comentario en la base de datos
        commentRepository.save(createdComment);
        
        // publico el evento para generar la notificación asociada
        eventPublisher.publishEvent(new PostInteractionEvent(post.getId(), user.getId(), InteractionType.COMMENT));

        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/comments/{id}")
                                    .buildAndExpand(createdComment.getId())
                                    .toUri());
        
        // Devolver el código de estado 201 con la URI del recurso creado
        return new ResponseEntity<>(createdComment, headers, HttpStatus.CREATED);
    }
    
    
}
