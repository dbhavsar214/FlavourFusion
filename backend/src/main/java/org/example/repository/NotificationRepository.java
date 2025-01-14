package org.example.repository;

import org.example.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    boolean save(Notification notification);
    Optional<Notification> findById(Long id);
    List<Notification> findAll();
    void update(Notification notification, Long id);
    void delete(Long id);
}
