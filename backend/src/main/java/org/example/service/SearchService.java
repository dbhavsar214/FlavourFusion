package org.example.service;

import org.example.model.Recipe;
import org.example.repository.SearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<Recipe> search(String name, String tag, String ingredient) {
        if (name.isEmpty() && tag.isEmpty() && ingredient.isEmpty()) {
            return searchRepository.findAll();
        }
        return searchRepository.search(name, tag, ingredient);
    }
}
