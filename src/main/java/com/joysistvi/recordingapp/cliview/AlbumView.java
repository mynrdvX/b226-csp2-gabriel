package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.model.Album;
import com.joysistvi.recordingapp.model.Artist;

import java.util.List;
import java.util.Scanner;

public class AlbumView {

    private final AlbumController albumController;
    private final ArtistController artistController;
    private final Scanner scanner;

    // Constructor injection
    public AlbumView(
            AlbumController albumController,
            ArtistController artistController,
            Scanner scanner
    ) {
        this.albumController = albumController;
        this.artistController = artistController;
        this.scanner = scanner;
    }

    public void run() {
        int choice;

        do {
            clearScreen();
            printMenu();
            choice = promptChoice();

            switch (choice) {
                case 1 -> viewAllAlbums();
                case 2 -> searchAlbum();
                case 3 -> addAlbum();
                case 4 -> updateAlbum();
                case 5 -> deleteAlbum();
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Try again.");
            }

            if (choice != 0) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printMenu() {
        clearScreen();

        System.out.println("\n===== ALBUM MANAGEMENT =====");
        System.out.println("1. View All Albums");
        System.out.println("2. Search Album");
        System.out.println("3. Add Album");
        System.out.println("4. Update Album");
        System.out.println("5. Delete Album");
        System.out.println("0. Back");
    }

    private int promptChoice() {
        System.out.print("Choice: ");
        return readInt();
    }

    private void viewAllAlbums() {
        List<Album> albums = albumController.getAllAlbums();
        printAlbums(albums);
    }

    private void searchAlbum() {
        System.out.print("Enter album or artist keyword: ");
        String keyword = scanner.nextLine();

        List<Album> albums = albumController.searchAlbum(keyword);
        printAlbums(albums);
    }

    private void addAlbum() {
        System.out.println("\n===== ADD ALBUM =====");

        displayAvailableArtists();

        System.out.print("Album Name: ");
        String name = scanner.nextLine();

        System.out.print("Release Year: ");
        int year = readInt();

        System.out.print("Artist ID: ");
        int artistId = readInt();

        Album album = new Album(name, year, artistId);

        boolean success = albumController.addAlbum(album);

        System.out.println(
                success
                        ? "Album added successfully."
                        : "Failed to add album."
        );
    }

    private void updateAlbum() {
        System.out.println("\n===== UPDATE ALBUM =====");

        viewAllAlbums();
        displayAvailableArtists();

        System.out.print("Album ID to update: ");
        int id = readInt();

        System.out.print("New Album Name: ");
        String name = scanner.nextLine();

        System.out.print("New Release Year: ");
        int year = readInt();

        System.out.print("New Artist ID: ");
        int artistId = readInt();

        Album album = new Album(id, name, year, artistId);

        boolean success = albumController.updateAlbum(album);

        System.out.println(
                success
                        ? "Album updated successfully."
                        : "Failed to update album."
        );
    }

    private void deleteAlbum() {
        System.out.println("\n===== DELETE ALBUM =====");

        viewAllAlbums();

        System.out.print("Album ID to delete: ");
        int id = readInt();

        System.out.print("Are you sure you want to delete this album? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Album deletion cancelled.");
            return;
        }

        boolean success = albumController.deleteAlbum(id);

        System.out.println(
                success
                        ? "Album deleted successfully."
                        : "Failed to delete album."
        );
    }

    private void displayAvailableArtists() {
        List<Artist> artists = artistController.getAllArtists();

        if (artists.isEmpty()) {
            System.out.println("No artists available.");
            return;
        }

        String border = "+"
                + "-".repeat(6)
                + "+"
                + "-".repeat(27)
                + "+";

        System.out.println("\nAVAILABLE ARTISTS");
        System.out.println(border);
        System.out.printf("| %-4s | %-25s |%n", "ID", "Artist");
        System.out.println(border);

        for (Artist artist : artists) {
            System.out.printf(
                    "| %-4d | %-25s |%n",
                    artist.getId(),
                    artist.getName()
            );
        }

        System.out.println(border);
    }

    private void printAlbums(List<Album> albums) {
        if (albums.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }

        String border = "+"
                + "-".repeat(6)
                + "+"
                + "-".repeat(27)
                + "+"
                + "-".repeat(8)
                + "+"
                + "-".repeat(27)
                + "+";

        System.out.println(border);
        System.out.printf(
                "| %-4s | %-25s | %-6s | %-25s |%n",
                "ID",
                "Album",
                "Year",
                "Artist"
        );
        System.out.println(border);

        for (Album album : albums) {
            System.out.printf(
                    "| %-4d | %-25s | %-6d | %-25s |%n",
                    album.getId(),
                    album.getName(),
                    album.getYear(),
                    album.getArtistName()
            );
        }

        System.out.println(border);
    }

    // Reads an integer safely and consumes the trailing newline
    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }

        int value = scanner.nextInt();
        scanner.nextLine();

        return value;
    }
}