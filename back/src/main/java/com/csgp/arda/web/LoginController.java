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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        
        Authentication auth = authenticationManager.authenticate(creds);
        
        // Genero el token
        String jwt = jwtService.getToken(auth.getName());
        
        // revocó todos los tokens previamente creados 
        userService.revokeAllTokens(credentials.username());        

        // guardo el nuevo token 
        userService.saveUserToken(jwt, credentials.username());

        // Creo eñ cookie HttpOnly para almacenar el JWT
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);  //  No accesible por JavaScript
        //cookie.setSecure(true);    // Solo en HTTPS (en producción)
        cookie.setPath("/");       // Disponible para toda la app
        cookie.setMaxAge(7 * 24 * 60 * 60); // Expira en 7 días

        response.addCookie(cookie); // Envio la cookie en la respuesta

        // Construyo la respuesta con el token generado
        return ResponseEntity.ok("Success login");
    }



}
