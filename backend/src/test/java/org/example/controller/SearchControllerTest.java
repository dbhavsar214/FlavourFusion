package org.example.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Recipe;
import org.example.service.SearchService;
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
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SearchControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SearchService searchService;

    @InjectMocks
    private SearchController searchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    void testSearchByName_fail() throws Exception {
        String name = "Pasta";
        Recipe recipe1 = new Recipe(); // setup properties as needed
        Recipe recipe2 = new Recipe(); // setup properties as needed
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        //when(searchService.searchByName(name)).thenReturn(Optional.of(recipes));

        mockMvc.perform(MockMvcRequestBuilders.get("/search/byname")
                        .param("name", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testSearchByName_NotFound() throws Exception {
        String name = "NonExistentRecipe";

        //when(searchService.searchByName(name)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/search/byname")
                        .param("name", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testSearchByTag_fail() throws Exception {
        String tag = "Vegan";
        Recipe recipe1 = new Recipe(); // setup properties as needed
        Recipe recipe2 = new Recipe(); // setup properties as needed
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/bytag")
                        .param("tag", tag)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testSearchByTag_NotFound() throws Exception {
        String tag = "NonExistentTag";

        mockMvc.perform(MockMvcRequestBuilders.get("/search/bytag")
                        .param("tag", tag)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testSearchByCategory_NotFound()throws Exception {
        String category = "Dessert";
        Recipe recipe1 = new Recipe(); // setup properties as needed
        Recipe recipe2 = new Recipe(); // setup properties as needed
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/bycategory")
                        .param("category", category)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}