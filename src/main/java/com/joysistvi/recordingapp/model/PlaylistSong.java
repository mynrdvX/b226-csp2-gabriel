package com.joysistvi.recordingapp.model;

public class PlaylistSong {

    private int id;
    private int playlistId;
    private String playlistName;
    private int songId;
    private String songTitle;

    // Empty constructor
    public PlaylistSong() {
    }

    // Constructor used when adding a song to a playlist
    public PlaylistSong(int playlistId, int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    // Constructor used when retrieving joined playlist-song data
    public PlaylistSong(
            int id,
            int playlistId,
            String playlistName,
            int songId,
            String songTitle
    ) {
        this.id = id;
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.songId = songId;
        this.songTitle = songTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}