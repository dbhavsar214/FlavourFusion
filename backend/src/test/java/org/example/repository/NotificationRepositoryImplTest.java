package org.example.repository;

import org.example.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class NotificationRepositoryImplTest {
    @InjectMocks
    private NotificationRepositoryImpl notificationRepository;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    void testSave_Success() throws SQLException {
        Notification notification = new Notification(1L, 2L, "Test body", "Test title");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean result = notificationRepository.save(notification);
    }

    @Test
    void testSave_Failure() throws SQLException {
        Notification notification = new Notification(1L, 2L, "Test body", "Test title");
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        boolean result = notificationRepository.save(notification);
    }

    @Test
    void testFindById_Found() throws SQLException {
        Long id = 1L;

        Notification expectedNotification = new Notification(id, 2L, "Test body", "Test title");

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("notificationID")).thenReturn(expectedNotification.getNotificationID());
        when(mockResultSet.getLong("receiverID")).thenReturn(expectedNotification.getReceiverID());
        when(mockResultSet.getString("body")).thenReturn(expectedNotification.getBody());
        when(mockResultSet.getString("title")).thenReturn(expectedNotification.getTitle());

        Optional<Notification> result = notificationRepository.findById(id);

    }

    @Test
    void testFindById_NotFound() throws SQLException {
        Long id = 1L;

        when(mockResultSet.next()).thenReturn(false);

        Optional<Notification> result = notificationRepository.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() throws SQLException {
        Notification notification1 = new Notification(1L, 2L, "Body 1", "Title 1");
        Notification notification2 = new Notification(2L, 3L, "Body 2", "Title 2");

        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("notificationID")).thenReturn(notification1.getNotificationID()).thenReturn(notification2.getNotificationID());
        when(mockResultSet.getLong("receiverID")).thenReturn(notification1.getReceiverID()).thenReturn(notification2.getReceiverID());
        when(mockResultSet.getString("body")).thenReturn(notification1.getBody()).thenReturn(notification2.getBody());
        when(mockResultSet.getString("title")).thenReturn(notification1.getTitle()).thenReturn(notification2.getTitle());

        List<Notification> result = notificationRepository.findAll();
    }

    @Test
    void testUpdate() throws SQLException {
        Notification notification = new Notification(1L, 2L, "Updated body", "Updated title");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        notificationRepository.update(notification, 1L);

    }

    @Test
    void testDelete() throws SQLException {
        Long id = 1L;
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        notificationRepository.delete(id);
    }
}