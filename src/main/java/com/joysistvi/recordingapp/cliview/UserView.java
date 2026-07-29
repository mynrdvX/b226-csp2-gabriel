package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.List;
import java.util.Scanner;

public class UserView {

    private final UserController userController;
    private final Scanner scanner;

    public UserView(
            UserController userController,
            Scanner scanner
    ) {
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
                    System.out.println(
                            "Returning to main menu..."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please select from 0 to 5."
                    );
                    pause();
            }

        } while (choice != 0);
    }

    private void printMenu() {

        System.out.println();
        System.out.println(
                "===== USER MANAGEMENT ====="
        );
        System.out.println(
                "1. View All Users"
        );
        System.out.println(
                "2. Search User"
        );
        System.out.println(
                "3. Add User"
        );
        System.out.println(
                "4. Update User"
        );
        System.out.println(
                "5. Delete User"
        );
        System.out.println(
                "0. Back"
        );
    }

    private void viewAllUsers() {

        clearScreen();

        System.out.println();
        System.out.println(
                "===== ALL USERS ====="
        );

        List<User> users =
                userController.getAllUsers();

        printUsers(users);

        pause();
    }

    private void searchUser() {

        clearScreen();

        System.out.println();
        System.out.println(
                "===== SEARCH USER ====="
        );

        System.out.print(
                "Enter username keyword: "
        );

        String keyword =
                scanner.nextLine().trim();

        List<User> users =
                userController.searchUser(keyword);

        printUsers(users);

        pause();
    }

    private void addUser() {

        clearScreen();

        System.out.println();
        System.out.println(
                "===== ADD USER ====="
        );

        System.out.print(
                "Username: "
        );

        String username =
                scanner.nextLine().trim();

        System.out.print(
                "Password: "
        );

        String password =
                scanner.nextLine();

        /*
         * This constructor automatically assigns
         * the default USER role.
         */
        User user =
                new User(username, password);

        boolean success =
                userController.addUser(user);

        if (success) {
            System.out.println(
                    "User added successfully."
            );
            System.out.println(
                    "Assigned role: " + user.getRole()
            );
        } else {
            System.out.println(
                    "Failed to add user."
            );
        }

        pause();
    }

    private void updateUser() {

        clearScreen();

        System.out.println();
        System.out.println(
                "===== UPDATE USER ====="
        );

        List<User> users =
                userController.getAllUsers();

        printUsers(users);

        if (users == null || users.isEmpty()) {
            pause();
            return;
        }

        int id = readInt(
                "User ID to update: "
        );

        User selectedUser =
                findUserById(users, id);

        if (selectedUser == null) {
            System.out.println(
                    "User was not found."
            );
            pause();
            return;
        }

        System.out.println();
        System.out.println(
                "Selected User"
        );
        System.out.println(
                "-----------------------------"
        );
        System.out.println(
                "Username : "
                        + selectedUser.getUsername()
        );
        System.out.println(
                "Role     : "
                        + selectedUser.getRole()
        );
        System.out.println();

        System.out.print(
                "New Username: "
        );

        String username =
                scanner.nextLine().trim();

        System.out.print(
                "New Password: "
        );

        String password =
                scanner.nextLine();

        /*
         * Preserve the user's current role.
         *
         * This prevents an ADMIN account from being
         * accidentally changed into a USER account.
         */
        User updatedUser =
                new User(
                        id,
                        username,
                        password,
                        selectedUser.getRole()
                );

        boolean success =
                userController.updateUser(updatedUser);

        if (success) {
            System.out.println(
                    "User updated successfully."
            );
            System.out.println(
                    "Role preserved: "
                            + selectedUser.getRole()
            );
        } else {
            System.out.println(
                    "Failed to update user."
            );
        }

        pause();
    }

    private void deleteUser() {

        clearScreen();

        System.out.println();
        System.out.println(
                "===== DELETE USER ====="
        );

        List<User> users =
                userController.getAllUsers();

        printUsers(users);

        if (users == null || users.isEmpty()) {
            pause();
            return;
        }

        int id = readInt(
                "User ID to delete: "
        );

        User selectedUser =
                findUserById(users, id);

        if (selectedUser == null) {
            System.out.println(
                    "User was not found."
            );
            pause();
            return;
        }

        System.out.println();
        System.out.println(
                "Selected User"
        );
        System.out.println(
                "-----------------------------"
        );
        System.out.println(
                "Username : "
                        + selectedUser.getUsername()
        );
        System.out.println(
                "Role     : "
                        + selectedUser.getRole()
        );
        System.out.println();

        String confirmation;

        while (true) {

            System.out.print(
                    "Are you sure you want to delete this user? (Y/N): "
            );

            confirmation =
                    scanner.nextLine().trim();

            if (confirmation.equalsIgnoreCase("Y")
                    || confirmation.equalsIgnoreCase("N")) {
                break;
            }

            System.out.println(
                    "Invalid choice. Please enter Y or N only."
            );
        }

        if (confirmation.equalsIgnoreCase("N")) {
            System.out.println(
                    "User deletion cancelled."
            );
            pause();
            return;
        }

        boolean success =
                userController.deleteUser(id);

        if (success) {
            System.out.println(
                    "User deleted successfully."
            );
        } else {
            System.out.println(
                    "Failed to delete user."
            );
        }

        pause();
    }

    private User findUserById(
            List<User> users,
            int id
    ) {

        for (User user : users) {

            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    private void printUsers(
            List<User> users
    ) {

        if (users == null || users.isEmpty()) {
            System.out.println(
                    "No users found."
            );
            return;
        }

        System.out.println(
                "+------+---------------------------+------------+"
        );

        System.out.printf(
                "| %-4s | %-25s | %-10s |%n",
                "ID",
                "Username",
                "Role"
        );

        System.out.println(
                "+------+---------------------------+------------+"
        );

        for (User user : users) {

            String role =
                    user.getRole() == null
                            ? "USER"
                            : user.getRole();

            System.out.printf(
                    "| %-4d | %-25s | %-10s |%n",
                    user.getId(),
                    user.getUsername(),
                    role
            );
        }

        System.out.println(
                "+------+---------------------------+------------+"
        );
    }

    private int readInt(
            String prompt
    ) {

        while (true) {

            System.out.print(prompt);

            String input =
                    scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println(
                        "Please enter a valid whole number."
                );
            }
        }
    }

    private void pause() {

        System.out.println();
        System.out.println(
                "Press Enter to continue..."
        );

        scanner.nextLine();
    }

    private void clearScreen() {

        System.out.print(
                "\033[H\033[2J"
        );

        System.out.flush();
    }
}