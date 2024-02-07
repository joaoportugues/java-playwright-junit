package example.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {
    private static final String PROPERTIES_FILE = "src/test/resources/db/database.properties";

    public static Connection getConnection() throws SQLException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(PROPERTIES_FILE));

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            return DriverManager.getConnection(url, username, password);
        } catch (IOException e) {
            throw new SQLException("Failed to load database properties", e);
        }
    }
}