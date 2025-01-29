package com.csgp.arda;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
            //jwt = jwt.substring(7);
            if(jwt != null) {
                // si el token no es nulo, obtengo el usuario y lo autentico
                String username = jwtService.getAuthUser(request);
                jwt = jwt.substring(7);
                if(jwtService.validateToken(jwt, username)) {
                    Authentication authenticaction = new UsernamePasswordAuthenticationToken(username, 
                    null, java.util.Collections.emptyList());

                    // en SecurityContextHolder Spring guarda detalles del usuario actualmente autenticado
                    SecurityContextHolder.getContext().setAuthentication(authenticaction);
                } else {
                    System.out.println("\n \n \n \n ****************************************************************************** \n \n \n \n");
                    System.out.println("EL TOKEN NO FUE VALIDADO");
                    System.out.println("\n \n \n \n ****************************************************************************** \n \n \n \n");
                }
            } 

            filterChain.doFilter(request, response);
    }
}
