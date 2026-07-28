package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Search users by username
    public List<User> searchUser(String keyword) {
        return userService.searchUser(keyword);
    }

    // Add a new user
    public boolean addUser(User user) {
        return userService.addUser(user);
    }

    // Update an existing user
    public boolean updateUser(User user) {
        return userService.updateUser(user);
    }

    // Delete a user
    public boolean deleteUser(int id) {
        return userService.deleteUser(id);
    }
}