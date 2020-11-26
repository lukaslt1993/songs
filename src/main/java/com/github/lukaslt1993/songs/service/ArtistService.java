package com.github.lukaslt1993.songs.service;

import org.springframework.stereotype.Service;

import com.github.lukaslt1993.songs.exception.EntityCreationException;
import com.github.lukaslt1993.songs.exception.EntityNotFoundException;
import com.github.lukaslt1993.songs.model.Artist;
import com.github.lukaslt1993.songs.repository.ArtistRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ArtistService {

    public static final String ARTIST_ALREADY_EXIST = "Artist already exist";
    public static final String ARTIST_DOES_NOT_EXIST = "Artist does not exist";
    private Parser parser;
    private ArtistRepository repo;
    private ItunesClient itunes;

    public ArtistService(Parser parser, ArtistRepository repo, ItunesClient itunes) {
        this.parser = parser;
        this.repo = repo;
        this.itunes = itunes;
    }

    private void checkArtistExist(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException(ARTIST_DOES_NOT_EXIST, id);
        }
    }

    public void createArtist(Artist artist) {
        if (artist.getArtistId() != null && repo.existsById(artist.getArtistId())) {
            throw new EntityCreationException(ARTIST_ALREADY_EXIST, artist.getArtistId());
        }
        repo.save(artist);
    }

    public Artist getArtistFromItunes(Long id) throws IOException {
        return itunes.getArtist(id);
    }

    public List<Artist> getArtistsFromItunes(String name) throws IOException {
        return itunes.getArtists(name);
    }

    public Artist getArtistFromDb(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<Artist> getArtistsFromDb() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Artist> getArtistsFromDb(String name) {
        return StreamSupport.stream(repo.findAllByArtistName(name).spliterator(), false)
                .collect(Collectors.toList());
    }

    public Artist getArtistFromFile(Long id) throws IOException {
        return parser.getArtist(id);
    }

    public List<Artist> getArtistsFromFile() throws IOException {
        return parser.getArtists();
    }

    public List<Artist> getArtistsFromFile(String name) throws IOException {
        return parser.getArtists(name);
    }

    public void updateArtist(Long id, Artist updatedArtist) {
        checkArtistExist(id);
        Artist result = repo.findById(id).get();
        result.setAmgArtistId(updatedArtist.getAmgArtistId());
        result.setArtistName(updatedArtist.getArtistName());
        repo.save(result);
    }

    public void deleteArtist(Long id) {
        checkArtistExist(id);
        repo.deleteById(id);
    }
}
