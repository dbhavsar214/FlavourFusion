package org.example.repository;

import java.util.List;

import org.example.model.Review;

public interface ReviewRepository {
    boolean createReview(Review review);
    void updateReview(Review review);
    void deleteReview(int reviewID);
    List<Review> getReview(int recipeId);
}
