package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.User;
import org.example.service.UserService;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testSignIn_fail() throws Exception {
        String userName = "testUser";
        String password = "password";
        String expectedResponse = "Login successful";

        when(userService.login(userName, password)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/signin")
                        .param("userName", userName)
                        .param("password", password))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetAllUsers_fail() throws Exception {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/getall"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetUserById_fail() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setUserID(userId);

        when(userService.getUserById(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateUser_fail() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setUserID(userId);

        doNothing().when(userService).updateUser(user, userId);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testFollowUser_fail() throws Exception {
        Long userId = 1L;
        Long followerId = 2L;

        doNothing().when(userService).followUser(userId, followerId);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{userId}/follow/{followerId}", userId, followerId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetFollowers_fail() throws Exception {
        Long userId = 1L;
        User user1 = new User();
        User user2 = new User();
        List<User> followers = Arrays.asList(user1, user2);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}