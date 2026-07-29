package com.joysistvi.recordingapp.cliview;

import java.util.Scanner;

/**
 * Displays the Admin Dashboard.
 *
 * This class reuses the existing Session 2 management views:
 * - SongView
 * - AlbumView
 * - ArtistView
 * - UserView
 */
public class AdminDashboardView {

    private final SongView songView;
    private final AlbumView albumView;
    private final ArtistView artistView;
    private final UserView userView;
    private final Scanner scanner;

    /**
     * Constructor injection for all Admin Dashboard dependencies.
     *
     * @param songView   existing Song Management view
     * @param albumView  existing Album Management view
     * @param artistView existing Artist Management view
     * @param userView   existing User Management view
     * @param scanner    shared Scanner from App.java
     */
    public AdminDashboardView(
            SongView songView,
            AlbumView albumView,
            ArtistView artistView,
            UserView userView,
            Scanner scanner
    ) {
        this.songView = songView;
        this.albumView = albumView;
        this.artistView = artistView;
        this.userView = userView;
        this.scanner = scanner;
    }

    /**
     * Runs the Admin Dashboard until the administrator chooses Logout.
     */
    public void run() {

        boolean loggedIn = true;

        while (loggedIn) {

            showMenu();

            int choice = readInt();

            switch (choice) {

                case 1:
                    songView.run();
                    break;

                case 2:
                    albumView.run();
                    break;

                case 3:
                    artistView.showMenu();
                    break;

                case 4:
                    userView.run();
                    break;

                case 0:
                    loggedIn = false;
                    System.out.println(
                            "\nAdmin logged out successfully."
                    );
                    break;

                default:
                    System.out.println(
                            "\nInvalid option. Please choose from 0 to 4."
                    );
            }
        }
    }

    /**
     * Displays the Admin Dashboard menu.
     */
    private void showMenu() {

        clearScreen();

        System.out.println();
        System.out.println("===== ADMIN DASHBOARD =====");
        System.out.println();
        System.out.println("1. Song Management");
        System.out.println("2. Album Management");
        System.out.println("3. Artist Management");
        System.out.println("4. User Management");
        System.out.println("0. Logout");
        System.out.print("Choice: ");
    }

    /**
     * Safely reads a whole-number menu choice.
     *
     * @return valid integer entered by the user
     */
    private int readInt() {

        while (!scanner.hasNextInt()) {

            System.out.print(
                    "Please enter a valid number: "
            );

            scanner.nextLine();
        }

        int number = scanner.nextInt();
        scanner.nextLine();

        return number;
    }

    /**
     * Adds blank lines to separate console screens.
     */
    private void clearScreen() {

        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}