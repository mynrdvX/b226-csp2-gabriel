package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    List<User> searchUser(String keyword);

    boolean createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int id);

    boolean usernameExists(String username);
}