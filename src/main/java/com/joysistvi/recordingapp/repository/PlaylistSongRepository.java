package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.PlaylistSong;

import java.util.List;

public interface PlaylistSongRepository {

    // Gets all playlist-song relationships with readable names
    List<PlaylistSong> getAllPlaylistSongs();

    // Gets all songs that belong to one selected playlist
    List<PlaylistSong> getSongsByPlaylistId(int playlistId);

    // Adds a song to a playlist
    boolean addSongToPlaylist(PlaylistSong playlistSong);

    // Removes a playlist-song relationship using its junction table ID
    boolean removeSongFromPlaylist(int id);

    // Checks whether the same song is already inside the playlist
    boolean playlistSongExists(
            int playlistId,
            int songId
    );
}