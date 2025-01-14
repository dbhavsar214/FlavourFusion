package org.example.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;

@Component
public class DatabaseInitializer {

    private final DataSource dataSource;

    @Value("classpath:database_init.sql")
    private Resource schemaScript;

    public DatabaseInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initializeDatabase() {
        try {
            String sql = StreamUtils.copyToString(schemaScript.getInputStream(), StandardCharsets.UTF_8);
            executeSqlScript(sql);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }
    }

    private void executeSqlScript(String sqlScript) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            System.out.println("DB working");
            statement.execute(sqlScript);
        }
    }
}
