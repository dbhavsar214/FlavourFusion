package org.example.repository;

import org.example.model.Category;
import org.example.model.Tag;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public boolean save(Category category) {
        System.out.println(category.toString());
        return true;
        // String sql = "INSERT INTO categories (categoryName) VALUES (?)";
        // try (Connection connection = DatabaseManager.getConnection();
        //      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        //     preparedStatement.setString(1, category.getCategoryName());
        //     int rowsInserted = preparedStatement.executeUpdate();
        //     if (rowsInserted > 0) {
        //         ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        //         if (generatedKeys.next()) {
        //             category.setCategoryID(generatedKeys.getLong(1));
        //             return true;
        //         }
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        // return false;
    }

    @Override
    public void update(Category category) {
        String sql = "UPDATE categories SET categoryName = ? WHERE categoryID = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setLong(2, category.getCategoryID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateTags(Long categoryId, List<Tag> tags) {
        if(!validateTagsExistence(tags))
            return false;

        String updateSql = "UPDATE categories SET tags = ? WHERE category_id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {

            // Convert List<Tag> to a comma-separated string of tag names
            List<String> tagNames = new ArrayList<>();
            for (Tag tag : tags) {
                tagNames.add(tag.getTagName());
            }
            String tagsString = String.join(",", tagNames);

            preparedStatement.setString(1, tagsString);
            preparedStatement.setLong(2, categoryId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Failed to update category with ID: " + categoryId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean validateTagsExistence(List<Tag> tags) {
        for (Tag tag : tags) {
            if (!tagExistsInDatabase(tag.getTagName())) {
                return false;
            }
        }
        return true;
    }

    private boolean tagExistsInDatabase(String tagName) {
        String sql = "SELECT COUNT(*) FROM tags WHERE tag_name = ?";
    
        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, tagName);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if(count<=0)
                        return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true; // default to false if an exception occurs or no result found
    }

    @Override
    public Category findById(Long id) {
        String sql = "SELECT * FROM categories WHERE categoryID = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category category = new Category();
                category.setCategoryID(resultSet.getLong("categoryID"));
                category.setCategoryName(resultSet.getString("categoryName"));
                return category;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tag> findTagsByCategoryId(Long categoryId) {
        // Implement logic to fetch tags associated with a category
        // Example: This method assumes a separate table or structure for tags associated with categories
        // Not implemented in detail here for brevity
        return new ArrayList<>();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM categories WHERE categoryID = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
