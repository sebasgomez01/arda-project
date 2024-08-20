package com.csgp.arda.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = CustomPost.class)
public interface PostRepository extends CrudRepository<Post, Long> {

}