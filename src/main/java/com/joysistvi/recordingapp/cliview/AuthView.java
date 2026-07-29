package com.joysistvi.recordingapp.cliview;

import com.joysistvi.recordingapp.controller.UserController;
import com.joysistvi.recordingapp.model.User;

import java.util.Scanner;

public class AuthView {

    private final UserController userController;
    private final Scanner scanner;

    // Constructor injection
    public AuthView(
            UserController userController,
            Scanner scanner
    ) {
        this.userController = userController;
        this.scanner = scanner;
    }

    // Returns the authenticated user, including their role
    public User login() {

        System.out.println();
        System.out.println("===== LOGIN =====");
        System.out.println();

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User loggedInUser = userController.login(
                username,
                password
        );

        if (loggedInUser == null) {
            System.out.println();
            System.out.println("Login failed.");
            return null;
        }

        System.out.println();
        System.out.println(
                "Login successful. Welcome, "
                        + loggedInUser.getUsername()
                        + "!"
        );

        return loggedInUser;
    }

    // Registers a normal USER account
    public boolean register() {

        System.out.println();
        System.out.println("===== USER REGISTRATION =====");
        System.out.println();

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println();
            System.out.println(
                    "Passwords do not match."
            );
            return false;
        }

        User user = new User(
                username,
                password
        );

        boolean registered =
                userController.addUser(user);

        if (registered) {
            System.out.println();
            System.out.println(
                    "Registration successful. "
                            + "You may now log in."
            );
        } else {
            System.out.println();
            System.out.println(
                    "Registration failed."
            );
        }

        return registered;
    }
}