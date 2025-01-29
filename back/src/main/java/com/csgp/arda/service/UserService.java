package com.csgp.arda.service;

import java.security.spec.EncodedKeySpec;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
 
import com.csgp.arda.domain.*;

//@Service
@Component
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserCredentialsRepository UserCredentialsRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, 
    UserCredentialsRepository UserCredentialsRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.UserCredentialsRepository = UserCredentialsRepository;
        this.tokenRepository = tokenRepository;
    }

    public User createUser(String username, String name) {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        return userRepository.save(user);
    }

    public UserCredentials createUserCredentials(String username, String name, String password, String role) {
        createUser(username, name);
        String encodedPassword = passwordEncoder.encode(password); // encripto la contraseña
        UserCredentials appUser = new UserCredentials();
        appUser.setUsername(username);
        appUser.setName(name);
        appUser.setPassword(encodedPassword);
        appUser.setRole(role);
        return UserCredentialsRepository.save(appUser);
    }

    public void saveUserToken(String accesToken, String username) {
        User user = userRepository.findByUsername(username);
        Token token = new Token(accesToken, false, user);
        tokenRepository.save(token);
    }

    public void revokeAllTokens(String username) {
        User user = userRepository.findByUsername(username);
        List<Token> tokensByUser = tokenRepository.findAllAccessTokensByUser(user.getId());
        for(Token token : tokensByUser) {
            token.setisLoggedOut(true);
        }

        tokenRepository.saveAll(tokensByUser);
    }
}
