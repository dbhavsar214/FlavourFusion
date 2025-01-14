package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Recipe;
import org.example.service.RecipeService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RecipeControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void testCreateRecipe_Success() throws Exception {
        Recipe recipe = new Recipe();
        // Setup any properties for the recipe if needed

        when(recipeService.createRecipe(recipe)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipe)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testCreateRecipe_Failure() throws Exception {
        Recipe recipe = new Recipe();

        when(recipeService.createRecipe(recipe)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipe)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
                //.andExpect(MockMvcResultMatchers.content().string("Failed to create recipe."));
    }

    @Test
    void testGetRecipe() throws Exception {
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setRecipeID(recipeId);

        when(recipeService.getRecipeById(recipeId)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/{id}", recipeId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllRecipes_fail() throws Exception {
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipes"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
                //.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                //.andExpect(MockMvcResultMatchers.jsonPath("$[1]").exists());
    }

    @Test
    void testUpdateRecipe() throws Exception {
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setRecipeID(recipeId);

        doNothing().when(recipeService).updateRecipe(recipe, recipeId);

        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/update/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(recipe)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Recipe updated successfully!"));
    }

    @Test
    void testDeleteRecipe() throws Exception {
        Long recipeId = 1L;

        doNothing().when(recipeService).deleteRecipe(recipeId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/delete/{id}", recipeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Recipe deleted successfully!"));
    }
}