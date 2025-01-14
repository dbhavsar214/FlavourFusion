package org.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.model.Recipe;
import org.example.model.Tag;
import org.example.model.User;
import org.example.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final EmailService emailService;

    public RecipeService(RecipeRepository recipeRepository, EmailService emailService) {
        this.recipeRepository = recipeRepository;
        this.emailService = emailService;
    }

    public boolean createRecipe(Recipe recipe) {
        boolean success= recipeRepository.save(recipe);
        if(success){
            List<String> tags = recipe.getTags();
            for(String tag : tags){
                Tag temp = new Tag();
                temp.setTagName(tag);
                TagRepository tagRepository = new TagRepositoryImpl();
                Tag check = tagRepository.findByName(tag);
                if(check==null)
                    tagRepository.save(temp);

                System.out.println("Now going to get list of followers");
                UserRepository userRepository = new UserRepositoryImpl();
                List<Long> followers = userRepository.getFollowerUserIds(recipe.getCreatorID());
                List<String> emails = new ArrayList<>();
                for(Long id: followers){
                    Optional<User> user=userRepository.getUserById(id);
                    user.ifPresent(value -> emails.add(value.getEmailAddress()));
                }
                System.out.println("Followers of "+recipe.getCreatorID()+" are: "+emails.toString());
                emailService.sendBulkMessage(emails,"New recipe created",
                        "A new recipe has been created by the user you are following, Recipe Name : "
                                +recipe.getName());
            }
        }
        return success;

    }

    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        return recipe.get();
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public void updateRecipe(Recipe recipe, Long id) {
        recipeRepository.update(recipe, id);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.delete(id);
    }
}
