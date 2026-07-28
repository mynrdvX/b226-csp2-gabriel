package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.model.Artist;

import java.util.List;
import java.util.Scanner;

public class ArtistView {

    private final ArtistController artistController;
    private final Scanner scanner;

    // Constructor injection
    public ArtistView(ArtistController artistController, Scanner scanner) {
        this.artistController = artistController;
        this.scanner = scanner;
    }

    // Main Artist Management menu
    public void showMenu() {

        int choice;

        do {
            System.out.println("\n===== ARTIST MANAGEMENT =====");
            System.out.println("1. View All Artists");
            System.out.println("2. Search Artist");
            System.out.println("3. Add Artist");
            System.out.println("4. Update Artist");
            System.out.println("5. Delete Artist");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            choice = readInteger();

            switch (choice) {
                case 1:
                    viewAllArtists();
                    break;

                case 2:
                    searchArtist();
                    break;

                case 3:
                    addArtist();
                    break;

                case 4:
                    updateArtist();
                    break;

                case 5:
                    deleteArtist();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }

    // Display all artists
    private void viewAllArtists() {

        List<Artist> artists = artistController.getAllArtists();

        if (artists.isEmpty()) {
            System.out.println("No artists found.");
            return;
        }

        printArtists(artists);
    }

    // Search artist by name
    private void searchArtist() {

        System.out.print("Enter artist name or keyword: ");
        String keyword = scanner.nextLine();

        List<Artist> artists = artistController.searchArtist(keyword);

        if (artists.isEmpty()) {
            System.out.println("No matching artists found.");
            return;
        }

        printArtists(artists);
    }

    // Add a new artist
    private void addArtist() {

        System.out.print("Enter artist name: ");
        String name = scanner.nextLine();

        Artist artist = new Artist(name);

        boolean added = artistController.addArtist(artist);

        if (added) {
            System.out.println("Artist added successfully.");
        } else {
            System.out.println("Failed to add artist.");
        }
    }

    // Update an existing artist
    private void updateArtist() {

        viewAllArtists();

        System.out.print("Enter artist ID to update: ");
        int id = readInteger();

        System.out.print("Enter new artist name: ");
        String name = scanner.nextLine();

        Artist artist = new Artist(id, name);

        boolean updated = artistController.updateArtist(artist);

        if (updated) {
            System.out.println("Artist updated successfully.");
        } else {
            System.out.println("Artist not found or update failed.");
        }
    }

    // Delete an artist
    private void deleteArtist() {

        viewAllArtists();

        System.out.print("Enter artist ID to delete: ");
        int id = readInteger();

        System.out.print("Are you sure you want to delete this artist? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Delete operation cancelled.");
            return;
        }

        boolean deleted = artistController.deleteArtist(id);

        if (deleted) {
            System.out.println("Artist deleted successfully.");
        } else {
            System.out.println("Artist not found or cannot be deleted.");
        }
    }

    // Display artists in table format
    private void printArtists(List<Artist> artists) {

        System.out.println("\n-------------------------------");
        System.out.printf("%-10s %-20s%n", "ID", "ARTIST NAME");
        System.out.println("-------------------------------");

        for (Artist artist : artists) {
            System.out.printf(
                    "%-10d %-20s%n",
                    artist.getId(),
                    artist.getName()
            );
        }

        System.out.println("-------------------------------");
    }

    // Safely read integer input
    private int readInteger() {

        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                return number;

            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a whole number: ");
            }
        }
    }
}