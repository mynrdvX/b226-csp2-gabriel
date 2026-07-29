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

    // Retrieves playlists owned by a specific user
    public List<Playlist> getPlaylistsByUserId(int userId) {
        return playlistService.getPlaylistsByUserId(userId);
    }

    // Checks whether a playlist belongs to a specific user
    public boolean playlistBelongsToUser(
            int playlistId,
            int userId
    ) {
        return playlistService.playlistBelongsToUser(
                playlistId,
                userId
        );
    }

    // Deletes a playlist only when it belongs to the user
    public boolean deletePlaylistByUser(
            int playlistId,
            int userId
    ) {
        return playlistService.deletePlaylistByUser(
                playlistId,
                userId
        );
    }
}