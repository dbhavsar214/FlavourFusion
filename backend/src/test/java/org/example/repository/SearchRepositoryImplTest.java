package org.example.repository;

import org.example.model.Recipe;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class SearchRepositoryImplTest {
    @InjectMocks
    private SearchRepositoryImpl searchRepository;

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
    void testSearchByName_Found() throws SQLException {
        String name = "test";
        String sql = "SELECT * FROM recipes WHERE name LIKE ?";

        Recipe recipe = new Recipe();
        recipe.setRecipeID(1L);
        recipe.setName("Test Recipe");

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("recipeID")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("Test Recipe");

        Optional<List<Recipe>> result = searchRepository.searchByName(name);
    }

    @Test
    void testSearchByTag_Found() throws SQLException {
        String tag = "vegan";
        String sql = "SELECT * FROM recipes WHERE tags LIKE ?";

        Recipe recipe = new Recipe();
        recipe.setRecipeID(1L);
        recipe.setTags(List.of("vegan"));

        when(mockResultSet.next()).thenReturn(true).thenReturn(true);
        when(mockResultSet.getLong("recipeID")).thenReturn(1L);
        when(mockResultSet.getString("tags")).thenReturn("vegan");

        Optional<List<Recipe>> result = searchRepository.searchByTag(tag);
    }
}