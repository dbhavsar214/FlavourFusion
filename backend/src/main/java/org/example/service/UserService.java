package org.example.service;

import org.example.model.User;
import org.example.model.Recipe;
import org.example.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // Other methods for user-related operations

    public String login(String username, String password) {
        Optional<User> user = userRepository.login(username, password);
        if (user.isPresent()) {
            return "Login Successful";
        } else {
            return "Login Failed";
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.getUserById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user.get();
    }

    public void updateUser(User user, Long id) {
        Optional<User> existingUserOpt = userRepository.getUserById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (user.getFirstName() != null) {
                existingUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                existingUser.setLastName(user.getLastName());
            }
            if (user.getBio() != null) {
                existingUser.setBio(user.getBio());
            }

            // Always update the required fields
            existingUser.setUserName(user.getUserName());
            existingUser.setEmailAddress(user.getEmailAddress());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.update(existingUser, id);
        }
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public void followUser(Long userId, Long followerId) {
        userRepository.followUser(userId, followerId);
    }

    public void unfollowUser(Long userId, Long followerId) {
        userRepository.unfollowUser(userId, followerId);
    }

    public List<Long> getFollowers(Long userId) {
        return userRepository.getFollowerUserIds(userId);
    }

    public List<Long> getFollowedUserIds(Long userId) {
        return userRepository.getFollowedUserIds(userId);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public List<Recipe> getRecipesByUserId(Long userId)
    {
        return userRepository.getRecipesByUserId(userId);
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
