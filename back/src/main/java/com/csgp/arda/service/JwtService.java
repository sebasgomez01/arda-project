package com.csgp.arda.service;

import org.springframework.stereotype.Component;

import com.csgp.arda.domain.Token;
import com.csgp.arda.domain.TokenRepository;
import com.csgp.arda.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtService {

    TokenRepository tokenRepository;

    public JwtService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // Defino constantes:
    static final long EXPIRATIONTIME = 3600000;
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
    // En otro caso devuelve null
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (token != null) {
            String user = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace(PREFIX, ""))
                .getBody()
                .getSubject();
            if (user != null)
            return user;
        }

        return null;
    }

    public Boolean validateToken(String token, String username) {
        Token tokenEntity = tokenRepository.findByAccessToken(token).get();
        Boolean isValidToken = !tokenEntity.getIsLoggedOut();

        return isValidToken && username.equals(tokenEntity.getUser().getUsername());

    }
}
