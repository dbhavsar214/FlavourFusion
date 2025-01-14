package org.example.repository;

import org.example.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class ReviewRepositoryImplTest {
    @InjectMocks
    private ReviewRepositoryImpl reviewRepository;

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
    void testCreateReview_fail() throws SQLException {
        Review review = new Review();
        review.setRecipeID(1);
        review.setDescription("Delicious!");
        review.setAuthor("Chef John");
        review.setRating(5);

        // Mock the behavior of executeUpdate
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = reviewRepository.createReview(review);

        assertFalse(result);
    }

    @Test
    void testUpdateReview() throws SQLException {
        Review review = new Review();
        review.setReviewID(1);
        review.setRecipeID(2);
        review.setDescription("Updated review");
        review.setAuthor("Chef Alice");
        review.setRating(4);

        // Mock the behavior of executeUpdate
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        reviewRepository.updateReview(review);
    }

    @Test
    void testDeleteReview() throws SQLException {
        int reviewID = 1;
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        reviewRepository.deleteReview(reviewID);
    }

    @Test
    void testGetReview_Found() throws SQLException {
        int reviewID = 1;

        Review expectedReview = new Review();
        expectedReview.setReviewID(reviewID);
        expectedReview.setRecipeID(2);
        expectedReview.setDescription("Great recipe!");
        expectedReview.setAuthor("Chef Bob");
        expectedReview.setRating(5);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("reviewID")).thenReturn(expectedReview.getReviewID());
        when(mockResultSet.getInt("recipeID")).thenReturn(expectedReview.getRecipeID());
        when(mockResultSet.getString("description")).thenReturn(expectedReview.getDescription());
        when(mockResultSet.getString("author")).thenReturn(expectedReview.getAuthor());
        when(mockResultSet.getInt("rating")).thenReturn(expectedReview.getRating());
    }
}