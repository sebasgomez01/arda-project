package com.csgp.arda.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.csgp.arda.service.*;
import com.csgp.arda.domain.*;

@RestController
@RequestMapping("/users")
public class CreateAccountController {
    
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateAccountController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/credentials")
    public ResponseEntity<User> createUser(@RequestBody UserCredentials user) {
        //User createdUser = userService.createUser(user.getUsername(), user.getName());
        String encodedPassword = passwordEncoder.encode(user.getPassword()); // encripto la contraseña
        UserCredentials createdAppUser = userService.createAppUser(user.getUsername(), 
        user.getName(), encodedPassword, user.getRole());
        User createdUser = userRepository.findByName(user.getName());
        return ResponseEntity.ok(createdUser);
    }
}

