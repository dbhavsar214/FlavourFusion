package org.example.repository;

import org.example.model.Notification;
import org.example.repository.DatabaseManager;
import org.example.repository.NotificationRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    @Override
    public boolean save(Notification notification) {
//        try (Connection connection = DatabaseManager.getConnection();
//             PreparedStatement statement = connection.prepareStatement(
//                     "INSERT INTO notifications (receiverID, body, title) VALUES (?, ?, ?)")) {
//            statement.setLong(1, notification.getReceiverID());
//            statement.setString(2, notification.getBody());
//            statement.setString(3, notification.getTitle());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
        System.out.println(notification.toString());
        return true;
    }

    @Override
    public Optional<Notification> findById(Long id) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM notifications WHERE notificationID = ?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Notification notification = new Notification(
                        resultSet.getLong("notificationID"),
                        resultSet.getLong("receiverID"),
                        resultSet.getString("body"),
                        resultSet.getString("title")
                );
                return Optional.of(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Notification> findAll() {
        List<Notification> notifications = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM notifications")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Notification notification = new Notification(
                        resultSet.getLong("notificationID"),
                        resultSet.getLong("receiverID"),
                        resultSet.getString("body"),
                        resultSet.getString("title")
                );
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    @Override
    public void update(Notification notification, Long id) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE notifications SET receiverID = ?, body = ?, title = ? WHERE notificationID = ?")) {
            statement.setLong(1, notification.getReceiverID());
            statement.setString(2, notification.getBody());
            statement.setString(3, notification.getTitle());
            statement.setLong(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM notifications WHERE notificationID = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
