package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.model.Artist;
import com.joysistvi.recordingapp.model.Song;
import com.joysistvi.recordingapp.model.User;

import java.util.List;
import java.util.Scanner;

/**
 * Displays the dashboard for authenticated users.
 *
 * Available features:
 * - Browse songs
 * - Browse albums
 * - Browse artists
 * - Search songs
 * - Manage personal playlists
 */
public class UserDashboardView {

    private final SongController songController;
    private final AlbumController albumController;
    private final ArtistController artistController;
    private final UserPlaylistView userPlaylistView;
    private final Scanner scanner;

    /**
     * Constructor injection for the User Dashboard dependencies.
     */
    public UserDashboardView(
            SongController songController,
            AlbumController albumController,
            ArtistController artistController,
            UserPlaylistView userPlaylistView,
            Scanner scanner
    ) {
        this.songController = songController;
        this.albumController = albumController;
        this.artistController = artistController;
        this.userPlaylistView = userPlaylistView;
        this.scanner = scanner;
    }

    /**
     * Runs the User Dashboard until the user logs out.
     *
     * @param loggedInUser currently authenticated user
     */
    public void run(User loggedInUser) {

        int choice;

        do {
            showMenu();

            choice = readInteger();

            switch (choice) {

                case 1:
                    browseSongs();
                    pause();
                    break;

                case 2:
                    browseAlbums();
                    pause();
                    break;

                case 3:
                    browseArtists();
                    pause();
                    break;

                case 4:
                    searchSongs();
                    pause();
                    break;

                case 5:
                    userPlaylistView.run(loggedInUser);
                    break;

                case 0:
                    System.out.println(
                            "\nUser logged out successfully."
                    );
                    break;

                default:
                    System.out.println(
                            "\nInvalid option. Please choose from 0 to 5."
                    );
                    pause();
            }

        } while (choice != 0);
    }

    // Displays the User Dashboard menu
    private void showMenu() {

        System.out.println();
        System.out.println("===== USER DASHBOARD =====");
        System.out.println();
        System.out.println("1. Browse Songs");
        System.out.println("2. Browse Albums");
        System.out.println("3. Browse Artists");
        System.out.println("4. Search Songs");
        System.out.println("5. My Playlists");
        System.out.println("0. Logout");
        System.out.print("Enter your choice: ");
    }

    // Displays all active songs
    private void browseSongs() {

        System.out.println();
        System.out.println("===== BROWSE SONGS =====");

        List<Song> songs =
                songController.handleViewAllSongs();

        if (songs == null || songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        printSongs(songs);
    }

    // Displays all albums
    private void browseAlbums() {

        System.out.println();
        System.out.println("===== BROWSE ALBUMS =====");

        List<Album> albums =
                albumController.getAllAlbums();

        if (albums == null || albums.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }

        printAlbums(albums);
    }

    // Displays all artists
    private void browseArtists() {

        System.out.println();
        System.out.println("===== BROWSE ARTISTS =====");

        List<Artist> artists =
                artistController.getAllArtists();

        if (artists == null || artists.isEmpty()) {
            System.out.println("No artists found.");
            return;
        }

        printArtists(artists);
    }

    // Searches songs by title or keyword
    private void searchSongs() {

        System.out.println();
        System.out.println("===== SEARCH SONGS =====");
        System.out.println();

        System.out.print("Enter song title or keyword: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println(
                    "Search keyword cannot be empty."
            );
            return;
        }

        List<Song> songs =
                songController.handleSearchSong(keyword);

        if (songs == null || songs.isEmpty()) {
            System.out.println(
                    "No matching songs found."
            );
            return;
        }

        printSongs(songs);
    }

    // Displays songs in table format
    private void printSongs(List<Song> songs) {

        System.out.println();
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
                    song.getGenre(),
                    displayValue(song.getAlbumName())
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------"
        );
    }

    // Displays albums in table format
    private void printAlbums(List<Album> albums) {

        System.out.println();
        System.out.println(
                "----------------------------------------------------------------"
        );

        System.out.printf(
                "%-6s %-25s %-10s %-20s%n",
                "ID",
                "ALBUM NAME",
                "YEAR",
                "ARTIST"
        );

        System.out.println(
                "----------------------------------------------------------------"
        );

        for (Album album : albums) {
            System.out.printf(
                    "%-6d %-25s %-10d %-20s%n",
                    album.getId(),
                    album.getName(),
                    album.getYear(),
                    displayValue(album.getArtistName())
            );
        }

        System.out.println(
                "----------------------------------------------------------------"
        );
    }

    // Displays artists in table format
    private void printArtists(List<Artist> artists) {

        System.out.println();
        System.out.println(
                "----------------------------------------"
        );

        System.out.printf(
                "%-6s %-30s%n",
                "ID",
                "ARTIST NAME"
        );

        System.out.println(
                "----------------------------------------"
        );

        for (Artist artist : artists) {
            System.out.printf(
                    "%-6d %-30s%n",
                    artist.getId(),
                    artist.getName()
            );
        }

        System.out.println(
                "----------------------------------------"
        );
    }

    /**
     * Prevents null values from appearing as the word "null"
     * in the console table.
     */
    private String displayValue(String value) {

        if (value == null || value.isBlank()) {
            return "N/A";
        }

        return value;
    }

    // Safely reads an integer
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

    // Pauses before returning to the dashboard
    private void pause() {

        System.out.println();
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}