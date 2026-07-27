package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;


// SongDao handles table song CRUD Operation
// has-a relationship : loosely coupled
public class SongDao  {

    // Reference to the DbConnection object(used to establish DB Connnection)
    // Composition
    private final DbConnection dbConnection; // db connection

    // Constructor Injection: SongDao depends on DbConnection, so we pass it here to decouple DB logic from DAO logic.
    public SongDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    /*
        SQL Language Categories

        // DDL Data Definition Language
        DQL Data Query Language
        DML Data Manipulation Language
     */

    // [Section] -> DQL
    // Read Songs
    public void readSong() {
        String query = "SELECT * FROM songs WHERE is_archived = 0";

        try (Connection connection = dbConnection.connect();
             Statement stmnt = connection.createStatement();
             ResultSet result = stmnt.executeQuery(query);){

            // Table Header
            System.out.println("+----+------------------------------+----------+----------------+----------------+");
            System.out.printf("| %-2s | %-28s | %-8s | %-20s | %-8s |%n",
                    "ID", "Title", "Length", "Genre", "Album ID");
            System.out.println("+----+------------------------------+----------+----------------+----------------+");

             // extract the data
             while (result.next()) {
                 int id = result.getInt("id");
                 String title = result.getString("title");
                 String length = result.getString("length");
                 String genre = result.getString("genre");
                 int albumId = result.getInt("album_id");

                 // Print the data
                 System.out.printf("| %-2s | %-28s | %-8s | %-20s | %-8s |%n",
                         id, title, length, genre, albumId);
             }
            System.out.println("+----+------------------------------+----------+----------------+----------------+");
        } catch (SQLException e) {
            System.out.println("Read Songs: " + e.getMessage());
        }
    }

    public void readArchivedSong() {
        String query = "SELECT * FROM songs WHERE is_archived = 1";

        try (Connection connection = dbConnection.connect();
             Statement stmnt = connection.createStatement();
             ResultSet result = stmnt.executeQuery(query);){

            // Table Header
            System.out.println("+----+------------------------------+----------+----------------+----------------+");
            System.out.printf("| %-2s | %-28s | %-8s | %-20s | %-8s |%n",
                    "ID", "Title", "Length", "Genre", "Album ID");
            System.out.println("+----+------------------------------+----------+----------------+----------------+");

            // extract the data
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String length = result.getString("length");
                String genre = result.getString("genre");
                int albumId = result.getInt("album_id");

                // Print the data
                System.out.printf("| %-2s | %-28s | %-8s | %-20s | %-8s |%n",
                        id, title, length, genre, albumId);
            }
            System.out.println("+----+------------------------------+----------+----------------+----------------+");
        } catch (SQLException e) {
            System.out.println("Read Songs: " + e.getMessage());
        }
    }

    // [Section] -> DML
    // Create Song
    public void createSong(String title, String length, String genre, int album_id) {
        String query = "INSERT INTO songs (title, length, genre, album_id) " + // create statement
                "VALUES (?,?,?,?)"; // Anti-SQL Injection

        // Try-with-resources: automatically close opened connection
        try (Connection connection = dbConnection.connect();
             PreparedStatement prep = connection.prepareStatement(query);
        ) {
            // Bind values to the placeholders in the query
            prep.setString(1, title);
            prep.setString(2, length);
            prep.setString(3, genre);
            prep.setInt(4, album_id);

            // Execute the insert statement
            int rowsAffected = prep.executeUpdate();
            System.out.println("Song " + title + " added successfully!");
            readSongsWithAlbum();
        } catch (SQLException e) {
            // Print the error message if something goes wrong
            System.out.println("Error in inserting song: " + e.getMessage());
        }
    }

    // Update Song
    public void updateSong(String title, String length, String genre, int id) {
        String query = "UPDATE songs SET title = ?, length = ?, genre = ? WHERE id = ?"; // parametherized query

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            // setting parameter wild cards
            prep.setString(1, title);
            prep.setString(2, length);
            prep.setString(3, genre);
            prep.setInt(4, id);

            int rowsAffected = prep.executeUpdate();
            System.out.println("Song "  + title + " updated successfully!");

            readSongsWithAlbum();
        } catch (SQLException e) {
            System.out.println("Update Song: " + e.getMessage());
        }
    }

    // Hard Delete Song
    public void deleteSong(int id) {
        String query = "DELETE FROM songs WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);
            int rows = prep.executeUpdate();

            System.out.println("Song "  + id + " deleted successfully!");
            readSongsWithAlbum();
        } catch (SQLException e) {
            System.out.println("Delete Song: " + e.getMessage());
        }
    }

    // Archive / Soft Delete Song
    public void archiveSong(int id) {
        String query = "UPDATE songs SET is_archived = 1 WHERE id = ?"; // parametherized query

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            // setting parameter wild cards
            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();
            System.out.println("Song "  + id + " archived successfully!");
            readSongsWithAlbum();
        } catch (SQLException e) {
            System.out.println("Archive Song: " + e.getMessage());
        }
    }

    // Restore Song
    public void restoreSong(int id) {
        String query = "UPDATE songs SET is_archived = 0 WHERE id = ?"; // parametherized query

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            // setting parameter wild cards
            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();
            System.out.println("Song "  + id + " archived successfully!\n\n\n");

            // synchronization
            readSongsWithAlbum();
        } catch (SQLException e) {
            System.out.println("Archive Song: " + e.getMessage());
        }
    }

    // Search Song
    public void searchSong(String keyword) {
        String query = "SELECT * FROM songs WHERE is_archived = 0 AND title LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);) {

            prep.setString(1, "%" + keyword + "%");
            ResultSet result = prep.executeQuery();

            // Table Header
            System.out.println("+----+------------------------------+----------+----------------+----------------+");
            System.out.printf("| %-2s | %-28s | %-8s | %-20s | %-8s |%n",
                    "ID", "Title", "Length", "Genre", "Album ID");
            System.out.println("+----+------------------------------+----------+----------------+----------------+");

            // extract the data
            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String length = result.getString("length");
                String genre = result.getString("genre");
                int albumId = result.getInt("album_id");

                // Print the data
                System.out.printf("| %-2s | %-28s | %-8s | %-20s | %-8s |%n",
                        id, title, length, genre, albumId);
            }
            System.out.println("+----+------------------------------+----------+----------------+----------------+");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    // Display All Songs with Album Name using JOIN
    public void readSongsWithAlbum() {
        String query = "SELECT s.id, s.title, s.length, s.genre, a.name " +
                "FROM songs s " +
                "JOIN albums a ON s.album_id = a.id " +
                "WHERE s.is_archived = 0";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet res = prep.executeQuery()) {

            // Table Header
            System.out.println("+----+------------------------------+----------+----------------+----------------+");
            System.out.printf("| %-2s | %-28s | %-8s | %-14s | %-14s |%n",
                    "ID", "Title", "Length", "Genre", "Album");
            System.out.println("+----+------------------------------+----------+----------------+----------------+");

            while (res.next()) {
                int id = res.getInt("id");
                String title = res.getString("title");
                String length = res.getString("length");
                String genre = res.getString("genre");
                String albumName = res.getString("name");

                System.out.printf("| %-2s | %-28s | %-8s | %-14s | %-14s |%n",
                        id, title, length, genre, albumName);
            }

            System.out.println("+----+------------------------------+----------+----------------+----------------+");

        } catch (Exception e) {
            System.out.println("Read Songs With Album: " + e.getMessage());
        }
    }


}

/*
    public void testConnection() {
        try {
            dbConnection.connect();
            System.out.println("Connected Successfully!");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
 */


// extends
// constructor injection
