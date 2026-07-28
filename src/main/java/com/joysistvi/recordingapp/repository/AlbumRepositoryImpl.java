package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepositoryImpl implements AlbumRepository {

    private final DbConnection dbConnection;

    public AlbumRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Retrieves all albums together with their artist names
    @Override
    public List<Album> getAllAlbumsWithArtist() {

        List<Album> albums = new ArrayList<>();

        String sql = """
                SELECT
                    albums.id,
                    albums.name,
                    albums.year,
                    albums.artist_id,
                    artists.name AS artist_name
                FROM albums
                INNER JOIN artists
                    ON albums.artist_id = artists.id
                ORDER BY albums.id
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Album album = new Album(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("year"),
                        resultSet.getInt("artist_id"),
                        resultSet.getString("artist_name")
                );

                albums.add(album);
            }

        } catch (SQLException exception) {
            System.out.println(
                    "Error retrieving albums: "
                            + exception.getMessage()
            );
        }

        return albums;
    }

    // Searches albums by album name or artist name
    @Override
    public List<Album> searchAlbum(String keyword) {

        List<Album> albums = new ArrayList<>();

        String sql = """
                SELECT
                    albums.id,
                    albums.name,
                    albums.year,
                    albums.artist_id,
                    artists.name AS artist_name
                FROM albums
                INNER JOIN artists
                    ON albums.artist_id = artists.id
                WHERE albums.name LIKE ?
                   OR artists.name LIKE ?
                ORDER BY albums.id
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            String searchKeyword = "%" + keyword + "%";

            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Album album = new Album(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("year"),
                            resultSet.getInt("artist_id"),
                            resultSet.getString("artist_name")
                    );

                    albums.add(album);
                }
            }

        } catch (SQLException exception) {
            System.out.println(
                    "Error searching albums: "
                            + exception.getMessage()
            );
        }

        return albums;
    }

    // Inserts a new album into the database
    @Override
    public boolean createAlbum(Album album) {

        String sql = """
                INSERT INTO albums (
                    name,
                    year,
                    artist_id
                )
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, album.getName());
            statement.setInt(2, album.getYear());
            statement.setInt(3, album.getArtistId());

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println(
                    "Error creating album: "
                            + exception.getMessage()
            );

            return false;
        }
    }

    // Updates an existing album
    @Override
    public boolean updateAlbum(Album album) {

        String sql = """
                UPDATE albums
                SET
                    name = ?,
                    year = ?,
                    artist_id = ?
                WHERE id = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, album.getName());
            statement.setInt(2, album.getYear());
            statement.setInt(3, album.getArtistId());
            statement.setInt(4, album.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println(
                    "Error updating album: "
                            + exception.getMessage()
            );

            return false;
        }
    }

    // Deletes an album using its ID
    @Override
    public boolean deleteAlbum(int id) {

        String sql = """
                DELETE FROM albums
                WHERE id = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println(
                    "Error deleting album: "
                            + exception.getMessage()
            );

            return false;
        }
    }
}