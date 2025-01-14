package org.example.repository;

import java.util.List;

import org.example.model.Tag;

public interface TagRepository {

    boolean save(Tag tag);

    public Tag findByName(String tagName);
    
    Tag findById(int id);

    List<Tag> findAll();

    boolean update(Tag tag);

    boolean delete(int id);
}
