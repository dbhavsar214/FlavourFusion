package org.example.repository;

import org.example.model.User;
import org.example.model.Recipe;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> login(String username, String password);

    List<User> findAll();

    Optional<User> getUserById(Long id);

    void update(User user, Long id);

    void delete(Long id);

    void followUser(Long userId, Long followerId);

    void unfollowUser(Long userId, Long followerId);

    List<Long> getFollowerUserIds(Long userId);

    List<Long> getFollowedUserIds(Long userId);


    List<Recipe> getRecipesByUserId(Long userId);

    Optional<User> findByUsername(String username);

    User save(User user);

    Optional<User> findByEmail(String email);
}
