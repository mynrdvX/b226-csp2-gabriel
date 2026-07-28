package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Artist;
import java.util.List;

/**
 * Repository interface for Artist database operations.
 * The SQL implementation will be provided in ArtistRepositoryImpl.
 */
public interface ArtistRepository {

    // Retrieve all artists
    List<Artist> getAllArtists();

    // Search artists by name
    List<Artist> searchArtist(String keyword);

    // Insert a new artist
    boolean createArtist(Artist artist);

    // Update an existing artist
    boolean updateArtist(Artist artist);

    // Delete an artist by ID
    boolean deleteArtist(int id);

}