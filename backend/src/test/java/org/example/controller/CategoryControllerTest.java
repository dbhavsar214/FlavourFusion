package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Category;
import org.example.model.Tag;
import org.example.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testCreateCategory() throws Exception {
        Category category = new Category();

        when(categoryService.createCategory(category)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateCategory_Failure() throws Exception {
        Category category = new Category();
        // Set properties for the category if needed

        when(categoryService.createCategory(category)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Failed to create category."));
    }

    @Test
    void testEditCategory() throws Exception {
        Long categoryId = 1L;
        Category category = new Category();
        category.setCategoryID(categoryId);

        mockMvc.perform(MockMvcRequestBuilders.put("/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(category)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Category updated successfully!"));

    }

    @Test
    void testGetCategoryById() throws Exception {
        Long categoryId = 1L;
        Category category = new Category();

        when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{categoryId}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetTagsForCategory() throws Exception {
        Long categoryId = 1L;
        List<Tag> tags = Arrays.asList(new Tag(), new Tag());

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{categoryId}/tags", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testRemoveCategory() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{categoryId}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Category removed successfully!"));

        verify(categoryService).removeCategory(categoryId);
    }
}