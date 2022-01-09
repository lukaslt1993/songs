package com.github.lukaslt1993.songs.service;

import com.github.lukaslt1993.songs.component.ItunesClient;
import com.github.lukaslt1993.songs.component.Parser;
import org.springframework.stereotype.Service;

import com.github.lukaslt1993.songs.exception.EntityCreationException;
import com.github.lukaslt1993.songs.exception.EntityNotFoundException;
import com.github.lukaslt1993.songs.model.Song;
import com.github.lukaslt1993.songs.repository.SongRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SongService {

    public static final String SONG_ALREADY_EXIST = "Song already exist";
    public static final String SONG_DOES_NOT_EXIST = "Song does not exist";
    private Parser parser;
    private SongRepository repo;
    private ItunesClient itunesClient;

    public SongService(Parser parser, SongRepository repo, ItunesClient itunesClient) {
        this.parser = parser;
        this.repo = repo;
        this.itunesClient = itunesClient;
    }

    private void checkSongExist(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException(SONG_DOES_NOT_EXIST, id);
        }
    }

    public void createSong(Song song) {
        if (song.getSongId() != null && repo.existsById(song.getSongId())) {
            throw new EntityCreationException(SONG_ALREADY_EXIST, song.getSongId());
        }
        repo.save(song);
    }

    public Song getSongFromDb(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Song> getSongsFromDb(String name) {
        return repo.findAllBySongName(name);
    }

    public List<Song> getSongsFromDb() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Song getSongFromItunes(Long id) throws IOException {
        return itunesClient.getSong(id);
    }

    public List<Song> getSongsFromItunes(String name) throws IOException {
        return itunesClient.getSongs(name);
    }

    public Song getSongFromFile(Long id) throws IOException {
        return parser.getSong(id);
    }

    public List<Song> getSongsFromFile() throws IOException {
        return parser.getSongs();
    }

    public List<Song> getSongsFromFile(String name) throws IOException {
        return parser.getSongs(name);
    }

    public void updateSong(Long id, Song updatedSong) {
        checkSongExist(id);
        Song result = repo.findById(id).get();
        result.setAlbumName(updatedSong.getAlbumName());
        result.setArtistName(updatedSong.getArtistName());
        result.setSongName(updatedSong.getSongName());
        repo.save(result);
    }

    public void deleteSong(Long id) {
        checkSongExist(id);
        repo.deleteById(id);
    }

}
