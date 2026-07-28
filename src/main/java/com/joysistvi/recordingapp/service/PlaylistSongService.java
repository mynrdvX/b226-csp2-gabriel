package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.PlaylistSong;

import java.util.List;

public interface PlaylistSongService {

    // Retrieves all playlist-song relationships
    List<PlaylistSong> getAllPlaylistSongs();

    // Retrieves all songs that belong to a selected playlist
    List<PlaylistSong> getSongsByPlaylistId(int playlistId);

    // Adds a song to a playlist
    boolean addSongToPlaylist(PlaylistSong playlistSong);

    // Removes a song from a playlist using the junction table ID
    boolean removeSongFromPlaylist(int id);
}