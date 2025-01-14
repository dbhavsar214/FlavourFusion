package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
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
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        String username = "testuser";
        String password = "testpass";
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);

        when(userRepository.login(username, password)).thenReturn(Optional.of(user));

        // Act
        String result = userService.login(username, password);

        // Assert
        assertEquals("Login Successful", result);
        verify(userRepository).login(username, password);
    }

    @Test
    void testLogin_Failure() {
        // Arrange
        String username = "testuser";
        String password = "testpass";

        when(userRepository.login(username, password)).thenReturn(Optional.empty());

        // Act
        String result = userService.login(username, password);

        // Assert
        assertEquals("Login Failed", result);
        verify(userRepository).login(username, password);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(users.size(), result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setUserID(userId);

        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserID());
        verify(userRepository).getUserById(userId);
    }

    @Test
    void testGetUserById_Failure() {
        // Arrange
        Long userId = 1L;

        when(userRepository.getUserById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.getUserById(userId));
        verify(userRepository).getUserById(userId);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository).delete(userId);
    }

    @Test
    void testFollowUser() {
        // Arrange
        Long userId = 1L;
        Long followerId = 2L;

        // Act
        userService.followUser(userId, followerId);

        // Assert
        verify(userRepository).followUser(userId, followerId);
    }

    @Test
    void testUnfollowUser() {
        // Arrange
        Long userId = 1L;
        Long followerId = 2L;

        // Act
        userService.unfollowUser(userId, followerId);

        // Assert
        verify(userRepository).unfollowUser(userId, followerId);
    }

    @Test
    void testGetFollowers() {
        // Arrange
        Long userId = 1L;
        List<Long> followers = new ArrayList<>();
        followers.add(1L);

        when(userRepository.getFollowedUserIds(userId)).thenReturn(followers);

        // Act
        List<Long> result = userService.getFollowedUserIds(userId);

        // Assert
        assertEquals(followers.size(), result.size());
    }
}