package com.github.lukaslt1993.songs.unit.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.lukaslt1993.songs.model.Artist;
import com.github.lukaslt1993.songs.model.Song;
import com.github.lukaslt1993.songs.component.Parser;

import java.io.IOException;
import java.util.List;

public class ParserTest {

    private Parser parser = new Parser();

    public ParserTest() throws IOException {
    }

    @Test
    void getSong() throws IOException {
        Song song = parser.getSong(1440743961L);
        Assertions.assertNotNull(song);
        Assertions.assertEquals(song.getSongId(), 1440743961);
        Assertions.assertEquals(song.getArtistName(), "The Animals");
    }

    @Test
    void getAllSongs() throws IOException {
        List<Song> list = parser.getSongs();
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.size() == 100);
    }

    @Test
    void getSongs() throws IOException {
        List<Song> list = parser.getSongs("The House of the Rising Sun");
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.size() == 1);
    }

    @Test
    void getArtist() throws IOException {
        Artist artist  = parser.getArtist(45583837L);
        Assertions.assertNotNull(artist);
        Assertions.assertEquals(artist.getArtistName(), "Desperation Band");
    }

    @Test
    void getAllArtists() throws IOException {
        List<Artist> list = parser.getArtists();
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.size() == 100);
    }

    @Test
    void getArtists() throws IOException {
        List<Artist> list = parser.getArtists("Desperation Band");
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.size() == 1);
    }

}
