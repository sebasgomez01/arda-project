package com.csgp.arda.service;

import java.util.Optional;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.csgp.arda.domain.*;


@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;

    public UserDetailsServiceImplementation(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials user = userCredentialsRepository.findByUsername(username);

        UserBuilder builder = null;
        if(user != null) {
            UserCredentials currentUser = user;
            builder = org.springframework.security.core.userdetails.
                        User.withUsername(username);
            builder.password(currentUser.getPassword());
            builder.roles(currentUser.getRole());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
        
    

}
