package com.csgp.arda.web;

import com.csgp.arda.service.JwtService;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.domain.event.FollowEvent;


import java.util.List;
import java.util.Set;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class FollowInteractionsController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ApplicationEventPublisher eventPublisher;

    public FollowInteractionsController(UserRepository userRepository, JwtService jwtService, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.eventPublisher = eventPublisher;
    }

    @PatchMapping("/follow/{username}")
    public ResponseEntity<User> followUser(@PathVariable String username, HttpServletRequest request) {

        // obtengo el usuario autenticado
        User userAuthenticated = userRepository.findByUsername(jwtService.getAuthUser(request));

        // obtengo el usuario al que seguir 
        User userToFollow = userRepository.findByUsername(username);

        // agrego userAuthenticated a la lista de seguidores de userToFollow
        Set<User> updatedFollowers = userToFollow.getFollowers();
        updatedFollowers.add(userAuthenticated);
        userToFollow.setFollowers(updatedFollowers);
        userRepository.save(userToFollow);

        // publico el evento para generar la notificación asociada
        eventPublisher.publishEvent(new FollowEvent(userAuthenticated, userToFollow));

        return new ResponseEntity<>(userToFollow, HttpStatus.OK);
    }

    @PatchMapping("/delete-follow/{username}")
    public ResponseEntity<User> deleteFollow(@PathVariable String username, HttpServletRequest request) {

        // obtengo el usuario autenticado
        User userAuthenticated = userRepository.findByUsername(jwtService.getAuthUser(request));

        // obtengo el usuario al que dejar de seguir 
        User userToDeleteFollow = userRepository.findByUsername(username);

        // elimino userAuthenticated de la lista de seguidores
        Set<User> updatedFollowers = userToDeleteFollow.getFollowers();
        updatedFollowers.remove(userAuthenticated);
        userToDeleteFollow.setFollowers(updatedFollowers);
        userRepository.save(userToDeleteFollow);

        return new ResponseEntity<>(userToDeleteFollow, HttpStatus.OK);
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String username) {
        
        User user = userRepository.findByUsername(username);

        List<User> followers = userRepository.findAllFollowersById(user.getId());
        
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable String username) {
        
        User user = userRepository.findByUsername(username);

        List<User> following = userRepository.findAllFollowingById(user.getId());

        return ResponseEntity.ok(following);
    }
    
}