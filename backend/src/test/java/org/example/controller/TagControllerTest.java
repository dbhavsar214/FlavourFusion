package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Tag;
import org.example.service.TagService;
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

class TagControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }


    @Test
    void testCreateTag_Failure() throws Exception {
        Tag tag = new Tag();
        when(tagService.createTag(tag)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/tags/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetTagById_NotFound() throws Exception {
        int tagId = 1;

        when(tagService.getTagById(tagId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags/{id}", tagId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetAllTags() throws Exception {
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        List<Tag> tags = Arrays.asList(tag1, tag2);

        mockMvc.perform(MockMvcRequestBuilders.get("/tags/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testDeleteTag() throws Exception {
        int tagId = 1;

        when(tagService.deleteTag(tagId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/delete/{id}", tagId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Tag deleted successfully"));
    }

    @Test
    void testDeleteTag_Failure() throws Exception {
        int tagId = 1;

        when(tagService.deleteTag(tagId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/delete/{id}", tagId))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Failed to delete tag"));
    }
}