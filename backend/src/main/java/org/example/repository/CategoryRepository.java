package org.example.repository;

import org.example.model.Category;
import org.example.model.Tag;

import java.util.List;

public interface CategoryRepository {

    boolean save(Category category);

    void update(Category category);

    boolean updateTags(Long categoryId, List<Tag> tags);

    Category findById(Long id);

    List<Tag> findTagsByCategoryId(Long categoryId);

    void deleteById(Long id);
}
