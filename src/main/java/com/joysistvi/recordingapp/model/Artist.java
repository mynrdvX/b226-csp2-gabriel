
//Artist.java


package com.joysistvi.recordingapp.model;

public class Artist {

    private int id;
    private String name;

    // Empty constructor
    public Artist() {
    }

    // Constructor for creating a new artist
    public Artist(String name) {
        this.name = name;
    }

    // Constructor for existing artist
    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
}
