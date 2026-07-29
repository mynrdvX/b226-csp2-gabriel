package com.joysistvi.recordingapp.service;

import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 8;

    private final UserRepository userRepository;

    public UserServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<User> searchUser(
            String keyword
    ) {

        if (keyword == null
                || keyword.trim().isEmpty()) {

            System.out.println(
                    "Search keyword cannot be empty."
            );

            return Collections.emptyList();
        }

        return userRepository.searchUser(
                keyword.trim()
        );
    }

    @Override
    public boolean addUser(
            User user
    ) {

        if (!isValidUser(user, false)) {
            return false;
        }

        String username =
                user.getUsername().trim();

        if (userRepository.usernameExists(username)) {
            System.out.println(
                    "Username already exists."
            );
            return false;
        }

        String hashedPassword =
                BCrypt.hashpw(
                        user.getPassword(),
                        BCrypt.gensalt()
                );

        /*
         * Accounts created through regular registration
         * are always assigned the USER role.
         */
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRole(DEFAULT_ROLE);

        return userRepository.createUser(user);
    }

    @Override
    public boolean updateUser(
            User user
    ) {

        if (!isValidUser(user, true)) {
            return false;
        }

        String username =
                user.getUsername().trim();

        /*
         * Check whether the new username is already being
         * used by a different account.
         */
        User usernameOwner =
                userRepository.findByUsername(username);

        if (usernameOwner != null
                && usernameOwner.getId() != user.getId()) {

            System.out.println(
                    "Username already exists."
            );

            return false;
        }

        String role =
                normalizeRole(user.getRole());

        if (role == null) {
            System.out.println(
                    "Role must be either ADMIN or USER."
            );
            return false;
        }

        String hashedPassword =
                BCrypt.hashpw(
                        user.getPassword(),
                        BCrypt.gensalt()
                );

        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRole(role);

        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUser(
            int id
    ) {

        if (id <= 0) {
            System.out.println(
                    "User ID must be greater than zero."
            );
            return false;
        }

        return userRepository.deleteUser(id);
    }

    @Override
    public User login(
            String username,
            String password
    ) {

        if (username == null
                || username.trim().isEmpty()) {

            System.out.println(
                    "Username cannot be empty."
            );

            return null;
        }

        if (password == null
                || password.isEmpty()) {

            System.out.println(
                    "Password cannot be empty."
            );

            return null;
        }

        User user =
                userRepository.findByUsername(
                        username.trim()
                );

        /*
         * Use one general message for invalid login details.
         * This avoids revealing whether a username exists.
         */
        if (user == null) {
            System.out.println(
                    "Invalid username or password."
            );
            return null;
        }

        try {

            boolean passwordMatches =
                    BCrypt.checkpw(
                            password,
                            user.getPassword()
                    );

            if (!passwordMatches) {
                System.out.println(
                        "Invalid username or password."
                );
                return null;
            }

        } catch (IllegalArgumentException e) {

            /*
             * This can happen when an old database password
             * is not stored as a valid BCrypt hash.
             */
            System.out.println(
                    "Unable to verify the stored password."
            );

            return null;
        }

        String role =
                normalizeRole(user.getRole());

        if (role == null) {
            System.out.println(
                    "This account has an invalid role."
            );
            return null;
        }

        user.setRole(role);

        return user;
    }

    private boolean isValidUser(
            User user,
            boolean requireId
    ) {

        if (user == null) {
            System.out.println(
                    "User cannot be null."
            );
            return false;
        }

        if (requireId
                && user.getId() <= 0) {

            System.out.println(
                    "User ID must be greater than zero."
            );
            return false;
        }

        if (user.getUsername() == null
                || user.getUsername().trim().isEmpty()) {

            System.out.println(
                    "Username cannot be empty."
            );
            return false;
        }

        String username =
                user.getUsername().trim();

        if (username.length()
                < MIN_USERNAME_LENGTH) {

            System.out.println(
                    "Username must contain at least "
                            + MIN_USERNAME_LENGTH
                            + " characters."
            );

            return false;
        }

        if (username.length()
                > MAX_USERNAME_LENGTH) {

            System.out.println(
                    "Username cannot exceed "
                            + MAX_USERNAME_LENGTH
                            + " characters."
            );

            return false;
        }

        if (user.getPassword() == null
                || user.getPassword().isEmpty()) {

            System.out.println(
                    "Password cannot be empty."
            );
            return false;
        }

        if (user.getPassword().length()
                < MIN_PASSWORD_LENGTH) {

            System.out.println(
                    "Password must contain at least "
                            + MIN_PASSWORD_LENGTH
                            + " characters."
            );

            return false;
        }

        return true;
    }

    private String normalizeRole(
            String role
    ) {

        if (role == null
                || role.trim().isEmpty()) {

            return DEFAULT_ROLE;
        }

        String normalizedRole =
                role.trim().toUpperCase();

        if (normalizedRole.equals(DEFAULT_ROLE)
                || normalizedRole.equals(ADMIN_ROLE)) {

            return normalizedRole;
        }

        return null;
    }
}