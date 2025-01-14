package org.example.repository;

import org.example.model.Recipe;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class SearchRepositoryImpl implements SearchRepository {

    public SearchRepositoryImpl() {
    }

    @Override
    public List<Recipe> search(String name, String tags, String ingredients) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM recipes WHERE 1=1");
        List<String> parameters = new ArrayList<>();

        if (!name.isEmpty()) {
            sqlBuilder.append(" AND name LIKE ?");
            parameters.add("%" + name + "%");
        }

        if (!tags.isEmpty()) {
            String[] tagArray = tags.split(",");
            for (String tag : tagArray) {
                sqlBuilder.append(" AND tags LIKE ?");
                parameters.add("%" + tag.trim() + "%");
            }
        }

        if (!ingredients.isEmpty()) {
            String[] ingredientArray = ingredients.split(",");
            for (String ingredient : ingredientArray) {
                sqlBuilder.append(" AND ingredients LIKE ?");
                parameters.add("%" + ingredient.trim() + "%");
            }
        }

        String sql = sqlBuilder.toString();
        List<Recipe> recipes = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setString(i + 1, parameters.get(i));
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Recipe recipe = mapRowToRecipe(rs);
                    recipes.add(recipe);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    @Override
    public Optional<List<Recipe>> searchByName(String name) {
        return searchHelper("name LIKE ?", name);
    }

    @Override
    public Optional<List<Recipe>> searchByTag(String tag) {
        return searchHelper("tags LIKE ?", tag);
    }

    @Override
    public Optional<List<Recipe>> searchByIngredient(String ingredient) {
        return searchHelper("ingredients LIKE ?", ingredient);
    }

    private Optional<List<Recipe>> searchHelper(String condition, String value) {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes WHERE " + condition;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + value + "%");
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Recipe recipe = mapRowToRecipe(rs);
                    recipes.add(recipe);
                }
            }
            return Optional.of(recipes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Recipe> findAll() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Recipe recipe = mapRowToRecipe(rs);
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private Recipe mapRowToRecipe(ResultSet resultSet) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setCreatorID(resultSet.getLong("creatorID"));
        recipe.setRecipeID(resultSet.getLong("recipeID"));
        recipe.setName(resultSet.getString("name"));
        recipe.setIngredients(Optional.ofNullable(resultSet.getString("ingredients")).map(ingredients -> List.of(ingredients.split(","))).orElse(List.of()));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setCookingTime(resultSet.getInt("cookingTime"));
        recipe.setCreationDate(resultSet.getTimestamp("creationDate").toLocalDateTime());
        recipe.setLastUpdated(resultSet.getTimestamp("lastUpdated").toLocalDateTime());
        recipe.setTags(Optional.ofNullable(resultSet.getString("tags")).map(tags -> List.of(tags.split(","))).orElse(List.of()));
        recipe.setImages(Optional.ofNullable(resultSet.getBytes("images")).map(bytes -> List.of(bytes)).orElse(List.of()));
        return recipe;
    }
}
