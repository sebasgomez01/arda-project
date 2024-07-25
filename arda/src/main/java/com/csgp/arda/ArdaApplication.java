package com.csgp.arda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;
import com.csgp.arda.domain.Post;
import com.csgp.arda.domain.PostRepository;

@SpringBootApplication
public class ArdaApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ArdaApplication.class);

	private final UserRepository repository;
	private final PostRepository prepository;

	public ArdaApplication(UserRepository repository, PostRepository prepository) {
		this.repository = repository;
		this.prepository = prepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ArdaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User("Sebastián", "kamikaze", 
			"$2y$10$ff6hx3JlXkooLTDj2vAFF.hOgLTBLuwD2YpMIybEG3NKzgcmfqvX6", "admin");
		User user2 = new User("José", "pepe",
		 	"$2y$10$iZGaBUnXuYhAUoL5LpqzOubru4KO.jlGR4cRTE4/qygyJrTUtUx3O", "user");

		repository.save(user1);
		repository.save(user2);

		for(User user : repository.findAll()) {
			logger.info("name {}, username {}", user.getName(), user.getUsername());
		}

		Post post1 = new Post("Tengo chafa?", "Eso, que si tengo chafa", user2);
		prepository.save(post1);
		user2.getPosts().add(post1);
		user1.getLikedPosts().add(post1);
		post1.getLikes().add(user1);
		prepository.save(new Post("El anon escuchó in absentia?", "Discazo, obra maestra de porcupine tree.", user1));

		for(Post post : prepository.findAll()) {
			User owner = post.getUser();
			//String username = owner.getUsername();
			logger.info("title {}, text-content {}", post.getTitle(), post.getTextContent());
			
		}

		
		for(Post post : user2.getPosts()) {
			String title = post.getTitle();
			logger.info("title {}", title);
			
		}	

		for(Post post : user1.getLikedPosts()) {
			String title = post.getTitle();
			logger.info("post likeados: {}", title);
			
		}
		
	}

}
