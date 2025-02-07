package com.csgp.arda.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = CustomNotification.class)
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment WHERE post_id = :postId", nativeQuery = true)
    List<Comment> findAllByPostId(@Param("postId") Long postId);
}