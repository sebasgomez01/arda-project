package com.csgp.arda.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
 
import com.csgp.arda.domain.*;

//@Service
@Component
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialsRepository UserCredentialsRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserCredentialsRepository UserCredentialsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.UserCredentialsRepository = UserCredentialsRepository;
    }

    public User createUser(String username, String name) {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        //user.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
        return userRepository.save(user);
    }

    public UserCredentials createAppUser(String username, String name, String password, String role) {
        UserCredentials appUser = new UserCredentials();
        appUser.setUsername(username);
        appUser.setName(name);
        appUser.setPassword(password);
        appUser.setRole(role);
        return UserCredentialsRepository.save(appUser);
    }
}
