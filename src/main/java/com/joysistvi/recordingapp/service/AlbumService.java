package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;

import java.util.List;

public interface AlbumService {

    // Retrieve all albums
    List<Album> getAllAlbums();

    // Search albums by album name or artist name
    List<Album> searchAlbum(String keyword);

    // Add a new album
    boolean addAlbum(Album album);

    // Update an existing album
    boolean updateAlbum(Album album);

    // Delete an album
    boolean deleteAlbum(int id);
}