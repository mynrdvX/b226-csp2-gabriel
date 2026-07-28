package com.joysistvi.recordingapp.controller;

import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.service.PlaylistSongService;

import java.util.List;

public class PlaylistSongController {

    private final PlaylistSongService playlistSongService;

    public PlaylistSongController(
            PlaylistSongService playlistSongService
    ) {
        this.playlistSongService = playlistSongService;
    }

    public List<PlaylistSong> getAllPlaylistSongs() {
        return playlistSongService.getAllPlaylistSongs();
    }

    public List<PlaylistSong> getSongsByPlaylistId(
            int playlistId
    ) {
        return playlistSongService.getSongsByPlaylistId(
                playlistId
        );
    }

    public boolean addSongToPlaylist(
            PlaylistSong playlistSong
    ) {
        return playlistSongService.addSongToPlaylist(
                playlistSong
        );
    }

    public boolean removeSongFromPlaylist(int id) {
        return playlistSongService.removeSongFromPlaylist(
                id
        );
    }
}