package com.csgp.arda.domain;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customPost", types = {Post.class})
public interface CustomPost {
    String getTextContent();
    String getTitle();
    User getUser();
    String getImagePath();
}
