package com.github.lukaslt1993.songs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.lukaslt1993.songs.EndpointNames;
import com.github.lukaslt1993.songs.model.Artist;
import com.github.lukaslt1993.songs.service.ArtistService;

import java.util.List;

@RestController
@RequestMapping(EndpointNames.ARTIST)
public class ArtistController {

    private ArtistService service;

    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return service.getArtistsFromDb();
    }

    @GetMapping(path = { "/{name}" })
    public List<Artist> getArtists(@PathVariable String name) {
        return service.getArtistsFromDb(name);
    }

    @PostMapping
    public ResponseEntity<String> createArtist(@RequestBody Artist artist) {
        service.createArtist(artist);
        return ResponseEntity.ok("Artist created");
    }

    @PutMapping(path = { "/{id}" })
    public ResponseEntity<String> updateArtist(@RequestBody Artist artist, @PathVariable Long id) {
        service.updateArtist(id, artist);
        return ResponseEntity.ok("Artist updated");
    }

    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<String> deleteArtist(@PathVariable Long id) {
        service.deleteArtist(id);
        return ResponseEntity.ok("Artist deleted");
    }

}
