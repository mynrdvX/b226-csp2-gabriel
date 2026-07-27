package com.joysistvi.recordingapp.config;

// Reverse Domain Names
// Using a reverse domain, it helps avoid name conflicts between packages from different organization
// Java doesn't enforce this, but it's a widely followed convention
// commercial(com) organzation(org) network(net) edu gov

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private final static String URL = "jdbc:mysql://localhost:3306/recording_app_db";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";


    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // ducking exception -> throw
    // connection object
}