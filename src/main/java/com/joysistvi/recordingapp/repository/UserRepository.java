package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.User;

import java.util.List;

public interface UserRepository {

    // Retrieve all users
    List<User> getAllUsers();

    // Search users by username keyword
    List<User> searchUser(String keyword);

    // Create a new user
    boolean createUser(User user);

    // Update an existing user
    boolean updateUser(User user);

    // Delete a user using the user ID
    boolean deleteUser(int id);

    // Check whether a username already exists
    boolean usernameExists(String username);

    // Retrieve one complete user record for authentication
    User findByUsername(String username);
}