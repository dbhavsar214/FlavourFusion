package org.example.repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.junit.jupiter.api.Assertions.*;
@SpringJUnitConfig
class UserRepositoryImplTest {
    @InjectMocks
    private UserRepositoryImpl userRepository;

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
    void testFindByEmail_UserExists() throws SQLException {
        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setUserID(1L);
        expectedUser.setUserName("testuser");
        expectedUser.setEmailAddress(email);
        expectedUser.setPassword("password");
        expectedUser.setBio("bio");
        expectedUser.setUserType("type");

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("userID")).thenReturn(1L);
        when(mockResultSet.getString("userName")).thenReturn("testuser");
        when(mockResultSet.getString("emailAddress")).thenReturn(email);
        when(mockResultSet.getString("password")).thenReturn("password");
        when(mockResultSet.getString("bio")).thenReturn("bio");
        when(mockResultSet.getString("userType")).thenReturn("type");

        Optional<User> result = userRepository.findByEmail(email);
    }

    @Test
    void testFindByEmail_UserNotFound() throws SQLException {
        String email = "nonexistent@example.com";

        when(mockResultSet.next()).thenReturn(false);

        Optional<User> result = userRepository.findByEmail(email);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveUser() throws SQLException {
        User user = new User();
        user.setUserName("testuser");
        user.setEmailAddress("test@example.com");
        user.setPassword("password");
        user.setBio("bio");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        User savedUser = userRepository.save(user);

    }

    @Test
    void testLogin_Success() throws SQLException {
        String username = "testuser";
        String password = "password";
        User expectedUser = new User();
        expectedUser.setUserID(1L);
        expectedUser.setUserName(username);
        expectedUser.setEmailAddress("test@example.com");
        expectedUser.setPassword(password);
        expectedUser.setBio("bio");
        expectedUser.setUserType("type");

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("userID")).thenReturn(1L);
        when(mockResultSet.getString("userName")).thenReturn(username);
        when(mockResultSet.getString("emailAddress")).thenReturn("test@example.com");
        when(mockResultSet.getString("password")).thenReturn(password);
        when(mockResultSet.getString("bio")).thenReturn("bio");
        when(mockResultSet.getString("userType")).thenReturn("type");

        Optional<User> result = userRepository.login(username, password);
    }

    @Test
    void testLogin_Failure() throws SQLException {
        String username = "testuser";
        String password = "wrongpassword";

        when(mockResultSet.next()).thenReturn(false);

        Optional<User> result = userRepository.login(username, password);

        assertFalse(result.isPresent());
    }

    @Test
    void testFollowUser() throws SQLException {
        Long userId = 1L;
        Long followerId = 2L;

        userRepository.followUser(userId, followerId);

    }

    @Test
    void testUnfollowUser() throws SQLException {
        Long userId = 1L;
        Long followerId = 2L;

        userRepository.unfollowUser(userId, followerId);

    }

    @Test
    void testGetFollowers() throws SQLException {
        Long userId = 1L;
        User follower = new User();
        follower.setUserID(2L);
        follower.setUserName("follower");

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("user_id")).thenReturn(2L);
        when(mockResultSet.getString("username")).thenReturn("follower");
    }

    @Test
    void testGetFollowedUsers() throws SQLException {
        Long userId = 1L;
        User followedUser = new User();
        followedUser.setUserID(2L);
        followedUser.setUserName("followed");

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getLong("user_id")).thenReturn(2L);
        when(mockResultSet.getString("username")).thenReturn("followed");

        List<Long> followedUsers = userRepository.getFollowedUserIds(userId);
    }


    @Test
    void testGetUserById() {
        User user = new User(1L, "user1", "password1", "email1@example.com");
        Optional<User> result = userRepository.getUserById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testUpdate() {
        User oldUser = new User(1L, "olduser", "password", "email@example.com");
        User updatedUser = new User(1L, "newuser", "newpassword", "newemail@example.com");
        userRepository.update(updatedUser, 1L);
        Optional<User> result = userRepository.getUserById(1L);

    }

    @Test
    void testDelete() {
        User user = new User(1L, "user1", "password1", "email1@example.com");
        userRepository.delete(1L);
        Optional<User> result = userRepository.getUserById(1L);

        assertFalse(result.isPresent());
    }
}