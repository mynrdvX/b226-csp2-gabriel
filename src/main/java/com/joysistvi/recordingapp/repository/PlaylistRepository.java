package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;

public interface PlaylistRepository {

    // Retrieves all playlists together with their owners
    List<Playlist> getAllPlaylistsWithUser();

    // Searches playlists by playlist name or username
    List<Playlist> searchPlaylist(String keyword);

    // Creates a new playlist
    boolean createPlaylist(Playlist playlist);

    // Updates an existing playlist
    boolean updatePlaylist(Playlist playlist);

    // Deletes any playlist using its ID
    // Intended for administrator operations
    boolean deletePlaylist(int id);

    // Checks whether the user already has a playlist
    // with the same name
    boolean playlistNameExistsForUser(
            String name,
            int userId
    );

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