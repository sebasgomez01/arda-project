package com.csgp.arda.web;

import com.csgp.arda.service.ImageService;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/posts")
public class NewPostController {
    
    private final ImageService imageService;
    private final PostRepository postRepository;
    private final UserRepository userRepository; 
    
    public NewPostController(ImageService imageService, PostRepository postRepository, UserRepository userRepository) {
        this.imageService = imageService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        // obtengo el usuario que creo el post

        Post createdPost = new Post(post.getTitle(), post.getTextContent(), post.getUser());
        // lo guardo en la base de datos
        postRepository.save(createdPPost);
        return ResponseEntity.ok(createdPPost);   
    }

    /*
    @PostMapping("/image")
    public void createImage( @RequestParam("image") MultipartFile image) throws IOException {
        String uploadDirectory = "src/main/resources/static/images";
        String imageString = "";

        imageString += imageService.saveImageToStorage(uploadDirectory, image);
    

        // Save the adsImagesString in your database
        // You can also associate it with other data in your Ads object
    }
    */
}
