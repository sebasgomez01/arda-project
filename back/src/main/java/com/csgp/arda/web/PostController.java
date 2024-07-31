package com.csgp.arda.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;

@RestController
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    } 

}
