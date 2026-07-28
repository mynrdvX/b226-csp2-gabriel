package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepositoryImpl implements PlaylistRepository {

    private final DbConnection dbConnection;

    public PlaylistRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Playlist> getAllPlaylistsWithUser() {

        List<Playlist> playlists = new ArrayList<>();

        String sql = """
                SELECT
                    playlists.id,
                    playlists.name,
                    playlists.date_created,
                    playlists.user_id,
                    users.username
                FROM playlists
                INNER JOIN users
                    ON playlists.user_id = users.id
                ORDER BY playlists.id
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                playlists.add(mapResultSetToPlaylist(resultSet));
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving playlists: "
                            + e.getMessage()
            );
        }

        return playlists;
    }

    @Override
    public List<Playlist> searchPlaylist(String keyword) {

        List<Playlist> playlists = new ArrayList<>();

        String sql = """
                SELECT
                    playlists.id,
                    playlists.name,
                    playlists.date_created,
                    playlists.user_id,
                    users.username
                FROM playlists
                INNER JOIN users
                    ON playlists.user_id = users.id
                WHERE playlists.name LIKE ?
                   OR users.username LIKE ?
                ORDER BY playlists.id
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            String searchValue = "%" + keyword + "%";

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    playlists.add(
                            mapResultSetToPlaylist(resultSet)
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error searching playlists: "
                            + e.getMessage()
            );
        }

        return playlists;
    }

    @Override
    public boolean createPlaylist(Playlist playlist) {

        String sql = """
                INSERT INTO playlists (name, user_id)
                VALUES (?, ?)
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    playlist.getName()
            );

            statement.setInt(
                    2,
                    playlist.getUserId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error creating playlist: "
                            + e.getMessage()
            );
            return false;
        }
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {

        String sql = """
                UPDATE playlists
                SET name = ?, user_id = ?
                WHERE id = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    playlist.getName()
            );

            statement.setInt(
                    2,
                    playlist.getUserId()
            );

            statement.setInt(
                    3,
                    playlist.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error updating playlist: "
                            + e.getMessage()
            );
            return false;
        }
    }

    @Override
    public boolean deletePlaylist(int id) {

        String sql = """
                DELETE FROM playlists
                WHERE id = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error deleting playlist: "
                            + e.getMessage()
            );
            return false;
        }
    }

    @Override
    public boolean playlistNameExistsForUser(
            String name,
            int userId
    ) {

        String sql = """
                SELECT COUNT(*) AS total
                FROM playlists
                WHERE name = ?
                  AND user_id = ?
                """;

        try (
                Connection connection = dbConnection.connect();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setInt(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error checking playlist name: "
                            + e.getMessage()
            );
        }

        return false;
    }

    private Playlist mapResultSetToPlaylist(
            ResultSet resultSet
    ) throws SQLException {

        Timestamp timestamp =
                resultSet.getTimestamp("date_created");

        return new Playlist(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                timestamp != null
                        ? timestamp.toLocalDateTime()
                        : null,
                resultSet.getInt("user_id"),
                resultSet.getString("username")
        );
    }
}