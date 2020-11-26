package com.github.lukaslt1993.songs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.lukaslt1993.songs.EndpointNames;
import com.github.lukaslt1993.songs.model.Song;
import com.github.lukaslt1993.songs.service.SongService;

import java.util.List;

@RestController
@RequestMapping(EndpointNames.SONG)
public class SongController {

    private SongService service;

    public SongController(SongService service) {
        this.service = service;
    }

    @GetMapping
    public List<Song> getAllSongs() {
        return service.getSongsFromDb();
    }

    @GetMapping(path = { "/{name}" })
    public List<Song> getSongs(@PathVariable String name) {
        return service.getSongsFromDb(name);
    }

    @PostMapping
    public ResponseEntity<String> createSong(@RequestBody Song song) {
        service.createSong(song);
        return ResponseEntity.ok("Song created");
    }
    
    @PutMapping(path = { "/{id}" })
    public ResponseEntity<String> updateSong(@RequestBody Song song, @PathVariable Long id) {
        service.updateSong(id, song);
        return ResponseEntity.ok("Song updated");
    }
    
    @DeleteMapping(path = { "/{id}" })
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {
        service.deleteSong(id);
        return ResponseEntity.ok("Song deleted");
    }

}
