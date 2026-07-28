package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.service.ArtistService;

import java.util.List;

public class ArtistController {

    private final ArtistService artistService;

    // Constructor injection
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    // Retrieve all artists
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    // Search artists by name
    public List<Artist> searchArtist(String keyword) {
        return artistService.searchArtist(keyword);
    }

    // Add a new artist
    public boolean addArtist(Artist artist) {
        return artistService.addArtist(artist);
    }

    // Update an existing artist
    public boolean updateArtist(Artist artist) {
        return artistService.updateArtist(artist);
    }

    // Delete an artist
    public boolean deleteArtist(int id) {
        return artistService.deleteArtist(id);
    }
}