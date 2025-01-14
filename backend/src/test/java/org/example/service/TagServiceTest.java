package org.example.service;

import org.example.model.Tag;
import org.example.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTag() {
        // Arrange
        Tag tag = new Tag();
        when(tagRepository.save(tag)).thenReturn(true);

        // Act
        boolean result = tagService.createTag(tag);

        // Assert
        assertTrue(result);
        verify(tagRepository, times(1)).save(tag);
    }

    @Test
    void testGetTagById() {
        // Arrange
        int tagId = 1;
        Tag tag = new Tag();
        when(tagRepository.findById(tagId)).thenReturn(tag);

        // Act
        Tag result = tagService.getTagById(tagId);

        // Assert
        assertNotNull(result);
        assertEquals(tag, result);
    }

    @Test
    void testGetAllTags() {
        // Arrange
        List<Tag> tags = new ArrayList<>();
        when(tagRepository.findAll()).thenReturn(tags);

        // Act
        List<Tag> result = tagService.getAllTags();

        // Assert
        assertNotNull(result);
        assertEquals(tags, result);
    }

    @Test
    void testUpdateTag() {
        // Arrange
        Tag tag = new Tag();
        when(tagRepository.update(tag)).thenReturn(true);

        // Act
        boolean result = tagService.updateTag(tag);

        // Assert
        assertTrue(result);
        verify(tagRepository, times(1)).update(tag);
    }

    @Test
    void testDeleteTag() {
        // Arrange
        int tagId = 1;
        when(tagRepository.delete(tagId)).thenReturn(true);

        // Act
        boolean result = tagService.deleteTag(tagId);

        // Assert
        assertTrue(result);
        verify(tagRepository, times(1)).delete(tagId);
    }
}