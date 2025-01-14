package org.example.controller;

import org.example.model.Category;
import org.example.model.Tag;
import org.example.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public String createCategory(@RequestBody Category category) {
        boolean success = categoryService.createCategory(category);
        if (success) {
            return "Category created successfully!";
        } else {
            return "Failed to create category.";
        }
    }

    @PutMapping("/{categoryId}")
    public String editCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        category.setCategoryID(categoryId);
        categoryService.editCategory(category);
        return "Category updated successfully!";
    }

    @PutMapping("/{categoryId}/tags")
    public String editTags(@PathVariable Long categoryId, @RequestBody List<Tag> tags) {
        categoryService.editTags(categoryId, tags);
        return "Tags updated successfully for category ID: " + categoryId;
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/{categoryId}/tags")
    public List<Tag> getTagsForCategory(@PathVariable Long categoryId) {
        return categoryService.getTagsForCategory(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public String removeCategory(@PathVariable Long categoryId) {
        categoryService.removeCategory(categoryId);
        return "Category removed successfully!";
    }
}
