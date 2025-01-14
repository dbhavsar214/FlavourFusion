package org.example.service;

import org.example.model.Category;
import org.example.model.Tag;
import org.example.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        // Arrange
        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(true);

        // Act
        boolean result = categoryService.createCategory(category);

        // Assert
        assertTrue(result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testEditCategory() {
        // Arrange
        Category category = new Category();
        doNothing().when(categoryRepository).update(category);

        // Act
        categoryService.editCategory(category);

        // Assert
        verify(categoryRepository, times(1)).update(category);
    }

    @Test
    void testGetCategoryById() {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(category);

        // Act
        Category result = categoryService.getCategoryById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(category, result);
    }

    @Test
    void testGetTagsForCategory() {
        // Arrange
        Long categoryId = 1L;
        List<Tag> tags = new ArrayList<>();
        when(categoryRepository.findTagsByCategoryId(categoryId)).thenReturn(tags);

        // Act
        List<Tag> result = categoryService.getTagsForCategory(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(tags, result);
    }

    @Test
    void testRemoveCategory() {
        // Arrange
        Long categoryId = 1L;
        doNothing().when(categoryRepository).deleteById(categoryId);

        // Act
        categoryService.removeCategory(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}