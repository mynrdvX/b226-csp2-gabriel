package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final DbConnection dbConnection;

    public UserRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT id, username, password, role
                FROM users
                ORDER BY id
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving users: " + e.getMessage()
            );
        }

        return users;
    }

    @Override
    public List<User> searchUser(String keyword) {

        List<User> users = new ArrayList<>();

        String sql = """
                SELECT id, username, password, role
                FROM users
                WHERE username LIKE ?
                ORDER BY id
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    "%" + keyword.trim() + "%"
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {
                    users.add(mapUser(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error searching users: " + e.getMessage()
            );
        }

        return users;
    }

    @Override
    public boolean createUser(User user) {

        String sql = """
                INSERT INTO users (username, password, role)
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    user.getUsername()
            );

            statement.setString(
                    2,
                    user.getPassword()
            );

            statement.setString(
                    3,
                    normalizeRole(user.getRole())
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error creating user: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {

        String sql = """
                UPDATE users
                SET username = ?, password = ?, role = ?
                WHERE id = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    user.getUsername()
            );

            statement.setString(
                    2,
                    user.getPassword()
            );

            statement.setString(
                    3,
                    normalizeRole(user.getRole())
            );

            statement.setInt(
                    4,
                    user.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error updating user: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {

        String sql = """
                DELETE FROM users
                WHERE id = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error deleting user: " + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean usernameExists(String username) {

        String sql = """
                SELECT COUNT(*) AS total
                FROM users
                WHERE username = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    username.trim()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {
                    return resultSet.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error checking username: " + e.getMessage()
            );
        }

        return false;
    }

    @Override
    public User findByUsername(String username) {

        String sql = """
                SELECT id, username, password, role
                FROM users
                WHERE username = ?
                LIMIT 1
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    username.trim()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving user by username: "
                            + e.getMessage()
            );
        }

        return null;
    }

    /**
     * Converts one database row into a User object.
     */
    private User mapUser(ResultSet resultSet)
            throws SQLException {

        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                normalizeRole(
                        resultSet.getString("role")
                )
        );
    }

    /**
     * Ensures that roles are consistently stored as
     * either ADMIN or USER.
     */
    private String normalizeRole(String role) {

        if (role == null || role.isBlank()) {
            return "USER";
        }

        if (role.equalsIgnoreCase("ADMIN")) {
            return "ADMIN";
        }

        return "USER";
    }
}