package com.csgp.arda.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.csgp.arda.domain.AccountCredentials;
import com.csgp.arda.service.JwtService;

@RestController
public class LoginController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        System.out.println("el login controller se ejecutó");
        logger.info("el login controller se ejecutó");
        

        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(),
                    credentials.password());
        
        Authentication auth = authenticationManager.authenticate(creds);
        
        // Genero el token
        String jwts = jwtService.getToken(auth.getName());
        
        // Construyo la respuesta con el token generado
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts).header(HttpHeaders.
            ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();
    }



}
