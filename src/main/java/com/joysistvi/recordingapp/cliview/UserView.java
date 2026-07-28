package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.List;
import java.util.Scanner;

public class UserView {

    private final UserController userController;
    private final Scanner scanner;

    public UserView(UserController userController, Scanner scanner) {
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
                    viewAllUsers();
                    break;

                case 2:
                    searchUser();
                    break;

                case 3:
                    addUser();
                    break;

                case 4:
                    updateUser();
                    break;

                case 5:
                    deleteUser();
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
                    pause();
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println("\n===== USER MANAGEMENT =====");
        System.out.println("1. View All Users");
        System.out.println("2. Search User");
        System.out.println("3. Add User");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("0. Back");
    }

    private void viewAllUsers() {

        clearScreen();
        System.out.println("\n===== ALL USERS =====");

        List<User> users = userController.getAllUsers();
        printUsers(users);

        pause();
    }

    private void searchUser() {

        clearScreen();
        System.out.println("\n===== SEARCH USER =====");

        System.out.print("Enter username keyword: ");
        String keyword = scanner.nextLine();

        List<User> users = userController.searchUser(keyword);
        printUsers(users);

        pause();
    }

    private void addUser() {

        clearScreen();
        System.out.println("\n===== ADD USER =====");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);

        boolean success = userController.addUser(user);

        if (success) {
            System.out.println("User added successfully.");
        } else {
            System.out.println("Failed to add user.");
        }

        pause();
    }

    private void updateUser() {

        clearScreen();
        System.out.println("\n===== UPDATE USER =====");

        List<User> users = userController.getAllUsers();
        printUsers(users);

        if (users.isEmpty()) {
            pause();
            return;
        }

        int id = readInt("User ID to update: ");

        System.out.print("New Username: ");
        String username = scanner.nextLine();

        System.out.print("New Password: ");
        String password = scanner.nextLine();

        User user = new User(id, username, password);

        boolean success = userController.updateUser(user);

        if (success) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Failed to update user.");
        }

        pause();
    }

    private void deleteUser() {

        clearScreen();
        System.out.println("\n===== DELETE USER =====");

        List<User> users = userController.getAllUsers();
        printUsers(users);

        if (users.isEmpty()) {
            pause();
            return;
        }

        int id = readInt("User ID to delete: ");

        System.out.print(
                "Are you sure you want to delete this user? (Y/N): "
        );

        String confirmation = scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("User deletion cancelled.");
            pause();
            return;
        }

        boolean success = userController.deleteUser(id);

        if (success) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Failed to delete user.");
        }

        pause();
    }

    private void printUsers(List<User> users) {

        if (users == null || users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("+------+---------------------------+");
        System.out.printf(
                "| %-4s | %-25s |%n",
                "ID",
                "Username"
        );
        System.out.println("+------+---------------------------+");

        for (User user : users) {
            System.out.printf(
                    "| %-4d | %-25s |%n",
                    user.getId(),
                    user.getUsername()
            );
        }

        System.out.println("+------+---------------------------+");
    }

    private int readInt(String prompt) {

        while (true) {

            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }

            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }
    }

    private void pause() {

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void clearScreen() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}