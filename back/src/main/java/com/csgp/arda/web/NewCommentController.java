package com.csgp.arda.web;

import com.csgp.arda.service.StorageFileNotFoundException;
import com.csgp.arda.service.StorageService;
import com.csgp.arda.service.JwtService;
import com.csgp.arda.domain.Comment;
import com.csgp.arda.domain.CommentRepository;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;

import java.io.IOException;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;

//@RequestMapping("/api/posts") // esta anotación no se puede usar con @RepositoryRestController al mismo tiempo 
@RepositoryRestController // para sobreescribir y no anular el resto de las peticiones manejadas por Spring Data Rest
public class NewCommentController {
    
    private final CommentRepository commentRepository;
    private final UserRepository userRepository; 
    private final JwtService jwtService;
    private final PostRepository postRepository;

    public NewCommentController(CommentRepository commentRepository, UserRepository userRepository, 
    JwtService jwtService, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.postRepository = postRepository;
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        
        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        
        System.out.println(username);

        User user = userRepository.findByUsername(username);
        
        // obtengo el post correspondiente
        Post post = postRepository.findById(comment.getPost().getId()).get();

        // Crear el nuevo comentario
        Comment createdComment = new Comment(comment.getTextContent(), user, post);
        
        // Guardar el comentario en la base de datos
        commentRepository.save(createdComment);
        
        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/comments/{id}")
                                    .buildAndExpand(createdComment.getId())
                                    .toUri());
        
        // Devolver el código de estado 201 con la URI del recurso creado
        return new ResponseEntity<>(createdComment, headers, HttpStatus.CREATED);
    }
    
    
}
