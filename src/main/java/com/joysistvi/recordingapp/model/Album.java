package com.joysistvi.recordingapp.model;

public class Album {

    private int id;
    private String name;
    private int year;
    private int artistId;
    private String artistName;

    // Empty constructor
    public Album() {
    }

    // Constructor for creating a new album
    public Album(String name, int year, int artistId) {
        this.name = name;
        this.year = year;
        this.artistId = artistId;
    }

    // Constructor for updating an existing album
    public Album(int id, String name, int year, int artistId) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.artistId = artistId;
    }

    // Constructor for displaying album details with artist name
    public Album(int id, String name, int year, int artistId, String artistName) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.artistId = artistId;
        this.artistName = artistName;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}