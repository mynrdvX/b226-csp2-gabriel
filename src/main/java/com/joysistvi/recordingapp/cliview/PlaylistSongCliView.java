package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.model.PlaylistSong;

import java.util.List;
import java.util.Scanner;

public class PlaylistSongCliView {

    private final PlaylistSongController playlistSongController;
    private final Scanner scanner;

    public PlaylistSongCliView(
            PlaylistSongController playlistSongController,
            Scanner scanner
    ) {
        this.playlistSongController = playlistSongController;
        this.scanner = scanner;
    }

    public void run() {

        int choice;

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewSongsInPlaylist();
                    break;

                case 2:
                    addSongToPlaylist();
                    break;

                case 3:
                    removeSongFromPlaylist();
                    break;

                case 0:
                    System.out.println("Returning to the main menu...");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please select from 0 to 3."
                    );
            }

        } while (choice != 0);
    }

    private void displayMenu() {

        System.out.println();
        System.out.println(
                "======================================"
        );
        System.out.println(
                "     PLAYLIST SONG MANAGEMENT"
        );
        System.out.println(
                "======================================"
        );
        System.out.println(
                "1. View Songs in Playlist"
        );
        System.out.println(
                "2. Add Song to Playlist"
        );
        System.out.println(
                "3. Remove Song from Playlist"
        );
        System.out.println(
                "0. Back"
        );
        System.out.println(
                "======================================"
        );
    }

    private void viewSongsInPlaylist() {

        System.out.println();
        System.out.println(
                "===== VIEW SONGS IN PLAYLIST ====="
        );

        int playlistId = readInt(
                "Enter Playlist ID: "
        );

        List<PlaylistSong> playlistSongs =
                playlistSongController
                        .getSongsByPlaylistId(playlistId);

        if (playlistSongs.isEmpty()) {
            System.out.println(
                    "No songs found in this playlist."
            );
            return;
        }

        String playlistName =
                playlistSongs.get(0).getPlaylistName();

        System.out.println();
        System.out.println(
                "Playlist: " + playlistName
        );

        System.out.printf(
                "%-12s %-10s %-30s%n",
                "Junction ID",
                "Song ID",
                "Song Title"
        );

        System.out.println(
                "------------------------------------------------------"
        );

        for (PlaylistSong playlistSong : playlistSongs) {

            System.out.printf(
                    "%-12d %-10d %-30s%n",
                    playlistSong.getId(),
                    playlistSong.getSongId(),
                    playlistSong.getSongTitle()
            );
        }
    }

    private void addSongToPlaylist() {

        System.out.println();
        System.out.println(
                "===== ADD SONG TO PLAYLIST ====="
        );

        int playlistId = readInt(
                "Enter Playlist ID: "
        );

        int songId = readInt(
                "Enter Song ID: "
        );

        PlaylistSong playlistSong =
                new PlaylistSong(
                        playlistId,
                        songId
                );

        boolean added =
                playlistSongController
                        .addSongToPlaylist(playlistSong);

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

    private void removeSongFromPlaylist() {

        System.out.println();
        System.out.println(
                "===== REMOVE SONG FROM PLAYLIST ====="
        );

        List<PlaylistSong> playlistSongs =
                playlistSongController
                        .getAllPlaylistSongs();

        if (playlistSongs.isEmpty()) {
            System.out.println(
                    "No playlist-song records found."
            );
            return;
        }

        displayAllPlaylistSongs(playlistSongs);

        int id = readInt(
                "Enter Junction ID to remove: "
        );

        PlaylistSong selectedRecord = null;

        for (PlaylistSong playlistSong : playlistSongs) {
            if (playlistSong.getId() == id) {
                selectedRecord = playlistSong;
                break;
            }
        }

        if (selectedRecord == null) {
            System.out.println(
                    "Playlist-song record was not found."
            );
            return;
        }

        System.out.println();
        System.out.println(
                "Selected Record"
        );
        System.out.println(
                "-----------------------------"
        );
        System.out.println(
                "Playlist : " + selectedRecord.getPlaylistName()
        );
        System.out.println(
                "Song     : " + selectedRecord.getSongTitle()
        );
        System.out.println();

        String confirmation;

        while (true) {

            System.out.print(
                    "Are you sure you want to remove this song? (Y/N): "
            );

            confirmation = scanner.nextLine().trim();

            if (confirmation.equalsIgnoreCase("Y")
                    || confirmation.equalsIgnoreCase("N")) {
                break;
            }

            System.out.println(
                    "Invalid choice. Please enter Y or N only."
            );
        }

        if (confirmation.equalsIgnoreCase("N")) {
            System.out.println(
                    "Removal cancelled."
            );
            return;
        }

        boolean removed =
                playlistSongController
                        .removeSongFromPlaylist(id);

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

    private void displayAllPlaylistSongs(
            List<PlaylistSong> playlistSongs
    ) {

        System.out.println();

        System.out.printf(
                "%-12s %-12s %-25s %-10s %-30s%n",
                "Junction ID",
                "Playlist ID",
                "Playlist Name",
                "Song ID",
                "Song Title"
        );

        System.out.println(
                "---------------------------------------------------------------------------------------------"
        );

        for (PlaylistSong playlistSong : playlistSongs) {

            System.out.printf(
                    "%-12d %-12d %-25s %-10d %-30s%n",
                    playlistSong.getId(),
                    playlistSong.getPlaylistId(),
                    playlistSong.getPlaylistName(),
                    playlistSong.getSongId(),
                    playlistSong.getSongTitle()
            );
        }
    }

    private int readInt(String message) {

        while (true) {
            System.out.print(message);

            String input = scanner.nextLine();

            try {
                return Integer.parseInt(
                        input.trim()
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a whole number."
                );
            }
        }
    }
}