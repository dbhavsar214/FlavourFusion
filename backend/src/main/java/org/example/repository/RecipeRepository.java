package org.example.repository;

import org.example.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {
    boolean save(Recipe recipe);
    Optional<Recipe> findById(Long id);
    List<Recipe> findAll();
    void update(Recipe recipe, Long id);
    void delete(Long id);
}
