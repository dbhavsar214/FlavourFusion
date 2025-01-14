package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.model.Recipe;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

    @Override
    public boolean save(Recipe recipe) {
        System.out.println(recipe.toString());
        String sql = "INSERT INTO recipes (creatorID, name, ingredients, description, cookingTime, direction, tags) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, recipe.getCreatorID());
            preparedStatement.setString(2, recipe.getName());
            preparedStatement.setString(3, String.join(",", recipe.getIngredients()));
            preparedStatement.setString(4, recipe.getDescription());
            preparedStatement.setInt(5, 0);
            preparedStatement.setString(6, recipe.getDirection());
            preparedStatement.setString(7, String.join(",", recipe.getTags()));

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        String sql = "SELECT * FROM recipes WHERE recipeID = ?";
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Recipe recipe = mapRowToRecipe(resultSet);
                return Optional.of(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Recipe> findAll() {
        List<Recipe> recipes = new ArrayList<>();
        String sql = "SELECT * FROM recipes";
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                recipes.add(mapRowToRecipe(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    @Override
    public void update(Recipe recipe, Long id) {
        String sql = "UPDATE recipes SET name = ?, ingredients = ?, description = ?, cookingTime = ?, direction = ?, lastUpdated = ?, tags = ?, images = ? WHERE recipeID = ?";
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.setString(2, String.join(",", recipe.getIngredients()));
            preparedStatement.setString(3, recipe.getDescription());
            preparedStatement.setInt(4, recipe.getCookingTime());
            preparedStatement.setString(5, recipe.getDirection());
            preparedStatement.setObject(6, recipe.getLastUpdated());
            preparedStatement.setString(7, String.join(",", recipe.getTags()));
            preparedStatement.setBytes(8, recipe.getImages().toString().getBytes()); // Simplification for example
                                                                                     // purposes
            preparedStatement.setLong(9, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM recipes WHERE recipeID = ?";
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to map a ResultSet row to a Recipe object
    private Recipe mapRowToRecipe(ResultSet resultSet) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setCreatorID(resultSet.getLong("creatorID"));
        recipe.setRecipeID(resultSet.getLong("recipeID"));
        recipe.setName(resultSet.getString("name"));
        recipe.setIngredients(List.of(resultSet.getString("ingredients").split(",")));
        recipe.setDescription(resultSet.getString("description"));
        recipe.setCookingTime(resultSet.getInt("cookingTime"));
        recipe.setDirection(resultSet.getString("direction"));
        recipe.setCreationDate(resultSet.getTimestamp("creationDate").toLocalDateTime());
        recipe.setLastUpdated(resultSet.getTimestamp("lastUpdated").toLocalDateTime());
        recipe.setTags(List.of(resultSet.getString("tags").split(",")));
        // recipe.setImages(List.of(resultSet.getBytes("images"))); // Simplified for
        // example
        return recipe;
    }
}
