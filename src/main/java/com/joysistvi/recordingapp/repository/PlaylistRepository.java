package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.model.Playlist;

import java.util.List;

public interface PlaylistRepository {

    List<Playlist> getAllPlaylistsWithUser();

    List<Playlist> searchPlaylist(String keyword);

    boolean createPlaylist(Playlist playlist);

    boolean updatePlaylist(Playlist playlist);

    boolean deletePlaylist(int id);

    boolean playlistNameExistsForUser(
            String name,
            int userId
    );
}