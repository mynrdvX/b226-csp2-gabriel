package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.AlbumView;
import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.cliview.SongView;
import com.joysistvi.recordingapp.cliview.UserView;

import com.joysistvi.recordingapp.config.DbConnection;

import com.joysistvi.recordingapp.controller.AlbumController;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.controller.UserController;

import com.joysistvi.recordingapp.repository.AlbumRepository;
import com.joysistvi.recordingapp.repository.AlbumRepositoryImpl;
import com.joysistvi.recordingapp.repository.ArtistRepository;
import com.joysistvi.recordingapp.repository.ArtistRepositoryImpl;
import com.joysistvi.recordingapp.repository.SongRepository;
import com.joysistvi.recordingapp.repository.SongRepositoryImpl;
import com.joysistvi.recordingapp.repository.UserRepository;
import com.joysistvi.recordingapp.repository.UserRepositoryImpl;

import com.joysistvi.recordingapp.service.AlbumService;
import com.joysistvi.recordingapp.service.AlbumServiceImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.service.SongService;
import com.joysistvi.recordingapp.service.SongServiceImpl;
import com.joysistvi.recordingapp.service.UserService;
import com.joysistvi.recordingapp.service.UserServiceImpl;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DbConnection dbConnection = new DbConnection();

        // -------------------- Song Feature Wiring --------------------
        SongRepository songRepository =
                new SongRepositoryImpl(dbConnection);

        SongService songService =
                new SongServiceImpl(songRepository);

        SongController songController =
                new SongController(songService);

        SongView songView =
                new SongView(songController, scanner);

        // -------------------- Artist Feature Wiring --------------------
        ArtistRepository artistRepository =
                new ArtistRepositoryImpl(dbConnection);

        ArtistService artistService =
                new ArtistServiceImpl(artistRepository);

        ArtistController artistController =
                new ArtistController(artistService);

        ArtistView artistView =
                new ArtistView(artistController, scanner);

        // -------------------- Album Feature Wiring --------------------
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

        // -------------------- User Feature Wiring --------------------
        UserRepository userRepository =
                new UserRepositoryImpl(dbConnection);

        UserService userService =
                new UserServiceImpl(userRepository);

        UserController userController =
                new UserController(userService);

        UserView userView =
                new UserView(userController, scanner);

        // -------------------- Main Menu --------------------
        int choice;

        do {
            printMainMenu();
            choice = readInt(scanner);

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
                    System.out.println(
                            "Playlist Management is not wired up yet."
                    );
                    pause(scanner);
                    break;

                case 5:
                    userView.run();
                    break;

                case 0:
                    System.out.println(
                            "Exiting Recording Studio App. Goodbye!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Try again."
                    );
                    pause(scanner);
            }

        } while (choice != 0);

        scanner.close();
    }

    // Displays the main menu
    private static void printMainMenu() {

        clearScreen();

        System.out.println("\n===== RECORDING STUDIO APP =====");
        System.out.println("1. Song Management");
        System.out.println("2. Album Management");
        System.out.println("3. Artist Management");
        System.out.println("4. Playlist Management");
        System.out.println("5. User Management");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    // Reads an integer safely
    private static int readInt(Scanner scanner) {

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.nextLine();
        }

        int value = scanner.nextInt();
        scanner.nextLine();

        return value;
    }

    // Pauses the program before returning to the menu
    private static void pause(Scanner scanner) {

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    // Clears the console screen
    public static void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}