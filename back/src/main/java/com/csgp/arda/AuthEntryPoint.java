package com.csgp.arda;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
    throws IOException, ServerException {
        // pongo el status en UNAUThORIZED
        response.setStatus (HttpServletResponse.SC_UNAUTHORIZED);
        // pongo el tipo del contenido en JSON
        response.setContentType (MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        // Imprimo el mensaje de error
        writer.println("Error: " + authException.getMessage());
    }
}
