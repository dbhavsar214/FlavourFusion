package org.example.controller;

import org.example.model.Recipe;
import org.example.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping("/search")
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<Recipe> search(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> tag,
            @RequestParam Optional<String> ingredient) {
        return searchService.search(name.orElse(""), tag.orElse(""), ingredient.orElse(""));
    }
}
