package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Other methods for user-related operations

    @PostMapping("/signin")
    public String signIn(@RequestParam String userName, @RequestParam String password) {
        return userService.login(userName, password);
    }

    @GetMapping("/getall")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        System.out.println("Entering here");
        return userService.getUserById(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}/update")
    public void updateUser(@RequestBody User user, @PathVariable Long userId) {
        userService.updateUser(user, userId);
    }

    @PostMapping("/{userId}/follow/{followerId}")
    public ResponseEntity<String> followUser(@PathVariable Long userId, @PathVariable Long followerId) {
        userService.followUser(userId, followerId);
        return new ResponseEntity<>("User followed successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/unfollow/{followerId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long userId, @PathVariable Long followerId) {
        userService.unfollowUser(userId, followerId);
        return new ResponseEntity<>("User unfollowed successfully", HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<Long>> getFollowers(@PathVariable Long userId) {
        List<Long> followers = userService.getFollowers(userId);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followed")
    public ResponseEntity<List<Long>> getFollowedUsers(@PathVariable Long userId) {
        List<Long> followedUsers = userService.getFollowedUserIds(userId);
        return new ResponseEntity<>(followedUsers, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User currentUser = (User) authentication.getPrincipal();
        System.out.println("Authenticated user: " + currentUser);

        // Fetch followed user IDs and set them in the current user object
        List<Long> followedUserIds = userService.getFollowedUserIds(currentUser.getUserID());
        currentUser.setFollowedUsers(followedUserIds);
        currentUser.setRecipes(userService.getRecipesByUserId(currentUser.getUserID()));

        return ResponseEntity.ok(currentUser);
    }


    @GetMapping("/{userId}/profile")
    public ResponseEntity<User> getUserProfile(@PathVariable Long userId) {
        System.out.println("Fetching profile for user ID: " + userId);
        User userProfile = userService.getUserById(userId);
        if (userProfile == null) {
            System.out.println("User not found for ID: " + userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Long> followers = userService.getFollowers(userId);
        userProfile.setFollowers(followers);
        return ResponseEntity.ok(userProfile);
    }
}
