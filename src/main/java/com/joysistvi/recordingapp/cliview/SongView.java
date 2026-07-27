
//SongView.java
package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.model.Song;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SongView {

    private final SongController songController; // Composition
    private final Scanner scanner;

    // Constructor injection
    public SongView(SongController songController, Scanner scanner) {
        this.songController = songController;
        this.scanner = scanner;
    }

    public void run() {
        int choice;
        do {
            clearScreen();
            printMenu();
            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllSongs();
                case 2 -> searchSong();
                case 3 -> addSong();
                case 4 -> updateSong();
                case 5 -> deleteSong();
                case 6 -> archiveSong();
                case 7 -> restoreSong();
                case 8 -> viewArchivedSongs();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } while (choice != 0);
    }

    // Clears the console using the OS's native command: "cls" on Windows, "clear"
    // on Mac/Linux. Works when run from an actual terminal. Note: this has no
    // visible effect in IntelliJ's built-in Run console since it isn't a real
    // OS terminal — run the app from a terminal window to see it in action.
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printMenu() {
        clearScreen();
        System.out.println("\n===== SONG MANAGEMENT =====");
        System.out.println("1. View All Songs");
        System.out.println("2. Search Song");
        System.out.println("3. Add Song");
        System.out.println("4. Update Song");
        System.out.println("5. Delete Song");
        System.out.println("6. Archive Song");
        System.out.println("7. Restore Song");
        System.out.println("8. View Archived Songs");
        System.out.println("0. Back");
    }

    private int promptChoice() {
        System.out.print("Choice: ");
        return readInt();
    }

    private void viewAllSongs() {
        List<Song> songs = songController.handleViewAllSongs();
        printSongs(songs);
    }

    private void viewArchivedSongs() {
        List<Song> songs = songController.handleViewArchivedSongs();
        printSongs(songs);
    }

    private void searchSong() {
        System.out.print("Enter title keyword: ");
        String keyword = scanner.nextLine();
        printSongs(songController.handleSearchSong(keyword));
    }

    private void addSong() {
        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Length (e.g. 3:45): ");
        String length = scanner.nextLine();

        System.out.print("Genre: ");
        String genre = scanner.nextLine();

        System.out.print("Album ID: ");
        int albumId = readInt();

        Song song = new Song(title, length, genre, albumId);

        boolean success = songController.handleAddSong(song);
        System.out.println(success ? "Song added successfully." : "Failed to add song.");
    }

    private void updateSong() {
        System.out.print("Song ID to update: ");
        int id = readInt();

        System.out.print("New Title: ");
        String title = scanner.nextLine();

        System.out.print("New Length (e.g. 3:45): ");
        String length = scanner.nextLine();

        System.out.print("New Genre: ");
        String genre = scanner.nextLine();

        System.out.print("New Album ID: ");
        int albumId = readInt();

        Song song = new Song(id, title, length, genre, albumId);

        boolean success = songController.handleUpdateSong(song);
        System.out.println(success ? "Song updated successfully." : "Failed to update song.");
    }

    private void deleteSong() {
        System.out.print("Song ID to delete: ");
        int id = readInt();

        boolean success = songController.handleDeleteSong(id);
        System.out.println(success ? "Song deleted successfully." : "Failed to delete song.");
    }

    private void archiveSong() {
        System.out.print("Song ID to archive: ");
        int id = readInt();

        boolean success = songController.handleArchiveSong(id);
        System.out.println(success ? "Song archived successfully." : "Failed to archive song.");
    }

    private void restoreSong() {
        System.out.print("Song ID to restore: ");
        int id = readInt();

        boolean success = songController.handleRestoreSong(id);
        System.out.println(success ? "Song restored successfully." : "Failed to restore song.");
    }

    private void printSongs(List<Song> songs) {
        if (songs.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }

        String border = "+" + "-".repeat(6) + "+" + "-".repeat(27) + "+"
                + "-".repeat(10) + "+" + "-".repeat(14) + "+" + "-".repeat(17) + "+";

        System.out.println(border);
        System.out.printf("| %-4s | %-25s | %-8s | %-12s | %-15s |%n",
                "ID", "Title", "Length", "Genre", "Album");
        System.out.println(border);

        for (Song song : songs) {
            System.out.printf("| %-4d | %-25s | %-8s | %-12s | %-15s |%n",
                    song.getId(), song.getTitle(), song.getLength(), song.getGenre(), song.getAlbumName());
        }

        System.out.println(border);
    }

    // Reads an int safely, re-prompting on invalid input, then consumes the trailing newline
    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return value;
    }
}