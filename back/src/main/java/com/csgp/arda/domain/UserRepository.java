package com.csgp.arda.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByName(String name);

    //@Query(value = "SELECT * FROM user_follower WHERE user_id = :userId", nativeQuery = true)
    //User findAllFollowersById(@Param("userId") Long userId);

    @Query(value = "SELECT u.* FROM users u " +
    "JOIN user_follower uf ON u.id = uf.follower_id " +
    "WHERE uf.user_id = :userId", nativeQuery = true)
    List<User> findAllFollowersById(@Param("userId") Long userId);

    @Query(value = "SELECT u.* FROM users u " +
    "JOIN user_follower uf ON u.id = uf.user_id " +
    "WHERE uf.follower_id = :userId", nativeQuery = true)
    List<User> findAllFollowingById(@Param("userId") Long userId);
}