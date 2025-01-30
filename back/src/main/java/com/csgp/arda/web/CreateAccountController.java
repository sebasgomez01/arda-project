package com.csgp.arda.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import com.csgp.arda.service.*;
import com.csgp.arda.domain.*;

@RestController
@RequestMapping("/users")
public class CreateAccountController {
    
    private final UserService userService;
    private final UserRepository userRepository;

    public CreateAccountController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

    }

    @PostMapping("/credentials")
    public ResponseEntity<?> createUser(@RequestBody UserCredentials user) {
        // chequeo si el username está disponible
        if(userRepository.findByUsername(user.getUsername()) == null) {
            userService.createUserCredentials(user.getUsername(), user.getName(), user.getPassword(), user.getRole());
            User createdUser = userRepository.findByUsername(user.getUsername());
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("username is already taken!", HttpStatus.CONFLICT);

        }
        
        
    }
}

