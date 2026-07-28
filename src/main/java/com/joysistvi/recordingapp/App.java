package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.cliview.ArtistView;
import com.joysistvi.recordingapp.cliview.SongView;
import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.controller.ArtistController;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.repository.ArtistRepository;
import com.joysistvi.recordingapp.repository.ArtistRepositoryImpl;
import com.joysistvi.recordingapp.repository.SongRepository;
import com.joysistvi.recordingapp.repository.SongRepositoryImpl;
import com.joysistvi.recordingapp.service.ArtistService;
import com.joysistvi.recordingapp.service.ArtistServiceImpl;
import com.joysistvi.recordingapp.service.SongService;
import com.joysistvi.recordingapp.service.SongServiceImpl;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DbConnection dbConnection = new DbConnection();

        // -------------------- Song Feature Wiring --------------------
        SongRepository songRepository = new SongRepositoryImpl(dbConnection);
        SongService songService = new SongServiceImpl(songRepository);
        SongController songController = new SongController(songService);
        SongView songView = new SongView(songController, scanner);

        // -------------------- Artist Feature Wiring --------------------
        ArtistRepository artistRepository = new ArtistRepositoryImpl(dbConnection);
        ArtistService artistService = new ArtistServiceImpl(artistRepository);
        ArtistController artistController = new ArtistController(artistService);
        ArtistView artistView = new ArtistView(artistController, scanner);

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
                    System.out.println("Album Management is not wired up yet.");
                    break;

                case 3:
                    artistView.showMenu();
                    break;

                case 4:
                    System.out.println("Playlist Management is not wired up yet.");
                    break;

                case 5:
                    System.out.println("User Management is not wired up yet.");
                    break;

                case 0:
                    System.out.println("Exiting Recording Studio App. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        scanner.close();
    }

    // Display the main menu
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

    // Read integer safely
    private static int readInt(Scanner scanner) {

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }

        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        return value;
    }

    // Clear console screen
    public static void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}