package org.example.service;

import org.example.model.Review;
import org.example.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview() {
        // Arrange
        Review review = new Review();
        when(reviewRepository.createReview(review)).thenReturn(true);

        // Act
        boolean result = reviewService.createReview(review);

        // Assert
        assertTrue(result);
        verify(reviewRepository, times(1)).createReview(review);
    }

    @Test
    void testGetReviewById_NotFound() {
        // Arrange
        Integer reviewId = 1;
        when(reviewRepository.getReview(reviewId)).thenReturn(null);

        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            reviewService.getReviewById(reviewId);
        });
        assertEquals("Review not found", thrown.getReason());
    }

    @Test
    void testUpdateReview() {
        // Arrange
        Review review = new Review();
        doNothing().when(reviewRepository).updateReview(review);

        // Act
        reviewService.updateReview(review);

        // Assert
        verify(reviewRepository, times(1)).updateReview(review);
    }

    @Test
    void testDeleteReview() {
        // Arrange
        int reviewId = 1;
        doNothing().when(reviewRepository).deleteReview(reviewId);

        // Act
        reviewService.deleteReview(reviewId);

        // Assert
        verify(reviewRepository, times(1)).deleteReview(reviewId);
    }
}