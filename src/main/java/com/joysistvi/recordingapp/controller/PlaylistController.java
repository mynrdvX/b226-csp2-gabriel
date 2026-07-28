package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.service.PlaylistService;

import java.util.List;

public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(
            PlaylistService playlistService
    ) {
        this.playlistService = playlistService;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    public List<Playlist> searchPlaylist(
            String keyword
    ) {
        return playlistService.searchPlaylist(keyword);
    }

    public boolean addPlaylist(
            Playlist playlist
    ) {
        return playlistService.addPlaylist(playlist);
    }

    public boolean updatePlaylist(
            Playlist playlist
    ) {
        return playlistService.updatePlaylist(playlist);
    }

    public boolean deletePlaylist(int id) {
        return playlistService.deletePlaylist(id);
    }
}