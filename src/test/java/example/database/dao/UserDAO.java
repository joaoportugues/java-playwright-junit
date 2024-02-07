package example.database.dao;

import example.database.DatabaseConnectionManager;
import example.database.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String SELECT_USER_BY_ID_SQL = "SELECT id, username, email FROM users WHERE id = ?";

    public static User getUserById(int userId) throws SQLException {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
        ) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    return new User(id, username, email);
                }
            }
        }
        return null; // User not found
    }
}