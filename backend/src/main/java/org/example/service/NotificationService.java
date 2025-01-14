package org.example.service;

import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public boolean createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found");
        }
        return notification.get();
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public void updateNotification(Notification notification, Long id) {
        notificationRepository.update(notification, id);
    }

    public void deleteNotification(Long id) {
        notificationRepository.delete(id);
    }
}
