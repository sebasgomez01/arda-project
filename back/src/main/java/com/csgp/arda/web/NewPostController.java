package com.csgp.arda.web;

import com.csgp.arda.service.StorageFileNotFoundException;
import com.csgp.arda.service.StorageService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;


//@RestController
//@RequestMapping("/api/posts")
@RepositoryRestController // para no anular las peticiones manejadas por Spring Data Rest
public class NewPostController {
    
    private final StorageService storageService;
    private final PostRepository postRepository;
    private final UserRepository userRepository; 
    

    public NewPostController(StorageService storageService, PostRepository postRepository, UserRepository userRepository) {
        this.storageService = storageService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post, UriComponentsBuilder uriBuilder) {
        // Crear el nuevo post
        Post createdPost = new Post(post.getTitle(), post.getTextContent(), post.getUser());
        
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

    @PostMapping("/api/posts/image")
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {

		storageService.store(file);

		return "Image succesfully uploaded";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
