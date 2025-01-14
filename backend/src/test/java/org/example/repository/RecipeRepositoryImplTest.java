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
class RecipeRepositoryImplTest {

    @InjectMocks
    private RecipeRepositoryImpl recipeRepository;

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
    void testSave() throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setRecipeID(1L);
        recipe.setName("Test Recipe");
        recipe.setIngredients(List.of("ingredient1", "ingredient2"));
        recipe.setDescription("Delicious");
        recipe.setCookingTime(30);
        recipe.setCreationDate(java.time.LocalDateTime.now());
        recipe.setLastUpdated(java.time.LocalDateTime.now());
        recipe.setTags(List.of("tag1", "tag2"));
        recipe.setImages(new ArrayList<>());

        String sql = "INSERT INTO recipes (creatorID, recipeID, name, ingredients, description, cookingTime, creationDate, lastUpdated, tags, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
    }

    @Test
    void testFindById() throws SQLException {
        long id = 1L;
        String sql = "SELECT * FROM recipes WHERE recipeID = ?";

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        Recipe recipe = new Recipe();
        recipe.setRecipeID(id);
        when(mockResultSet.getLong("recipeID")).thenReturn(id);
        when(mockResultSet.getString("name")).thenReturn("Test Recipe");
        when(mockResultSet.getString("ingredients")).thenReturn("ingredient1,ingredient2");
        when(mockResultSet.getString("description")).thenReturn("Delicious");
        when(mockResultSet.getInt("cookingTime")).thenReturn(30);
        when(mockResultSet.getTimestamp("creationDate")).thenReturn(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        when(mockResultSet.getTimestamp("lastUpdated")).thenReturn(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        when(mockResultSet.getString("tags")).thenReturn("tag1,tag2");
        when(mockResultSet.getBytes("images")).thenReturn(new byte[]{});
    }

    @Test
    void testFindById_NotFound() throws SQLException {
        long id = 1L;

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Optional<Recipe> result = recipeRepository.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() throws SQLException {
        String sql = "SELECT * FROM recipes";

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        Recipe recipe = new Recipe();
        recipe.setRecipeID(1L);
        when(mockResultSet.getLong("recipeID")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("Test Recipe");
        when(mockResultSet.getString("ingredients")).thenReturn("ingredient1,ingredient2");
        when(mockResultSet.getString("description")).thenReturn("Delicious");
        when(mockResultSet.getInt("cookingTime")).thenReturn(30);
        when(mockResultSet.getTimestamp("creationDate")).thenReturn(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        when(mockResultSet.getTimestamp("lastUpdated")).thenReturn(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        when(mockResultSet.getString("tags")).thenReturn("tag1,tag2");
        when(mockResultSet.getBytes("images")).thenReturn(new byte[]{});
    }

    @Test
    void testUpdate() throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setName("Updated Recipe");
        recipe.setIngredients(List.of("updatedIngredient1", "updatedIngredient2"));
        recipe.setDescription("Updated Description");
        recipe.setCookingTime(45);
        recipe.setLastUpdated(java.time.LocalDateTime.now());
        recipe.setTags(List.of("updatedTag1", "updatedTag2"));
        recipe.setImages(new ArrayList<>());

        long id = 1L;
        String sql = "UPDATE recipes SET name = ?, ingredients = ?, description = ?, cookingTime = ?, lastUpdated = ?, tags = ?, images = ? WHERE recipeID = ?";
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        recipeRepository.update(recipe, id);
    }

    @Test
    void testDelete() throws SQLException {
        long id = 1L;
        String sql = "DELETE FROM recipes WHERE recipeID = ?";
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        recipeRepository.delete(id);
    }
}