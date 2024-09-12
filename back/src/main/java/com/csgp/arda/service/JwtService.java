package com.csgp.arda.service;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtService {
    // Defino constantes:
    static final long EXPIRATIONTIME = 86400000;
    // 1 day in ms. Should be shorter in production.
    static final String PREFIX = "Bearer";

    // genero la clave secreta
    static final Key key = Keys.secretKeyFor (SignatureAlgorithm.HS256);

    // función que crea y devuelve el token
    public String getToken(String username) {
        String token = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(key)
        .compact();
        return token;
    }

    // función que extrae el token del header Authorization si existe y devuelve el usuario
    // En otro caso devuelve 
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (token != null) {
            String user = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace(PREFIX, " "))
                .getBody()
                .getSubject();
            if (user != null)
            return user;
        }

        return null;
    }
}
