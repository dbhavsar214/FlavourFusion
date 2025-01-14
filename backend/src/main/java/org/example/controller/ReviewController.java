package org.example.controller;

import java.util.List;

import org.example.model.Review;
import org.example.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody Review review) {
        System.out.println("Coming in create now");
        boolean success = reviewService.createReview(review);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Recipe created successfully!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\":\"Failed to create recipe.\"}");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateReview(@RequestBody Review review) {
        reviewService.updateReview(review);
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully");
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<Review>> getReview(@PathVariable int id) {
        List<Review> review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }
}
