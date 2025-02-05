package com.csgp.arda.web;

import com.csgp.arda.service.JwtService;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;



import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.servlet.http.HttpServletRequest;

//@RestController
//@RequestMapping("/api/posts") // esta anotación no se puede usar con @RepositoryRestController al mismo tiempo 
@RepositoryRestController // para no anular las peticiones manejadas por Spring Data Rest
public class NewPostController {
    
    
    private final PostRepository postRepository;
    private final UserRepository userRepository; 
    private final JwtService jwtService;

    public NewPostController(PostRepository postRepository, UserRepository userRepository, 
    JwtService jwtService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post, UriComponentsBuilder uriBuilder, HttpServletRequest request) {
        
        // obtengo el usuario autenticado utilizando el token
        String username = jwtService.getAuthUser(request);
        
        System.out.println(username);

        User user = userRepository.findByUsername(username);
        
        // Crear el nuevo post
        Post createdPost = new Post(post.getTitle(), post.getTextContent(), user, null);
        
        // Guardar el post en la base de datos
        postRepository.save(createdPost);
        
        System.out.println("El controlador ha sido llamado");

        // Construir la URI del nuevo recurso creado
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/posts/{id}")
                                    .buildAndExpand(createdPost.getId())
                                    .toUri());
        
        // Devolver el código de estado 201 con la URI del recurso creado
        return new ResponseEntity<>(createdPost, headers, HttpStatus.CREATED);
    }
    
    
}
