package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    private final DbConnection dbConnection;

    public UserDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }



    public void registerUser(String username, String password) {
        String query = "INSERT INTO users(username, password) VALUES (?, ?)";

        // Hash the password securely
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection connection = dbConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setString(1, username);
            prep.setString(2, hashedPassword);

            int rows = prep.executeUpdate();
            if (rows > 0) {
                System.out.println("User " + username + " registered successfully!");
            } else {
                System.out.println("Failed to register user " + username);
            }
        } catch (Exception e) {
            System.out.println("Register User Error: " + e.getMessage());
        }
    }

    //winter
    public void loginUser(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query)) {

            prep.setString(1, username);
            ResultSet rs = prep.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");

                if (BCrypt.checkpw(password, storedHash)) {
                    System.out.println("Login successful! Welcome, " + username);

                } else {
                    System.out.println("Invalid password for user: " + username);
                }
            } else {
                System.out.println("User not found: " + username);
            }
        } catch (Exception e) {
            System.out.println("Login Error: " + e.getMessage());
        }
    }


}
