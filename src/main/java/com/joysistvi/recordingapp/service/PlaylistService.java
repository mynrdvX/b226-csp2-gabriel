package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;

public interface PlaylistService {

    // Retrieves all playlists
    // Intended for administrator operations
    List<Playlist> getAllPlaylists();

    // Searches playlists by playlist name or owner
    List<Playlist> searchPlaylist(String keyword);

    // Creates a new playlist
    boolean addPlaylist(Playlist playlist);

    // Updates an existing playlist
    boolean updatePlaylist(Playlist playlist);

    // Deletes any playlist using its ID
    // Intended for administrator operations
    boolean deletePlaylist(int id);

    // Retrieves only the playlists owned by one user
    List<Playlist> getPlaylistsByUserId(int userId);

    // Checks whether a playlist belongs to a specific user
    boolean playlistBelongsToUser(
            int playlistId,
            int userId
    );

    // Deletes a playlist only when it belongs to
    // the specified user
    boolean deletePlaylistByUser(
            int playlistId,
            int userId
    );
}