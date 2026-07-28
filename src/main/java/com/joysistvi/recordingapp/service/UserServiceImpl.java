package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<User> searchUser(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return Collections.emptyList();
        }

        return userRepository.searchUser(keyword.trim());
    }

    @Override
    public boolean addUser(User user) {

        if (!isValidUser(user, false)) {
            return false;
        }

        String username = user.getUsername().trim();

        if (userRepository.usernameExists(username)) {
            System.out.println("Username already exists.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(
                user.getPassword(),
                BCrypt.gensalt()
        );

        user.setUsername(username);
        user.setPassword(hashedPassword);

        return userRepository.createUser(user);
    }

    @Override
    public boolean updateUser(User user) {

        if (!isValidUser(user, true)) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(
                user.getPassword(),
                BCrypt.gensalt()
        );

        user.setUsername(user.getUsername().trim());
        user.setPassword(hashedPassword);

        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUser(int id) {

        if (id <= 0) {
            System.out.println("User ID must be greater than zero.");
            return false;
        }

        return userRepository.deleteUser(id);
    }

    private boolean isValidUser(User user, boolean requireId) {

        if (user == null) {
            System.out.println("User cannot be null.");
            return false;
        }

        if (requireId && user.getId() <= 0) {
            System.out.println("User ID must be greater than zero.");
            return false;
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return false;
        }

        if (user.getUsername().trim().length() < 3) {
            System.out.println("Username must contain at least 3 characters.");
            return false;
        }

        if (user.getUsername().trim().length() > 50) {
            System.out.println("Username cannot exceed 50 characters.");
            return false;
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }

        if (user.getPassword().length() < 8) {
            System.out.println("Password must contain at least 8 characters.");
            return false;
        }

        return true;
    }
}