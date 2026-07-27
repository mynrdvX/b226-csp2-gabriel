package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;

public class Main {

    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
//        SongDao songDao = new SongDao(dbConnection);
//        songDao.readSongsWithAlbum();

        UserDao userDao = new UserDao(dbConnection);
        userDao.registerUser("asta", "asta123");

    }
}
