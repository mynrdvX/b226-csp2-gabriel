package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Album;

import java.util.List;

/**
 * Repository interface for Album database operations.
 */
public interface AlbumRepository {

    // Retrieve all albums with their artist information
    List<Album> getAllAlbumsWithArtist();

    // Search albums by album name
    List<Album> searchAlbum(String keyword);

    // Insert a new album
    boolean createAlbum(Album album);

    // Update an existing album
    boolean updateAlbum(Album album);

    // Delete an album by ID
    boolean deleteAlbum(int id);
}