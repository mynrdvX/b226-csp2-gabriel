package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.AdminDashboardView;
import com.joysistvi.recordingapp.cliview.AlbumView;
import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.cliview.AuthView;
import com.joysistvi.recordingapp.cliview.SongView;
import com.joysistvi.recordingapp.cliview.UserDashboardView;
import com.joysistvi.recordingapp.cliview.UserPlaylistView;
import com.joysistvi.recordingapp.cliview.UserView;

import com.joysistvi.recordingapp.config.DbConnection;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.PlaylistController;
import com.joysistvi.recordingapp.controller.PlaylistSongController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.controller.UserController;

import com.joysistvi.recordingapp.model.User;

import com.joysistvi.recordingapp.repository.AlbumRepository;
import com.joysistvi.recordingapp.repository.AlbumRepositoryImpl;
import com.joysistvi.recordingapp.repository.ArtistRepository;
import com.joysistvi.recordingapp.repository.ArtistRepositoryImpl;
import com.joysistvi.recordingapp.repository.PlaylistRepository;
import com.joysistvi.recordingapp.repository.PlaylistRepositoryImpl;
import com.joysistvi.recordingapp.repository.PlaylistSongRepository;
import com.joysistvi.recordingapp.repository.PlaylistSongRepositoryImpl;
import com.joysistvi.recordingapp.repository.SongRepository;
import com.joysistvi.recordingapp.repository.SongRepositoryImpl;
import com.joysistvi.recordingapp.repository.UserRepository;
import com.joysistvi.recordingapp.repository.UserRepositoryImpl;

import com.joysistvi.recordingapp.service.AlbumService;
import com.joysistvi.recordingapp.service.AlbumServiceImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.service.PlaylistService;
import com.joysistvi.recordingapp.service.PlaylistServiceImpl;
import com.joysistvi.recordingapp.service.PlaylistSongService;
import com.joysistvi.recordingapp.service.PlaylistSongServiceImpl;
import com.joysistvi.recordingapp.service.SongService;
import com.joysistvi.recordingapp.service.SongServiceImpl;
import com.joysistvi.recordingapp.service.UserService;
import com.joysistvi.recordingapp.service.UserServiceImpl;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DbConnection dbConnection = new DbConnection();

        /*
         * =========================================================
         * SONG MODULE
         * =========================================================
         */

        SongRepository songRepository =
                new SongRepositoryImpl(dbConnection);

        SongService songService =
                new SongServiceImpl(songRepository);

        SongController songController =
                new SongController(songService);

        SongView songView =
                new SongView(
                        songController,
                        scanner
                );

        /*
         * =========================================================
         * ARTIST MODULE
         * =========================================================
         */

        ArtistRepository artistRepository =
                new ArtistRepositoryImpl(dbConnection);

        ArtistService artistService =
                new ArtistServiceImpl(artistRepository);

        ArtistController artistController =
                new ArtistController(artistService);

        ArtistView artistView =
                new ArtistView(
                        artistController,
                        scanner
                );

        /*
         * =========================================================
         * ALBUM MODULE
         * =========================================================
         */

        AlbumRepository albumRepository =
                new AlbumRepositoryImpl(dbConnection);

        AlbumService albumService =
                new AlbumServiceImpl(albumRepository);

        AlbumController albumController =
                new AlbumController(albumService);

        AlbumView albumView =
                new AlbumView(
                        albumController,
                        artistController,
                        scanner
                );

        /*
         * =========================================================
         * USER AND AUTHENTICATION MODULE
         * =========================================================
         */

        UserRepository userRepository =
                new UserRepositoryImpl(dbConnection);

        UserService userService =
                new UserServiceImpl(userRepository);

        UserController userController =
                new UserController(userService);

        UserView userView =
                new UserView(
                        userController,
                        scanner
                );

        AuthView authView =
                new AuthView(
                        userController,
                        scanner
                );

        /*
         * =========================================================
         * PLAYLIST MODULE
         * =========================================================
         */

        PlaylistRepository playlistRepository =
                new PlaylistRepositoryImpl(dbConnection);

        PlaylistService playlistService =
                new PlaylistServiceImpl(
                        playlistRepository
                );

        PlaylistController playlistController =
                new PlaylistController(
                        playlistService
                );

        /*
         * =========================================================
         * PLAYLIST-SONG MODULE
         * =========================================================
         */

        PlaylistSongRepository playlistSongRepository =
                new PlaylistSongRepositoryImpl(dbConnection);

        PlaylistSongService playlistSongService =
                new PlaylistSongServiceImpl(
                        playlistSongRepository
                );

        PlaylistSongController playlistSongController =
                new PlaylistSongController(
                        playlistSongService
                );

        /*
         * =========================================================
         * USER PLAYLIST VIEW
         * =========================================================
         */

        UserPlaylistView userPlaylistView =
                new UserPlaylistView(
                        playlistController,
                        playlistSongController,
                        songController,
                        scanner
                );

        /*
         * =========================================================
         * ADMIN DASHBOARD
         * =========================================================
         */

        AdminDashboardView adminDashboardView =
                new AdminDashboardView(
                        songView,
                        albumView,
                        artistView,
                        userView,
                        scanner
                );

        /*
         * =========================================================
         * USER DASHBOARD
         * =========================================================
         */

        UserDashboardView userDashboardView =
                new UserDashboardView(
                        songController,
                        albumController,
                        artistController,
                        userPlaylistView,
                        scanner
                );

        /*
         * =========================================================
         * APPLICATION AUTHENTICATION MENU
         * =========================================================
         */

        boolean applicationRunning = true;

        while (applicationRunning) {

            showAuthenticationMenu();

            int choice = readInteger(scanner);

            switch (choice) {

                case 1:
                    handleLogin(
                            authView,
                            adminDashboardView,
                            userDashboardView
                    );
                    break;

                case 2:
                    authView.register();
                    pause(scanner);
                    break;

                case 0:
                    applicationRunning = false;

                    System.out.println();
                    System.out.println(
                            "Exiting Recording Studio App. Goodbye!"
                    );
                    break;

                default:
                    System.out.println();
                    System.out.println(
                            "Invalid option. Please choose from 0 to 2."
                    );
                    pause(scanner);
            }
        }

        scanner.close();
    }

    /**
     * Processes login and sends the authenticated user
     * to the correct dashboard based on their role.
     */
    private static void handleLogin(
            AuthView authView,
            AdminDashboardView adminDashboardView,
            UserDashboardView userDashboardView
    ) {

        User loggedInUser = authView.login();

        if (loggedInUser == null) {
            return;
        }

        if (loggedInUser.isAdmin()) {

            adminDashboardView.run();

        } else if (loggedInUser.isUser()) {

            userDashboardView.run(loggedInUser);

        } else {

            System.out.println();
            System.out.println(
                    "Access denied. The account has an invalid role: "
                            + loggedInUser.getRole()
            );
        }
    }

    /**
     * Displays the application's authentication menu.
     */
    private static void showAuthenticationMenu() {

        clearScreen();

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println(
                "       RECORDING STUDIO APPLICATION"
        );
        System.out.println(
                "========================================"
        );
        System.out.println();
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter your choice: ");
    }

    /**
     * Safely reads a whole-number menu choice.
     */
    private static int readInteger(Scanner scanner) {

        while (true) {

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.print(
                        "Invalid input. Enter a whole number: "
                );
            }
        }
    }

    /**
     * Pauses the program before returning to the menu.
     */
    private static void pause(Scanner scanner) {

        System.out.println();
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Adds blank lines to separate console screens.
     */
    private static void clearScreen() {

        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}