package com.csgp.arda.web;

import com.csgp.arda.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token") 
    public String token(Authentication authentication) {
        LOG.debug("Token solicitado por el usuario: '{}'", authentication.getName());
        // genero el token
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token garantizado: {}", token);
        return token;
    }
}
