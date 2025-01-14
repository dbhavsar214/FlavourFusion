package org.example.service;

import org.example.model.Category;
import org.example.model.Tag;
import org.example.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public boolean createCategory(Category category) {
        // Implement logic to create category
        return categoryRepository.save(category);
    }

    public void editCategory(Category category) {
        // Implement logic to edit category
        categoryRepository.update(category);
    }

    public void editTags(Long categoryId, List<Tag> tags) {
        // Implement logic to edit tags for a category
        categoryRepository.updateTags(categoryId, tags);
    }

    public Category getCategoryById(Long id) {
        // Implement logic to fetch category by ID
        return categoryRepository.findById(id);
    }

    public List<Tag> getTagsForCategory(Long categoryId) {
        // Implement logic to fetch tags for a category
        return categoryRepository.findTagsByCategoryId(categoryId);
    }

    public void removeCategory(Long id) {
        // Implement logic to remove category
        categoryRepository.deleteById(id);
    }
}
