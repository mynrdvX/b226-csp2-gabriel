package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.service.AlbumService;

import java.util.List;

public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // Retrieves all albums
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    // Searches albums by album name or artist name
    public List<Album> searchAlbum(String keyword) {
        return albumService.searchAlbum(keyword);
    }

    // Adds a new album
    public boolean addAlbum(Album album) {
        return albumService.addAlbum(album);
    }

    // Updates an existing album
    public boolean updateAlbum(Album album) {
        return albumService.updateAlbum(album);
    }

    // Deletes an album
    public boolean deleteAlbum(int id) {
        return albumService.deleteAlbum(id);
    }
}