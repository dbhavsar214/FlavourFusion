package org.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Tag;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String INSERT_SQL = "INSERT INTO tags (tagName) VALUES (?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM tags WHERE tagID = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM tags";
    private static final String UPDATE_SQL = "UPDATE tags SET tagName = ? WHERE tagID = ?";
    private static final String DELETE_SQL = "DELETE FROM tags WHERE tagID = ?";
    private static final String SELECT_BY_NAME_SQL = "SELECT * FROM tags WHERE tagName = ?";

    @Override
    public boolean save(Tag tag) {
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
            preparedStatement.setString(1, tag.getTagName());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Tag findByName(String tagName) {
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME_SQL)) {
            preparedStatement.setString(1, tagName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTag(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Tag findById(int id) {
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTag(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                tags.add(mapResultSetToTag(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    @Override
    public boolean update(Tag tag) {
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, tag.getTagName());
            preparedStatement.setInt(2, tag.getTagID());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = DatabaseManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Tag mapResultSetToTag(ResultSet resultSet) throws SQLException {
        Tag tag = new Tag();
        tag.setTagID(resultSet.getInt("tagID"));
        tag.setTagName(resultSet.getString("tagName"));
        return tag;
    }
}
