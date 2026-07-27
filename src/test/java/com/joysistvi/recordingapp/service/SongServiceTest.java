//package com.joysistvi.recordingapp.service;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class SongServiceTest {
//
//    @Test
//    public void testAddSongValid() {
//        SongRepository mockRepo = mock(SongRepository.class);
//        SongService service = new SongService(mockRepo);
//
//        boolean result = service.addSong("Imagine", 240, "Pop", 1);
//
//        assertTrue(result); // should succeed
//        verify(mockRepo).save(any(Song.class)); // repo should be called
//    }
//
//    @Test
//    public void testAddSongInvalidTitle() {
//        SongRepository mockRepo = mock(SongRepository.class);
//        SongService service = new SongService(mockRepo);
//
//        boolean result = service.addSong("", 240, "Pop", 1);
//
//        assertFalse(result); // should fail
//        verify(mockRepo, never()).save(any(Song.class)); // repo not called
//    }
//}
//
