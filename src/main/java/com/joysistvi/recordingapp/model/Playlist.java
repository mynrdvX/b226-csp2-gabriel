package com.joysistvi.recordingapp.model;

import java.time.LocalDateTime;

public class Playlist {

    private int id;
    private String name;
    private LocalDateTime dateCreated;
    private int userId;
    private String username;

    // Empty constructor
    public Playlist() {
    }

    // Constructor used when creating a new playlist
    public Playlist(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }

    // Constructor used when updating an existing playlist
    public Playlist(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    // Constructor used when retrieving playlist data with owner information
    public Playlist(
            int id,
            String name,
            LocalDateTime dateCreated,
            int userId,
            String username
    ) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.userId = userId;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}