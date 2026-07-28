package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.Playlist;
import com.joysistvi.recordingapp.model.User;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class PlaylistView {

    private final PlaylistController playlistController;
    private final UserController userController;
    private final Scanner scanner;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd HH:mm:ss"
            );

    public PlaylistView(
            PlaylistController playlistController,
            UserController userController,
            Scanner scanner
    ) {
        this.playlistController = playlistController;
        this.userController = userController;
        this.scanner = scanner;
    }

    public void run() {

        int choice;

        do {
            clearScreen();
            printMenu();
            choice = readInt("Choice: ");

            switch (choice) {
                case 1:
                    viewAllPlaylists();
                    break;

                case 2:
                    searchPlaylist();
                    break;

                case 3:
                    addPlaylist();
                    break;

                case 4:
                    updatePlaylist();
                    break;

                case 5:
                    deletePlaylist();
                    break;

                case 0:
                    System.out.println(
                            "Returning to main menu..."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Try again."
                    );
                    pause();
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println(
                "\n===== PLAYLIST MANAGEMENT ====="
        );

        System.out.println("1. View All Playlists");
        System.out.println("2. Search Playlist");
        System.out.println("3. Add Playlist");
        System.out.println("4. Update Playlist");
        System.out.println("5. Delete Playlist");
        System.out.println("0. Back");
    }

    private void viewAllPlaylists() {

        clearScreen();

        System.out.println(
                "\n===== ALL PLAYLISTS ====="
        );

        List<Playlist> playlists =
                playlistController.getAllPlaylists();

        printPlaylists(playlists);

        pause();
    }

    private void searchPlaylist() {

        clearScreen();

        System.out.println(
                "\n===== SEARCH PLAYLIST ====="
        );

        System.out.print(
                "Enter playlist name or username: "
        );

        String keyword = scanner.nextLine();

        List<Playlist> playlists =
                playlistController.searchPlaylist(keyword);

        printPlaylists(playlists);

        pause();
    }

    private void addPlaylist() {

        clearScreen();

        System.out.println(
                "\n===== ADD PLAYLIST ====="
        );

        if (!displayAvailableUsers()) {
            pause();
            return;
        }

        System.out.print("Playlist Name: ");
        String name = scanner.nextLine();

        int userId = readInt("User ID: ");

        Playlist playlist =
                new Playlist(name, userId);

        boolean success =
                playlistController.addPlaylist(playlist);

        if (success) {
            System.out.println(
                    "Playlist added successfully."
            );
        } else {
            System.out.println(
                    "Failed to add playlist."
            );
        }

        pause();
    }

    private void updatePlaylist() {

        clearScreen();

        System.out.println(
                "\n===== UPDATE PLAYLIST ====="
        );

        List<Playlist> playlists =
                playlistController.getAllPlaylists();

        printPlaylists(playlists);

        if (playlists == null || playlists.isEmpty()) {
            pause();
            return;
        }

        int id = readInt(
                "Playlist ID to update: "
        );

        if (!displayAvailableUsers()) {
            pause();
            return;
        }

        System.out.print("New Playlist Name: ");
        String name = scanner.nextLine();

        int userId = readInt("New User ID: ");

        Playlist playlist =
                new Playlist(id, name, userId);

        boolean success =
                playlistController.updatePlaylist(playlist);

        if (success) {
            System.out.println(
                    "Playlist updated successfully."
            );
        } else {
            System.out.println(
                    "Failed to update playlist."
            );
        }

        pause();
    }

    private void deletePlaylist() {

        clearScreen();

        System.out.println(
                "\n===== DELETE PLAYLIST ====="
        );

        List<Playlist> playlists =
                playlistController.getAllPlaylists();

        printPlaylists(playlists);

        if (playlists == null || playlists.isEmpty()) {
            pause();
            return;
        }

        int id = readInt(
                "Playlist ID to delete: "
        );

        System.out.print(
                "Are you sure you want to delete this playlist? (Y/N): "
        );

        String confirmation =
                scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println(
                    "Playlist deletion cancelled."
            );
            pause();
            return;
        }

        boolean success =
                playlistController.deletePlaylist(id);

        if (success) {
            System.out.println(
                    "Playlist deleted successfully."
            );
        } else {
            System.out.println(
                    "Failed to delete playlist."
            );
        }

        pause();
    }

    private boolean displayAvailableUsers() {

        List<User> users =
                userController.getAllUsers();

        if (users == null || users.isEmpty()) {
            System.out.println(
                    "No users available."
            );
            return false;
        }

        System.out.println(
                "\n===== AVAILABLE USERS ====="
        );

        System.out.println(
                "+------+---------------------------+"
        );

        System.out.printf(
                "| %-4s | %-25s |%n",
                "ID",
                "Username"
        );

        System.out.println(
                "+------+---------------------------+"
        );

        for (User user : users) {
            System.out.printf(
                    "| %-4d | %-25s |%n",
                    user.getId(),
                    user.getUsername()
            );
        }

        System.out.println(
                "+------+---------------------------+"
        );

        return true;
    }

    private void printPlaylists(
            List<Playlist> playlists
    ) {

        if (playlists == null || playlists.isEmpty()) {
            System.out.println(
                    "No playlists found."
            );
            return;
        }

        System.out.println(
                "+------+----------------------+---------------------+----------+---------------------------+"
        );

        System.out.printf(
                "| %-4s | %-20s | %-19s | %-8s | %-25s |%n",
                "ID",
                "Playlist Name",
                "Date Created",
                "User ID",
                "Username"
        );

        System.out.println(
                "+------+----------------------+---------------------+----------+---------------------------+"
        );

        for (Playlist playlist : playlists) {

            String formattedDate =
                    playlist.getDateCreated() != null
                            ? playlist.getDateCreated()
                            .format(DATE_FORMATTER)
                            : "N/A";

            System.out.printf(
                    "| %-4d | %-20s | %-19s | %-8d | %-25s |%n",
                    playlist.getId(),
                    shortenText(
                            playlist.getName(),
                            20
                    ),
                    formattedDate,
                    playlist.getUserId(),
                    shortenText(
                            playlist.getUsername(),
                            25
                    )
            );
        }

        System.out.println(
                "+------+----------------------+---------------------+----------+---------------------------+"
        );
    }

    private String shortenText(
            String value,
            int maximumLength
    ) {

        if (value == null) {
            return "";
        }

        if (value.length() <= maximumLength) {
            return value;
        }

        return value.substring(
                0,
                maximumLength - 3
        ) + "...";
    }

    private int readInt(String prompt) {

        while (true) {

            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }

            System.out.println(
                    "Please enter a valid number."
            );

            scanner.nextLine();
        }
    }

    private void pause() {

        System.out.println(
                "\nPress Enter to continue..."
        );

        scanner.nextLine();
    }

    private void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}