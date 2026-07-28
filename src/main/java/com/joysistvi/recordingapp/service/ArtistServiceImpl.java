package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.repository.ArtistRepository;

import java.util.ArrayList;
import java.util.List;

public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    // Constructor injection
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    // Retrieve all artists
    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.getAllArtists();
    }

    // Search artists by name
    @Override
    public List<Artist> searchArtist(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return new ArrayList<>();
        }

        return artistRepository.searchArtist(keyword.trim());
    }

    // Add a new artist
    @Override
    public boolean addArtist(Artist artist) {

        if (!isValidArtist(artist)) {
            return false;
        }

        return artistRepository.createArtist(artist);
    }

    // Update an existing artist
    @Override
    public boolean updateArtist(Artist artist) {

        if (artist == null) {
            System.out.println("Artist information is required.");
            return false;
        }

        if (artist.getId() <= 0) {
            System.out.println("Artist ID must be greater than zero.");
            return false;
        }

        if (!isValidArtist(artist)) {
            return false;
        }

        return artistRepository.updateArtist(artist);
    }

    // Delete an artist
    @Override
    public boolean deleteArtist(int id) {

        if (id <= 0) {
            System.out.println("Artist ID must be greater than zero.");
            return false;
        }

        return artistRepository.deleteArtist(id);
    }

    // Validate artist information
    private boolean isValidArtist(Artist artist) {

        if (artist == null) {
            System.out.println("Artist information is required.");
            return false;
        }

        if (artist.getName() == null || artist.getName().trim().isEmpty()) {
            System.out.println("Artist name cannot be empty.");
            return false;
        }

        artist.setName(artist.getName().trim());

        return true;
    }
}