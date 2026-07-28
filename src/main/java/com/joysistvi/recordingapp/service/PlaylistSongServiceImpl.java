package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.repository.PlaylistSongRepository;

import java.util.Collections;
import java.util.List;

public class PlaylistSongServiceImpl
        implements PlaylistSongService {

    private final PlaylistSongRepository playlistSongRepository;

    public PlaylistSongServiceImpl(
            PlaylistSongRepository playlistSongRepository
    ) {
        this.playlistSongRepository = playlistSongRepository;
    }

    @Override
    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongRepository.getAllPlaylistSongs();
    }

    @Override
    public List<PlaylistSong> getSongsByPlaylistId(
            int playlistId
    ) {

        if (playlistId <= 0) {
            System.out.println(
                    "Playlist ID must be greater than zero."
            );

            return Collections.emptyList();
        }

        return playlistSongRepository.getSongsByPlaylistId(
                playlistId
        );
    }

    @Override
    public boolean addSongToPlaylist(
            PlaylistSong playlistSong
    ) {

        if (!isValidPlaylistSong(playlistSong)) {
            return false;
        }

        int playlistId =
                playlistSong.getPlaylistId();

        int songId =
                playlistSong.getSongId();

        if (playlistSongRepository.playlistSongExists(
                playlistId,
                songId
        )) {
            System.out.println(
                    "This song is already inside the selected playlist."
            );

            return false;
        }

        return playlistSongRepository.addSongToPlaylist(
                playlistSong
        );
    }

    @Override
    public boolean removeSongFromPlaylist(int id) {

        if (id <= 0) {
            System.out.println(
                    "Playlist-song ID must be greater than zero."
            );

            return false;
        }

        return playlistSongRepository.removeSongFromPlaylist(
                id
        );
    }

    private boolean isValidPlaylistSong(
            PlaylistSong playlistSong
    ) {

        if (playlistSong == null) {
            System.out.println(
                    "Playlist-song data cannot be null."
            );

            return false;
        }

        if (playlistSong.getPlaylistId() <= 0) {
            System.out.println(
                    "Playlist ID must be greater than zero."
            );

            return false;
        }

        if (playlistSong.getSongId() <= 0) {
            System.out.println(
                    "Song ID must be greater than zero."
            );

            return false;
        }

        return true;
    }
}