package com.csgp.arda.domain;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "customNotification", types = {Notification.class})
public interface CustomNotification {
    Long getId();
    String getTextContent();
    User getUser();
}
