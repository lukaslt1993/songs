package com.github.lukaslt1993.songs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.lukaslt1993.songs.model.Artist;
import com.github.lukaslt1993.songs.model.Song;

import java.io.IOException;
import java.util.List;

@Service
public class ItunesClient {

    public static final String ENTITY = "entity";
    public static final String TERM = "term";
    public static final String ID = "id";
    public static final String MUSIC_ARTIST = "musicArtist";
    public static final String SONG = "song";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Parser parser;

    private String searchUrl;
    private String lookupUrl;

    public String getSearchUrl() {
        return searchUrl;
    }

    @Autowired
    public void setSearchUrl(@Value("${itunes.search.url}") String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getLookupUrl() {
        return lookupUrl;
    }

    @Autowired
    public void setLookupUrl(@Value("${itunes.lookup.url}") String lookupUrl) {
        this.lookupUrl = lookupUrl;
    }

    public ItunesClient(Parser parser) {
        this.parser = parser;
    }

    public List<Song> getSongs(String name) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(searchUrl);
        builder.queryParam(TERM, name);
        builder.queryParam(ENTITY, SONG);
        String response = restTemplate.getForObject(builder.build().toUri(), String.class);
        parser.setInput(response.getBytes());
        return parser.getSongs();
    }

    public List<Artist> getArtists(String name) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(searchUrl);
        builder.queryParam(TERM, name);
        builder.queryParam(ENTITY, MUSIC_ARTIST);
        String response = restTemplate.getForObject(builder.build().toUri(), String.class);
        parser.setInput(response.getBytes());
        return parser.getArtists();
    }

    public Song getSong(Long id) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(lookupUrl);
        builder.queryParam(ID, id.toString());
        builder.queryParam(ENTITY, SONG);
        String response = restTemplate.getForObject(builder.build().toUri(), String.class);
        parser.setInput(response.getBytes());
        return parser.getSong(id);
    }

    public Artist getArtist(Long id) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(lookupUrl);
        builder.queryParam(ID, id.toString());
        builder.queryParam(ENTITY, MUSIC_ARTIST);
        String response = restTemplate.getForObject(builder.build().toUri(), String.class);
        parser.setInput(response.getBytes());
        return parser.getArtist(id);
    }

}
