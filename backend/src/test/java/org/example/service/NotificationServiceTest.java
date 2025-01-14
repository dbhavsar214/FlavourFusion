package org.example.service;

import org.example.model.Notification;
import org.example.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotification() {
        // Arrange
        Notification notification = new Notification();
        when(notificationRepository.save(notification)).thenReturn(true);

        // Act
        boolean result = notificationService.createNotification(notification);

        // Assert
        assertTrue(result);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testGetNotificationById_Found() {
        // Arrange
        Long notificationId = 1L;
        Notification notification = new Notification();
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // Act
        Notification result = notificationService.getNotificationById(notificationId);

        // Assert
        assertNotNull(result);
        assertEquals(notification, result);
    }

    @Test
    void testGetNotificationById_NotFound() {
        // Arrange
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            notificationService.getNotificationById(notificationId);
        });
        assertEquals("Notification not found", thrown.getReason());
    }

    @Test
    void testGetAllNotifications() {
        // Arrange
        List<Notification> notifications = new ArrayList<>();
        when(notificationRepository.findAll()).thenReturn(notifications);

        // Act
        List<Notification> result = notificationService.getAllNotifications();

        // Assert
        assertNotNull(result);
        assertEquals(notifications, result);
    }

    @Test
    void testUpdateNotification() {
        // Arrange
        Notification notification = new Notification();
        Long notificationId = 1L;
        doNothing().when(notificationRepository).update(notification, notificationId);

        // Act
        notificationService.updateNotification(notification, notificationId);

        // Assert
        verify(notificationRepository, times(1)).update(notification, notificationId);
    }

    @Test
    void testDeleteNotification() {
        // Arrange
        Long notificationId = 1L;
        doNothing().when(notificationRepository).delete(notificationId);

        // Act
        notificationService.deleteNotification(notificationId);

        // Assert
        verify(notificationRepository, times(1)).delete(notificationId);
    }
}