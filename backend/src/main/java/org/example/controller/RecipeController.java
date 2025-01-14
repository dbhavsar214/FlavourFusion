package org.example.controller;

import org.example.model.Recipe;
import org.example.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        System.out.println("In Controller: "+ recipe.toString());
        boolean success = recipeService.createRecipe(recipe);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Recipe created successfully!\"}");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Failed to create recipe.\"}");
        }
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/all")
    public List<Recipe> getAllRecipes() {
        System.out.println("COMING IN GET");
        return recipeService.getAllRecipes();
    }

    @PutMapping("/update/{id}")
    public String updateRecipe(@RequestBody Recipe recipe, @PathVariable Long id) {
        recipeService.updateRecipe(recipe, id);
        return "Recipe updated successfully!";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return "Recipe deleted successfully!";
    }
}
