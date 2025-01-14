package org.example.service;

import org.example.model.Recipe;
import org.example.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecipeById_Found() {
        // Arrange
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // Act
        Recipe result = recipeService.getRecipeById(recipeId);

        // Assert
        assertNotNull(result);
        assertEquals(recipe, result);
    }

    @Test
    void testGetRecipeById_NotFound() {
        // Arrange
        Long recipeId = 1L;
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            recipeService.getRecipeById(recipeId);
        });
        assertEquals("Recipe not found", thrown.getReason());
    }

    @Test
    void testGetAllRecipes() {
        // Arrange
        List<Recipe> recipes = new ArrayList<>();
        when(recipeRepository.findAll()).thenReturn(recipes);

        // Act
        List<Recipe> result = recipeService.getAllRecipes();

        // Assert
        assertNotNull(result);
        assertEquals(recipes, result);
    }

    @Test
    void testUpdateRecipe() {
        // Arrange
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        doNothing().when(recipeRepository).update(recipe, recipeId);

        // Act
        recipeService.updateRecipe(recipe, recipeId);

        // Assert
        verify(recipeRepository, times(1)).update(recipe, recipeId);
    }

    @Test
    void testDeleteRecipe() {
        // Arrange
        Long recipeId = 1L;
        doNothing().when(recipeRepository).delete(recipeId);

        // Act
        recipeService.deleteRecipe(recipeId);

        // Assert
        verify(recipeRepository, times(1)).delete(recipeId);
    }
}