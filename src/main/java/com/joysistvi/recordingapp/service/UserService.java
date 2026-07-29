package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    List<User> searchUser(String keyword);

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int id);

    User login(String username, String password);
}