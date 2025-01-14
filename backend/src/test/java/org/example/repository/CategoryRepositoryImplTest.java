package org.example.repository;

import org.example.model.Category;
import org.example.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryRepositoryImplTest {
    @InjectMocks
    private CategoryRepositoryImpl categoryRepository;

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
    }

    @Test
    void testSave_Success() throws SQLException {
        Category category = new Category();
        category.setCategoryName("Test Category");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = categoryRepository.save(category);
    }

    @Test
    void testUpdate() throws SQLException {
        Category category = new Category();
        category.setCategoryID(1L);
        category.setCategoryName("Updated Category");

        String sql = "UPDATE categories SET categoryName = ? WHERE categoryID = ?";

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        categoryRepository.update(category);
    }

    @Test
    void testUpdateTags_Success() throws SQLException {
        Long categoryId = 1L;
        List<Tag> tags = List.of(new Tag("Tag1"), new Tag("Tag2"));

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = categoryRepository.updateTags(categoryId, tags);
    }

    @Test
    void testUpdateTags_Failure() throws SQLException {
        Long categoryId = 1L;
        List<Tag> tags = List.of(new Tag("Tag1"), new Tag("Tag2"));

        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        boolean result = categoryRepository.updateTags(categoryId, tags);

    }

    @Test
    void testFindById_Found() throws SQLException {
        Long id = 1L;
        Category expectedCategory = new Category();
        expectedCategory.setCategoryID(id);
        expectedCategory.setCategoryName("Test Category");

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("categoryID")).thenReturn(id);
        when(mockResultSet.getString("categoryName")).thenReturn("Test Category");

        Category result = categoryRepository.findById(id);
    }

    @Test
    void testFindById_NotFound() throws SQLException {
        Long id = 1L;
        when(mockResultSet.next()).thenReturn(false);
        Category result = categoryRepository.findById(id);
        assertNull(result);
    }

    @Test
    void testDeleteById() throws SQLException {
        Long id = 1L;
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        categoryRepository.deleteById(id);
    }
}