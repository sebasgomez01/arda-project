package com.csgp.arda.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

@RepositoryRestResource(exported = false)
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {
    UserCredentials findByUsername(String username);
    UserCredentials findByName(String name);
}