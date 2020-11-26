package com.github.lukaslt1993.songs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lukaslt1993.songs.model.Artist;
import com.github.lukaslt1993.songs.model.Song;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class Parser {

    public static final String ARTIST_ID = "artistId";
    public static final String ARTIST_NAME = "artistName";
    public static final String WRAPPER_TYPE = "wrapperType";
    public static final String RESULTS = "results";
    public static final String TRACK = "track";
    public static final String SONG = "song";
    public static final String KIND = "kind";
    public static final String ARTIST = "artist";
    private byte[] input = Files.readAllBytes(Paths.get("src/main/resources/response.json"));
    private ObjectMapper objectMapper = new ObjectMapper();

    public Parser() throws IOException {
    }

    public void setInput(byte[] input) {
        this.input = input;
    }

    public byte[] getInput() {
        return input;
    }

    public Song getSong(Long id) throws IOException {

        for (JsonNode result : getResults(input)) {
            if (isSong(result) && result.get(Song.TRACK_ID).asInt() == id) {
                return getSong(result);
            }
        }

        return null;
    }

    public List<Song> getSongs() throws IOException {
        List<Song> songs = new ArrayList<>();

        for (JsonNode node : getResults(input)) {
            if (isSong(node)) {
                songs.add(getSong(node));
            }
        }

        return songs;
    }

    public List<Song> getSongs(String name) throws IOException {
        List<Song> songs = new ArrayList<>();

        for (JsonNode node : getResults(input)) {
            if (isSong(node) && node.get(Song.TRACK_NAME).asText().equals(name)) {
                songs.add(getSong(node));
            }
        }

        return songs;
    }

    private JsonNode getResults(byte[] bytes) throws IOException {
        JsonNode root = objectMapper.readTree(bytes);
        return root.get(RESULTS);
    }

    private Song getSong(JsonNode node) throws IOException {
        return objectMapper.treeToValue(node, Song.class);
    }

    private boolean isSong(JsonNode result) {
        return TRACK.equals(result.get(WRAPPER_TYPE).asText()) && SONG.equals(result.get(KIND).asText());
    }

    private boolean isArtist(JsonNode result) {
        return ARTIST.equals(result.get(WRAPPER_TYPE).asText());
    }

    private Artist getArtist(JsonNode node) throws IOException {
        return objectMapper.treeToValue(node, Artist.class);
    }

    public Artist getArtist(Long id) throws IOException {

        for (JsonNode result : getResults(input)) {
            if (isArtist(result) && result.get(ARTIST_ID).asInt() == id) {
                return getArtist(result);
            }
        }

        return null;
    }

    public List<Artist> getArtists() throws IOException {
        List<Artist> artists = new ArrayList<>();

        for (JsonNode node : getResults(input)) {
            if (isArtist(node)) {
                artists.add(getArtist(node));
            }
        }

        return artists;
    }

    public List<Artist> getArtists(String name) throws IOException {
        List<Artist> artists = new ArrayList<>();

        for (JsonNode node : getResults(input)) {
            if (isArtist(node) && node.get(ARTIST_NAME).asText().equals(name)) {
                artists.add(getArtist(node));
            }
        }

        return artists;
    }

}
