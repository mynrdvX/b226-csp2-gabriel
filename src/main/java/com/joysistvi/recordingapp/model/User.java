package com.joysistvi.recordingapp.model;

public class User {

    private int id;
    private String username;
    private String password;
    private String role;

    // Empty constructor
    public User() {
    }

    // Constructor for registration
    // New accounts are automatically assigned the USER role
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "USER";
    }

    // Constructor for creating a user with a specified role
    public User(
            String username,
            String password,
            String role
    ) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Constructor for retrieving or updating complete user data
    public User(
            int id,
            String username,
            String password,
            String role
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {

        if (role == null || role.isBlank()) {
            this.role = "USER";
            return;
        }

        this.role = role.toUpperCase();
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public boolean isUser() {
        return "USER".equalsIgnoreCase(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}