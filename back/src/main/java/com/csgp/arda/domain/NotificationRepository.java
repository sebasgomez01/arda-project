package com.csgp.arda.domain;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import jakarta.transaction.Transactional;

//@RepositoryRestResource(path = "notifications", exported = false) // Deshabilita la API REST generada
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM notification WHERE post_id = :postId", nativeQuery = true)
    void deleteAllByPostId(@Param("postId") Long postId);  
}