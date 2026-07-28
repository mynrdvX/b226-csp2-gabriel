package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Artist;
import java.util.List;

/**
 * Service interface for Artist business operations.
 * Validation and business rules will be implemented
 * in ArtistServiceImpl.
 */
public interface ArtistService {

    // Retrieve all artists
    List<Artist> getAllArtists();

    // Search artists by name
    List<Artist> searchArtist(String keyword);

    // Add a new artist
    boolean addArtist(Artist artist);

    // Update an existing artist
    boolean updateArtist(Artist artist);

    // Delete an artist
    boolean deleteArtist(int id);

}