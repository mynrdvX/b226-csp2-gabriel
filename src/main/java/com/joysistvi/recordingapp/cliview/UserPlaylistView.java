package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.PlaylistSong;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * Displays and manages the personal playlists
 * of the currently authenticated user.
 *
 * Available operations:
 * - View personal playlists
 * - Create a playlist
 * - Delete an owned playlist
 * - View songs inside an owned playlist
 * - Add a song to an owned playlist
 * - Remove a song from an owned playlist
 */
public class UserPlaylistView {

    private final PlaylistController playlistController;
    private final PlaylistSongController playlistSongController;
    private final SongController songController;
    private final Scanner scanner;

    /**
     * Constructor injection for playlist-related dependencies.
     */
    public UserPlaylistView(
            PlaylistController playlistController,
            PlaylistSongController playlistSongController,
            SongController songController,
            Scanner scanner
    ) {
        this.playlistController = playlistController;
        this.playlistSongController = playlistSongController;
        this.songController = songController;
        this.scanner = scanner;
    }

    /**
     * Runs the personal playlist menu until the user
     * chooses to return to the User Dashboard.
     *
     * @param loggedInUser currently authenticated user
     */
    public void run(User loggedInUser) {

        int choice;

        do {
            showMenu(loggedInUser);

            choice = readInteger();

            switch (choice) {

                case 1:
                    viewMyPlaylists(loggedInUser);
                    pause();
                    break;

                case 2:
                    createPlaylist(loggedInUser);
                    pause();
                    break;

                case 3:
                    deletePlaylist(loggedInUser);
                    pause();
                    break;

                case 4:
                    viewSongsInPlaylist(loggedInUser);
                    pause();
                    break;

                case 5:
                    addSongToPlaylist(loggedInUser);
                    pause();
                    break;

                case 6:
                    removeSongFromPlaylist(loggedInUser);
                    pause();
                    break;

                case 0:
                    System.out.println();
                    System.out.println(
                            "Returning to User Dashboard..."
                    );
                    break;

                default:
                    System.out.println();
                    System.out.println(
                            "Invalid option. Please choose from 0 to 6."
                    );
                    pause();
            }

        } while (choice != 0);
    }

    /**
     * Displays the playlist management menu.
     */
    private void showMenu(User loggedInUser) {

        System.out.println();
        System.out.println("===== MY PLAYLISTS =====");
        System.out.println(
                "Logged in as: " + loggedInUser.getUsername()
        );
        System.out.println();
        System.out.println("1. View My Playlists");
        System.out.println("2. Create Playlist");
        System.out.println("3. Delete Playlist");
        System.out.println("4. View Songs in Playlist");
        System.out.println("5. Add Song to Playlist");
        System.out.println("6. Remove Song from Playlist");
        System.out.println("0. Back");
        System.out.print("Enter your choice: ");
    }

    /**
     * Displays only the playlists owned by the
     * currently authenticated user.
     */
    private void viewMyPlaylists(User loggedInUser) {

        System.out.println();
        System.out.println("===== VIEW MY PLAYLISTS =====");

        List<Playlist> playlists =
                playlistController.getPlaylistsByUserId(
                        loggedInUser.getId()
                );

        if (playlists == null || playlists.isEmpty()) {
            System.out.println();
            System.out.println(
                    "You do not have any playlists yet."
            );
            return;
        }

        printPlaylists(playlists);
    }

    /**
     * Creates a playlist and automatically assigns it
     * to the currently authenticated user.
     */
    private void createPlaylist(User loggedInUser) {

        System.out.println();
        System.out.println("===== CREATE PLAYLIST =====");
        System.out.println();

        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine().trim();

        if (playlistName.isEmpty()) {
            System.out.println();
            System.out.println(
                    "Playlist name cannot be empty."
            );
            return;
        }

        Playlist playlist = new Playlist(
                playlistName,
                loggedInUser.getId()
        );

        boolean created =
                playlistController.addPlaylist(playlist);

        System.out.println();

        if (created) {
            System.out.println(
                    "Playlist created successfully."
            );
        } else {
            System.out.println(
                    "Failed to create playlist."
            );
        }
    }

    /**
     * Deletes a playlist only if it belongs to the
     * currently authenticated user.
     */
    private void deletePlaylist(User loggedInUser) {

        System.out.println();
        System.out.println("===== DELETE PLAYLIST =====");

        List<Playlist> playlists =
                playlistController.getPlaylistsByUserId(
                        loggedInUser.getId()
                );

        if (playlists == null || playlists.isEmpty()) {
            System.out.println();
            System.out.println(
                    "You do not have any playlists to delete."
            );
            return;
        }

        printPlaylists(playlists);

        System.out.println();
        System.out.print("Enter playlist ID to delete: ");
        int playlistId = readInteger();

        if (!playlistController.playlistBelongsToUser(
                playlistId,
                loggedInUser.getId()
        )) {
            System.out.println();
            System.out.println(
                    "Playlist not found or you do not own it."
            );
            return;
        }

        System.out.print(
                "Are you sure you want to delete this playlist? (Y/N): "
        );

        String confirmation =
                scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println();
            System.out.println(
                    "Playlist deletion cancelled."
            );
            return;
        }

        boolean deleted =
                playlistController.deletePlaylistByUser(
                        playlistId,
                        loggedInUser.getId()
                );

        System.out.println();

        if (deleted) {
            System.out.println(
                    "Playlist deleted successfully."
            );
        } else {
            System.out.println(
                    "Failed to delete playlist."
            );
        }
    }

    /**
     * Displays all songs inside a playlist after confirming
     * that the playlist belongs to the current user.
     */
    private void viewSongsInPlaylist(User loggedInUser) {

        System.out.println();
        System.out.println("===== VIEW PLAYLIST SONGS =====");

        Integer playlistId =
                selectOwnedPlaylist(loggedInUser);

        if (playlistId == null) {
            return;
        }

        List<PlaylistSong> playlistSongs =
                playlistSongController.getSongsByPlaylistId(
                        playlistId
                );

        if (playlistSongs == null || playlistSongs.isEmpty()) {
            System.out.println();
            System.out.println(
                    "This playlist does not contain any songs."
            );
            return;
        }

        printPlaylistSongs(playlistSongs);
    }

    /**
     * Adds a selected song to one of the playlists
     * owned by the current user.
     */
    private void addSongToPlaylist(User loggedInUser) {

        System.out.println();
        System.out.println("===== ADD SONG TO PLAYLIST =====");

        Integer playlistId =
                selectOwnedPlaylist(loggedInUser);

        if (playlistId == null) {
            return;
        }

        List<Song> songs =
                songController.handleViewAllSongs();

        if (songs == null || songs.isEmpty()) {
            System.out.println();
            System.out.println(
                    "No songs are currently available."
            );
            return;
        }

        printAvailableSongs(songs);

        System.out.println();
        System.out.print("Enter song ID to add: ");
        int songId = readInteger();

        if (!songExists(songs, songId)) {
            System.out.println();
            System.out.println(
                    "The selected song does not exist."
            );
            return;
        }

        List<PlaylistSong> currentSongs =
                playlistSongController.getSongsByPlaylistId(
                        playlistId
                );

        if (songAlreadyInPlaylist(
                currentSongs,
                songId
        )) {
            System.out.println();
            System.out.println(
                    "That song is already in this playlist."
            );
            return;
        }

        PlaylistSong playlistSong =
                new PlaylistSong(
                        playlistId,
                        songId
                );

        boolean added =
                playlistSongController.addSongToPlaylist(
                        playlistSong
                );

        System.out.println();

        if (added) {
            System.out.println(
                    "Song added to playlist successfully."
            );
        } else {
            System.out.println(
                    "Failed to add song to playlist."
            );
        }
    }

    /**
     * Removes a song from an owned playlist using the
     * junction-table record ID.
     */
    private void removeSongFromPlaylist(User loggedInUser) {

        System.out.println();
        System.out.println("===== REMOVE SONG FROM PLAYLIST =====");

        Integer playlistId =
                selectOwnedPlaylist(loggedInUser);

        if (playlistId == null) {
            return;
        }

        List<PlaylistSong> playlistSongs =
                playlistSongController.getSongsByPlaylistId(
                        playlistId
                );

        if (playlistSongs == null || playlistSongs.isEmpty()) {
            System.out.println();
            System.out.println(
                    "This playlist does not contain any songs."
            );
            return;
        }

        printPlaylistSongs(playlistSongs);

        System.out.println();
        System.out.print(
                "Enter playlist-song record ID to remove: "
        );

        int playlistSongId = readInteger();

        if (!playlistSongRecordExists(
                playlistSongs,
                playlistSongId
        )) {
            System.out.println();
            System.out.println(
                    "The selected playlist-song record was not found."
            );
            return;
        }

        System.out.print(
                "Are you sure you want to remove this song? (Y/N): "
        );

        String confirmation =
                scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println();
            System.out.println(
                    "Song removal cancelled."
            );
            return;
        }

        boolean removed =
                playlistSongController.removeSongFromPlaylist(
                        playlistSongId
                );

        System.out.println();

        if (removed) {
            System.out.println(
                    "Song removed from playlist successfully."
            );
        } else {
            System.out.println(
                    "Failed to remove song from playlist."
            );
        }
    }

    /**
     * Displays the user's playlists and asks them
     * to select one.
     *
     * @return selected playlist ID, or null when unavailable
     */
    private Integer selectOwnedPlaylist(User loggedInUser) {

        List<Playlist> playlists =
                playlistController.getPlaylistsByUserId(
                        loggedInUser.getId()
                );

        if (playlists == null || playlists.isEmpty()) {
            System.out.println();
            System.out.println(
                    "You do not have any playlists yet."
            );
            return null;
        }

        printPlaylists(playlists);

        System.out.println();
        System.out.print("Enter playlist ID: ");
        int playlistId = readInteger();

        boolean belongsToUser =
                playlistController.playlistBelongsToUser(
                        playlistId,
                        loggedInUser.getId()
                );

        if (!belongsToUser) {
            System.out.println();
            System.out.println(
                    "Playlist not found or you do not own it."
            );
            return null;
        }

        return playlistId;
    }

    /**
     * Checks whether a selected song exists
     * in the available song list.
     */
    private boolean songExists(
            List<Song> songs,
            int songId
    ) {

        for (Song song : songs) {
            if (song.getId() == songId) {
                return true;
            }
        }

        return false;
    }

    /**
     * Prevents duplicate songs from being inserted
     * into the same playlist.
     */
    private boolean songAlreadyInPlaylist(
            List<PlaylistSong> playlistSongs,
            int songId
    ) {

        if (playlistSongs == null) {
            return false;
        }

        for (PlaylistSong playlistSong : playlistSongs) {
            if (playlistSong.getSongId() == songId) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifies that the selected junction-table record belongs
     * to the list retrieved for the chosen playlist.
     */
    private boolean playlistSongRecordExists(
            List<PlaylistSong> playlistSongs,
            int playlistSongId
    ) {

        for (PlaylistSong playlistSong : playlistSongs) {
            if (playlistSong.getId() == playlistSongId) {
                return true;
            }
        }

        return false;
    }

    /**
     * Displays playlists in table format.
     */
    private void printPlaylists(
            List<Playlist> playlists
    ) {

        System.out.println();
        System.out.println(
                "----------------------------------------------------------------"
        );

        System.out.printf(
                "%-6s %-25s %-22s%n",
                "ID",
                "PLAYLIST NAME",
                "DATE CREATED"
        );

        System.out.println(
                "----------------------------------------------------------------"
        );

        for (Playlist playlist : playlists) {
            System.out.printf(
                    "%-6d %-25s %-22s%n",
                    playlist.getId(),
                    playlist.getName(),
                    formatDateTime(
                            playlist.getDateCreated()
                    )
            );
        }

        System.out.println(
                "----------------------------------------------------------------"
        );
    }

    /**
     * Displays songs inside a playlist.
     *
     * RECORD ID is the primary key of the playlist_songs
     * junction table and is used when removing a song.
     */
    private void printPlaylistSongs(
            List<PlaylistSong> playlistSongs
    ) {

        System.out.println();
        System.out.println(
                "----------------------------------------------------------------"
        );

        System.out.printf(
                "%-12s %-10s %-30s%n",
                "RECORD ID",
                "SONG ID",
                "SONG TITLE"
        );

        System.out.println(
                "----------------------------------------------------------------"
        );

        for (PlaylistSong playlistSong : playlistSongs) {
            System.out.printf(
                    "%-12d %-10d %-30s%n",
                    playlistSong.getId(),
                    playlistSong.getSongId(),
                    displayValue(
                            playlistSong.getSongTitle()
                    )
            );
        }

        System.out.println(
                "----------------------------------------------------------------"
        );
    }

    /**
     * Displays all available songs before the user
     * selects a song to add.
     */
    private void printAvailableSongs(
            List<Song> songs
    ) {

        System.out.println();
        System.out.println("===== AVAILABLE SONGS =====");

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        System.out.printf(
                "%-6s %-25s %-12s %-15s %-20s%n",
                "ID",
                "TITLE",
                "LENGTH",
                "GENRE",
                "ALBUM"
        );

        System.out.println(
                "--------------------------------------------------------------------------"
        );

        for (Song song : songs) {
            System.out.printf(
                    "%-6d %-25s %-12s %-15s %-20s%n",
                    song.getId(),
                    song.getTitle(),
                    song.getLength(),
                    displayValue(song.getGenre()),
                    displayValue(song.getAlbumName())
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );
    }

    /**
     * Formats the playlist creation date.
     */
    private String formatDateTime(
            LocalDateTime dateTime
    ) {

        if (dateTime == null) {
            return "N/A";
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm"
                );

        return dateTime.format(formatter);
    }

    /**
     * Prevents null or blank values from appearing
     * incorrectly in tables.
     */
    private String displayValue(String value) {

        if (value == null || value.isBlank()) {
            return "N/A";
        }

        return value;
    }

    /**
     * Safely reads a whole number.
     */
    private int readInteger() {

        while (true) {
            try {
                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {
                System.out.print(
                        "Invalid input. Enter a whole number: "
                );
            }
        }
    }

    /**
     * Pauses before returning to the playlist menu.
     */
    private void pause() {

        System.out.println();
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}