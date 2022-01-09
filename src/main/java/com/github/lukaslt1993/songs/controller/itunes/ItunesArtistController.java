package com.github.lukaslt1993.songs.controller.itunes;

import com.github.lukaslt1993.songs.controller.EndpointNames;
import com.github.lukaslt1993.songs.model.Artist;
import com.github.lukaslt1993.songs.service.ArtistService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(EndpointNames.ITUNES_ARTIST)
public class ItunesArtistController {

    private ArtistService service;

    public ItunesArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping(path = { "/{name}" })
    public List<Artist> getArtists(@PathVariable String name) throws IOException {
        return service.getArtistsFromItunes(name);
    }

}
