package org.example.service;

import org.example.model.Tag;
import org.example.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public boolean createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag getTagById(int id) {
        return tagRepository.findById(id);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public boolean updateTag(Tag tag) {
        return tagRepository.update(tag);
    }

    public boolean deleteTag(int id) {
        return tagRepository.delete(id);
    }
}
