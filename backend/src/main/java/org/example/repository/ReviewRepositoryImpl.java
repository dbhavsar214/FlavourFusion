package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    @Override
    public boolean createReview(Review review) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO reviews (recipeID, description, author, rating) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, review.getRecipeID());
                statement.setString(2, review.getDescription());
                statement.setString(3, review.getAuthor());
                statement.setInt(4, review.getRating());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
        // System.out.println(review.toString());
    }

    @Override
    public void updateReview(Review review) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "UPDATE reviews SET recipeID = ?, description = ?, author = ?, rating = ? WHERE reviewID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, review.getRecipeID());
                statement.setString(2, review.getDescription());
                statement.setString(3, review.getAuthor());
                statement.setInt(4, review.getRating());
                statement.setInt(5, review.getReviewID());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReview(int reviewID) {
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "DELETE FROM reviews WHERE reviewID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, reviewID);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Review> getReview(int recipeID) {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "SELECT * FROM reviews WHERE recipeID = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, recipeID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Review review = new Review();
                        review.setReviewID(resultSet.getInt("reviewID"));
                        review.setRecipeID(resultSet.getInt("recipeID"));
                        review.setDescription(resultSet.getString("description"));
                        review.setAuthor(resultSet.getString("author"));
                        review.setRating(resultSet.getInt("rating"));
                        reviews.add(review);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
