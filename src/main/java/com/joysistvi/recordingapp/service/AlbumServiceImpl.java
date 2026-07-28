package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.repository.AlbumRepository;

import java.time.Year;
import java.util.List;

public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    // Retrieves all albums
    @Override
    public List<Album> getAllAlbums() {
        return albumRepository.getAllAlbumsWithArtist();
    }

    // Searches albums by album name or artist name
    @Override
    public List<Album> searchAlbum(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return List.of();
        }

        return albumRepository.searchAlbum(keyword.trim());
    }

    // Adds a new album after validating its data
    @Override
    public boolean addAlbum(Album album) {

        if (!isValidAlbum(album)) {
            return false;
        }

        album.setName(album.getName().trim());

        return albumRepository.createAlbum(album);
    }

    // Updates an existing album after validating its data
    @Override
    public boolean updateAlbum(Album album) {

        if (album == null) {
            System.out.println("Album information cannot be empty.");
            return false;
        }

        if (album.getId() <= 0) {
            System.out.println("Album ID must be greater than zero.");
            return false;
        }

        if (!isValidAlbum(album)) {
            return false;
        }

        album.setName(album.getName().trim());

        return albumRepository.updateAlbum(album);
    }

    // Deletes an album after validating its ID
    @Override
    public boolean deleteAlbum(int id) {

        if (id <= 0) {
            System.out.println("Album ID must be greater than zero.");
            return false;
        }

        return albumRepository.deleteAlbum(id);
    }

    // Validates the common album fields
    private boolean isValidAlbum(Album album) {

        if (album == null) {
            System.out.println("Album information cannot be empty.");
            return false;
        }

        if (album.getName() == null || album.getName().trim().isEmpty()) {
            System.out.println("Album name cannot be empty.");
            return false;
        }

        int currentYear = Year.now().getValue();

        if (album.getYear() < 1900 || album.getYear() > currentYear) {
            System.out.println(
                    "Album year must be between 1900 and " + currentYear + "."
            );
            return false;
        }

        if (album.getArtistId() <= 0) {
            System.out.println("Artist ID must be greater than zero.");
            return false;
        }

        return true;
    }
}