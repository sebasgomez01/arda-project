package com.csgp.arda.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csgp.arda.domain.PostRepository;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.service.StorageFileNotFoundException;
import com.csgp.arda.service.StorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class SaveImageController {
    private final StorageService storageService;

    public SaveImageController(StorageService storageService) {
        this.storageService = storageService;
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

    
    @GetMapping("/api/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Test endpoint is reachable");
    }


}
