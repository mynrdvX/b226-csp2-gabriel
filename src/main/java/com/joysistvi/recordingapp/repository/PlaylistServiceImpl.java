package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.repository.PlaylistRepository;

import java.util.Collections;
import java.util.List;

public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(
            PlaylistRepository playlistRepository
    ) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.getAllPlaylistsWithUser();
    }

    @Override
    public List<Playlist> searchPlaylist(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println(
                    "Search keyword cannot be empty."
            );
            return Collections.emptyList();
        }

        return playlistRepository.searchPlaylist(
                keyword.trim()
        );
    }

    @Override
    public boolean addPlaylist(Playlist playlist) {

        if (!isValidPlaylist(playlist, false)) {
            return false;
        }

        String trimmedName =
                playlist.getName().trim();

        if (playlistRepository.playlistNameExistsForUser(
                trimmedName,
                playlist.getUserId()
        )) {
            System.out.println(
                    "This user already has a playlist with that name."
            );
            return false;
        }

        playlist.setName(trimmedName);

        return playlistRepository.createPlaylist(playlist);
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {

        if (!isValidPlaylist(playlist, true)) {
            return false;
        }

        playlist.setName(
                playlist.getName().trim()
        );

        return playlistRepository.updatePlaylist(playlist);
    }

    @Override
    public boolean deletePlaylist(int id) {

        if (id <= 0) {
            System.out.println(
                    "Playlist ID must be greater than zero."
            );
            return false;
        }

        return playlistRepository.deletePlaylist(id);
    }

    private boolean isValidPlaylist(
            Playlist playlist,
            boolean requireId
    ) {

        if (playlist == null) {
            System.out.println(
                    "Playlist cannot be null."
            );
            return false;
        }

        if (requireId && playlist.getId() <= 0) {
            System.out.println(
                    "Playlist ID must be greater than zero."
            );
            return false;
        }

        if (
                playlist.getName() == null
                        || playlist.getName().trim().isEmpty()
        ) {
            System.out.println(
                    "Playlist name cannot be empty."
            );
            return false;
        }

        if (playlist.getName().trim().length() > 100) {
            System.out.println(
                    "Playlist name cannot exceed 100 characters."
            );
            return false;
        }

        if (playlist.getUserId() <= 0) {
            System.out.println(
                    "User ID must be greater than zero."
            );
            return false;
        }

        return true;
    }
}