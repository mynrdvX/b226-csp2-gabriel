package com.joysistvi.recordingapp;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.controller.SongController;
import com.joysistvi.recordingapp.repository.SongRepository;
import com.joysistvi.recordingapp.repository.SongRepositoryImpl;
import com.joysistvi.recordingapp.service.SongService;
import com.joysistvi.recordingapp.service.SongServiceImpl;
import com.joysistvi.recordingapp.cliview.SongView;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DbConnection dbConnection = new DbConnection();

        // ----- Song feature wiring -----
        SongRepository songRepository = new SongRepositoryImpl(dbConnection);
        SongService songService = new SongServiceImpl(songRepository);
        SongController songController = new SongController(songService);
        SongView songView = new SongView(songController, scanner);

        // ----- Album, Artist, Playlist, User features -----
        // Wire these up the same way once their Repository/Service/Controller/View
        // classes exist, e.g.:
        // AlbumRepository albumRepository = new AlbumRepositoryImpl(dbConnection);
        // AlbumService albumService = new AlbumServiceImpl(albumRepository);
        // AlbumController albumController = new AlbumController(albumService);
        // AlbumConsoleView albumView = new AlbumConsoleView(albumController, scanner);

        int choice;
        do {
            printMainMenu();
            choice = readInt(scanner);

            switch (choice) {
                case 1 -> songView.run();
                case 2 -> System.out.println("Album Management is not wired up yet.");
                case 3 -> System.out.println("Artist Management is not wired up yet.");
                case 4 -> System.out.println("Playlist Management is not wired up yet.");
                case 5 -> System.out.println("User Management is not wired up yet.");
                case 0 -> System.out.println("Exiting Recording Studio App. Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

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

    // Reads an int safely, re-prompting on invalid input, then consumes the trailing newline
    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline
        return value;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}