package com.csgp.arda.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    
}