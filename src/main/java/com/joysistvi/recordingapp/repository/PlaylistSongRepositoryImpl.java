package com.joysistvi.recordingapp.repository;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.model.PlaylistSong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistSongRepositoryImpl
        implements PlaylistSongRepository {

    private final DbConnection dbConnection;

    public PlaylistSongRepositoryImpl(
            DbConnection dbConnection
    ) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<PlaylistSong> getAllPlaylistSongs() {

        List<PlaylistSong> playlistSongs =
                new ArrayList<>();

        String sql = """
                SELECT
                    playlist_songs.id,
                    playlist_songs.playlist_id,
                    playlists.name AS playlist_name,
                    playlist_songs.song_id,
                    songs.title AS song_title
                FROM playlist_songs
                INNER JOIN playlists
                    ON playlist_songs.playlist_id = playlists.id
                INNER JOIN songs
                    ON playlist_songs.song_id = songs.id
                ORDER BY
                    playlists.name,
                    songs.title
                """;

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            while (resultSet.next()) {
                playlistSongs.add(
                        mapResultSetToPlaylistSong(
                                resultSet
                        )
                );
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving playlist songs: "
                            + e.getMessage()
            );
        }

        return playlistSongs;
    }

    @Override
    public List<PlaylistSong> getSongsByPlaylistId(
            int playlistId
    ) {

        List<PlaylistSong> playlistSongs =
                new ArrayList<>();

        String sql = """
                SELECT
                    playlist_songs.id,
                    playlist_songs.playlist_id,
                    playlists.name AS playlist_name,
                    playlist_songs.song_id,
                    songs.title AS song_title
                FROM playlist_songs
                INNER JOIN playlists
                    ON playlist_songs.playlist_id = playlists.id
                INNER JOIN songs
                    ON playlist_songs.song_id = songs.id
                WHERE playlist_songs.playlist_id = ?
                ORDER BY songs.title
                """;

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, playlistId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                while (resultSet.next()) {
                    playlistSongs.add(
                            mapResultSetToPlaylistSong(
                                    resultSet
                            )
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error retrieving songs from playlist: "
                            + e.getMessage()
            );
        }

        return playlistSongs;
    }

    @Override
    public boolean addSongToPlaylist(
            PlaylistSong playlistSong
    ) {

        String sql = """
                INSERT INTO playlist_songs (
                    playlist_id,
                    song_id
                )
                VALUES (?, ?)
                """;

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    playlistSong.getPlaylistId()
            );

            statement.setInt(
                    2,
                    playlistSong.getSongId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {
                System.out.println(
                        "This song is already inside the selected playlist."
                );
            } else if (e.getErrorCode() == 1452) {
                System.out.println(
                        "Playlist ID or Song ID does not exist."
                );
            } else {
                System.out.println(
                        "Error adding song to playlist: "
                                + e.getMessage()
                );
            }

            return false;
        }
    }

    @Override
    public boolean removeSongFromPlaylist(int id) {

        String sql = """
                DELETE FROM playlist_songs
                WHERE id = ?
                """;

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(
                    "Error removing song from playlist: "
                            + e.getMessage()
            );

            return false;
        }
    }

    @Override
    public boolean playlistSongExists(
            int playlistId,
            int songId
    ) {

        String sql = """
                SELECT COUNT(*) AS total
                FROM playlist_songs
                WHERE playlist_id = ?
                  AND song_id = ?
                """;

        try (
                Connection connection =
                        dbConnection.connect();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, playlistId);
            statement.setInt(2, songId);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {
                    return resultSet.getInt(
                            "total"
                    ) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error checking playlist song: "
                            + e.getMessage()
            );
        }

        return false;
    }

    private PlaylistSong mapResultSetToPlaylistSong(
            ResultSet resultSet
    ) throws SQLException {

        return new PlaylistSong(
                resultSet.getInt("id"),
                resultSet.getInt("playlist_id"),
                resultSet.getString("playlist_name"),
                resultSet.getInt("song_id"),
                resultSet.getString("song_title")
        );
    }
}