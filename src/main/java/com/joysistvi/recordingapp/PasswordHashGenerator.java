package com.joysistvi.recordingapp;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashGenerator {

    public static void main(String[] args) {

        String password = "maynard123";

        String hashedPassword = BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );

        System.out.println("Generated Hash:");
        System.out.println(hashedPassword);
    }
}