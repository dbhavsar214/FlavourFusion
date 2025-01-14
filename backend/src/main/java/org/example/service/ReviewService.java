package org.example.service;

import java.util.List;

import org.example.model.Review;
import org.example.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public boolean createReview(Review review) {
        return reviewRepository.createReview(review);
    }

    public List<Review> getReviewById(Integer recipeid) {
        List<Review> review = reviewRepository.getReview(recipeid);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
        return review;
    }

    // public List<Review> getAllReviews() {
    // return reviewRepository.findAll();
    // }

    public void updateReview(Review review) {
        reviewRepository.updateReview(review);
    }

    public void deleteReview(int id) {
        reviewRepository.deleteReview(id);
    }
}
