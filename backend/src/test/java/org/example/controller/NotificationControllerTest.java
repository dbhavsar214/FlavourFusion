package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Notification;
import org.example.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
class NotificationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void testCreateNotification_Failure() throws Exception {
        Notification notification = new Notification();

        when(notificationService.createNotification(notification)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/notifications/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notification)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Failed to create notification"));
    }

    @Test
    void testGetNotificationById() throws Exception {
        Long notificationId = 1L;
        Notification notification = new Notification();

        when(notificationService.getNotificationById(notificationId)).thenReturn(notification);

        mockMvc.perform(MockMvcRequestBuilders.get("/notifications/{id}", notificationId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllNotifications() throws Exception {
        Notification notification1 = new Notification();
        Notification notification2 = new Notification();
        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationService.getAllNotifications()).thenReturn(notifications);

        mockMvc.perform(MockMvcRequestBuilders.get("/notifications")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").exists());
    }

    @Test
    void testUpdateNotification() throws Exception {
        Long notificationId = 1L;
        Notification notification = new Notification();
        // Set properties for the notification if needed

        mockMvc.perform(MockMvcRequestBuilders.put("/notifications/{id}", notificationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notification)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Notification updated successfully"));

    }

    @Test
    void testDeleteNotification() throws Exception {
        Long notificationId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/notifications/{id}", notificationId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Notification deleted successfully"));

        verify(notificationService).deleteNotification(notificationId);
    }
}