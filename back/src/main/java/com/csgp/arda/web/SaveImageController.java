package com.csgp.arda.web;

import java.nio.file.Path;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.service.StorageFileNotFoundException;
import com.csgp.arda.service.StorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class SaveImageController {
    private final StorageService storageService;
    private final PostRepository postRepository;

    public SaveImageController(StorageService storageService, PostRepository postRepository) {
        this.storageService = storageService;
        this.postRepository = postRepository;
    }

    
    @PostMapping("/api/posts/image")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
    @RequestParam("postIdentifier") String postIdentifier) {

        System.out.println(postIdentifier);

        char charPostId = postIdentifier.charAt(postIdentifier.length() - 1);
        String stringPostId = String.valueOf(charPostId);
        Long postId = Long.parseLong(stringPostId);

        System.out.println(postId);
        
        Post post = postRepository.findById(postId).get();
        
        System.out.println(post.getTitle());

		storageService.store(file);
        String imagePath = storageService.load(file.getOriginalFilename()).toString();

        post.setImagePath(imagePath);
        System.out.println(post);
        postRepository.save(post);

        // falta guardar el path de la imagen en la base de datos, para esto necesito saber 
        // el usuario que creo el post. Al estar activada la autorización con JWT, voy a poder obtener
        // el nombre del usuario mediante el token y ahí usar post repository para guardarlo.

		return "Image succesfully uploaded";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

    
    @GetMapping("/api/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Test endpoint is reachable");
    }


}
