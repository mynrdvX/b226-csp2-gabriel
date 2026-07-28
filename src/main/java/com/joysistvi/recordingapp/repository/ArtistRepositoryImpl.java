package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepository {

    private final DbConnection dbConnection; // Composition

    // Constructor Injection
    public ArtistRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Retrieve all artists
    @Override
    public List<Artist> getAllArtists() {

        List<Artist> artists = new ArrayList<>();

        String query = "SELECT * FROM artists";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet res = prep.executeQuery()) {

            while (res.next()) {
                artists.add(new Artist(
                        res.getInt("id"),
                        res.getString("name")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Read Artists: " + e.getMessage());
        }

        return artists;
    }

    // Search artist by name
    @Override
    public List<Artist> searchArtist(String keyword) {

        List<Artist> artists = new ArrayList<>();

        String query = "SELECT * FROM artists WHERE name LIKE ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, "%" + keyword + "%");

            ResultSet res = prep.executeQuery();

            while (res.next()) {
                artists.add(new Artist(
                        res.getInt("id"),
                        res.getString("name")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Search Artist: " + e.getMessage());
        }

        return artists;
    }

    // Insert a new artist
    @Override
    public boolean createArtist(Artist artist) {

        String query = "INSERT INTO artists (name) VALUES (?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, artist.getName());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Insert Artist: " + e.getMessage());
        }

        return false;
    }

    // Update artist
    @Override
    public boolean updateArtist(Artist artist) {

        String query = "UPDATE artists SET name = ? WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, artist.getName());
            prep.setInt(2, artist.getId());

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Update Artist: " + e.getMessage());
        }

        return false;
    }

    // Delete artist
    @Override
    public boolean deleteArtist(int id) {

        String query = "DELETE FROM artists WHERE id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, id);

            int rowsAffected = prep.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Delete Artist: " + e.getMessage());
        }

        return false;
    }

}