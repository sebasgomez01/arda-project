package com.csgp.arda.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.csgp.arda.domain.AccountCredentials;
import com.csgp.arda.service.JwtService;
import com.csgp.arda.service.UserService;

@RestController
@RequestMapping("/users")
public class LoginController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        
        Authentication auth = authenticationManager.authenticate(creds);
        
        // Genero el token
        String jwts = jwtService.getToken(auth.getName());
        
        // revocó todos los tokens previamente creados 
        userService.revokeAllTokens(credentials.username());        

        // guardo el nuevo token 
        userService.saveUserToken(jwts, credentials.username());

        // Construyo la respuesta con el token generado
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts).header(HttpHeaders.
            ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();
    }



}
