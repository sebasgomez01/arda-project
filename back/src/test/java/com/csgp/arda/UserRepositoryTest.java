package com.csgp.arda;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;

@DataJpaTest
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository repository;

    /*
    public UserRepositoryTest(UserRepository repository) {
        this.repository = repository;
    }
     */
   
    @Test
    //@ParameterizedTest
    void saveUser() {
        repository.save(new User("Lucy", "paltaAguada", "abcdef", "user"));
        //boolean value = repository.findByName("Lucy").isPresent();
        assertThat(repository.findByName("Lucy")).isNotNull();
    }

}
