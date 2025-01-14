package org.example.repository;

import org.example.model.Recipe;
import org.example.model.User;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> login(String username, String password) {
        String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = mapRowToUser(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapRowToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE userID = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = mapRowToUser(resultSet);
                user.setRecipes(getRecipesByUserId(id)); // Fetch recipes for the user
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE userName = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = mapRowToUser(resultSet);
                user.setRecipes(getRecipesByUserId(user.getUserID())); // Fetch recipes for the user
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE emailAddress = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = mapRowToUser(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public void update(User user, Long id) {
        StringBuilder sql = new StringBuilder("UPDATE users SET userName = ?, firstName = ?, lastName = ?, emailAddress = ?, bio = ?");

        // Check if password is provided and append to the SQL query if it is
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            sql.append(", password = ?");
        }
        sql.append(" WHERE userID = ?");

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmailAddress());
            preparedStatement.setString(5, user.getBio());

            // If password is provided, set it in the prepared statement
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                preparedStatement.setString(6, user.getPassword());
                preparedStatement.setLong(7, id);
            } else {
                preparedStatement.setLong(6, id);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE userID = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void followUser(Long userId, Long followerId) {
        String sql = "INSERT INTO followers (user_id, follower_id) VALUES (?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, followerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unfollowUser(Long userId, Long followerId) {
        String sql = "DELETE FROM followers WHERE user_id = ? AND follower_id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, followerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Long> getFollowerUserIds(Long userId) {
        List<Long> followerUserIds = new ArrayList<>();
        String sql = "SELECT follower_id FROM followers WHERE user_id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                followerUserIds.add(resultSet.getLong("follower_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followerUserIds;
    }

    @Override
    public List<Long> getFollowedUserIds(Long userId) {
        List<Long> followedUserIds = new ArrayList<>();
        String sql = "SELECT user_id FROM followers WHERE follower_id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                followedUserIds.add(resultSet.getLong("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followedUserIds;
    }


    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (userName, firstName, lastName, emailAddress, password, bio) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmailAddress());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getBio());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User mapRowToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserID(resultSet.getLong("userID"));
        user.setUserName(resultSet.getString("userName"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmailAddress(resultSet.getString("emailAddress"));
        user.setPassword(resultSet.getString("password"));
        user.setBio(resultSet.getString("bio"));
        user.setUserType(resultSet.getString("userType"));
        return user;
    }

    @Override
    public List<Recipe> getRecipesByUserId(Long userId) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes WHERE creatorID = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipe = mapRowToRecipe(resultSet);
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private Recipe mapRowToRecipe(ResultSet resultSet) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setRecipeID(resultSet.getLong("recipeID"));
        recipe.setName(resultSet.getString("name"));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setTags(List.of(resultSet.getString("tags").split(",")));
        return recipe;
    }
}
