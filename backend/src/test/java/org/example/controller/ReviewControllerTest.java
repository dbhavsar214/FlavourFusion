package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Review;
import org.example.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReviewControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void testCreateReview() throws Exception {
        Review review = new Review();

        mockMvc.perform(MockMvcRequestBuilders.post("/reviews/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(review)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testUpdateReview() throws Exception {
        Review review = new Review();
        // Setup any properties for the review if needed

        doNothing().when(reviewService).updateReview(review);

        mockMvc.perform(MockMvcRequestBuilders.put("/reviews/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(review)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Review updated successfully"));
    }

    @Test
    void testDeleteReview() throws Exception {
        int reviewId = 1;

        doNothing().when(reviewService).deleteReview(reviewId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/reviews/delete/{id}", reviewId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Review deleted successfully"));
    }

    @Test
    void testGetReview() throws Exception {
        int reviewId = 1;
        Review review = new Review();
        review.setReviewID(reviewId);

        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/get/{id}", reviewId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}