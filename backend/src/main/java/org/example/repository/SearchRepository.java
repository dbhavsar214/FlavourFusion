package org.example.repository;

import org.example.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface SearchRepository {

    List<Recipe> search(String name, String tag, String ingredient);
    Optional<List<Recipe>> searchByName(String name);
    Optional<List<Recipe>> searchByTag(String tag);
    Optional<List<Recipe>> searchByIngredient(String ingredient);
    List<Recipe> findAll();
}
