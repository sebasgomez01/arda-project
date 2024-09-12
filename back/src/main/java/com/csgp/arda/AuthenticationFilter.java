package com.csgp.arda;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.csgp.arda.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, java.io.IOException {
            // extraigp el token del header
            String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
            
            if(jwt != null) {
                // si el token no es nulo, obtento el usuario y lo autentico
                String user = jwtService.getAuthUser(request);

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, java.util.Collections.emptyList());

                // en SecurityContextHolder Spring guarda detalles del usuario actualmente autenticado
                //SecurityContextHolder.getContext().setAuthentication(authenticaction);

                SecurityContext context = SecurityContextHolder.createEmptyContext(); 
                //Authentication authentication = new TestingAuthenticationToken("username", "password", "ROLE_USER"); 
                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);
            } 

            filterChain.doFilter(request, response);
    }
}
