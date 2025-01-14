package org.example.repository;
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

class TagRepositoryImplTest {
    @InjectMocks
    private TagRepositoryImpl tagRepository;

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
        Tag tag = new Tag();
        tag.setTagName("Test Tag");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean result = tagRepository.save(tag);
    }

    @Test
    void testSave_Failure() throws SQLException {
        Tag tag = new Tag();
        tag.setTagName("Test Tag");
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        boolean result = tagRepository.save(tag);

    }

    @Test
    void testFindById_Found() throws SQLException {
        int id = 1;
        Tag expectedTag = new Tag();
        expectedTag.setTagID(id);
        expectedTag.setTagName("Test Tag");

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("tagID")).thenReturn(id);
        when(mockResultSet.getString("tagName")).thenReturn("Test Tag");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Tag result = tagRepository.findById(id);
    }

    @Test
    void testFindById_NotFound() throws SQLException {
        int id = 1;

        when(mockResultSet.next()).thenReturn(false);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Tag result = tagRepository.findById(id);

        assertNull(result);
    }

    @Test
    void testFindAll() throws SQLException {
        List<Tag> expectedTags = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setTagID(1);
        tag1.setTagName("Tag1");
        Tag tag2 = new Tag();
        tag2.setTagID(2);
        tag2.setTagName("Tag2");
        expectedTags.add(tag1);
        expectedTags.add(tag2);

        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("tagID")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("tagName")).thenReturn("Tag1").thenReturn("Tag2");
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        List<Tag> result = tagRepository.findAll();
    }

    @Test
    void testUpdate_Success() throws SQLException {
        Tag tag = new Tag();
        tag.setTagID(1);
        tag.setTagName("Updated Tag");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = tagRepository.update(tag);
    }

    @Test
    void testUpdate_Failure() throws SQLException {
        Tag tag = new Tag();
        tag.setTagID(1);
        tag.setTagName("Updated Tag");

        // Mock the behavior of executeUpdate
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        boolean result = tagRepository.update(tag);

    }


    @Test
    void testDelete_Failure() throws SQLException {
        int id = 1;
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        boolean result = tagRepository.delete(id);

    }
}