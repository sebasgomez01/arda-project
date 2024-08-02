package com.csgp.arda.web;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.csgp.arda.domain.AccountCredentials;
import com.csgp.arda.service.JwtService;
import org.springframework.http.HttpMethod;

import com.csgp.arda.service.UserService;

@RestController
@RequestMapping("/api/users")
public class CreateAccountController {
    
    private final UserService userService;

    

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user.getUsername(), user.getName(), user.getPassword());
        return ResponseEntity.ok(createdUser);
    }
}

