package com.csgp.arda;

import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.service.JwtService;
import com.csgp.arda.domain.User;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public CustomLogoutHandler(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        //Token storedToken = tokenRepository.findByAccessToken(token).orElse(null);
        //User user = storedToken.getUser();
        User user = userRepository.findByUsername(jwtService.getAuthUser(request));

        //if(storedToken != null) {
           //storedToken.setLoggedOut(true);
            //tokenRepository.save(storedToken);

        //}

        //tokenRepository.deleteByUserId(user.getId());
    }
}