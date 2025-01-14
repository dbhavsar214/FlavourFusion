package org.example.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_2_PRODUCTION";
    private static final String USER = "CSCI5308_2_PRODUCTION_USER";
    private static final String PASSWORD = "Yae1Rai0no";

    // Method to establish a connection to the database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
