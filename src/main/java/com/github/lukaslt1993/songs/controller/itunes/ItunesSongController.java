package com.github.lukaslt1993.songs.controller.itunes;

import com.github.lukaslt1993.songs.controller.EndpointNames;
import com.github.lukaslt1993.songs.model.Song;
import com.github.lukaslt1993.songs.service.SongService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(EndpointNames.ITUNES_SONG)
public class ItunesSongController {

    private SongService service;

    public ItunesSongController(SongService service) {
        this.service = service;
    }

    @GetMapping(path = { "/{name}" })
    public List<Song> getSongs(@PathVariable String name) throws IOException {
        return service.getSongsFromItunes(name);
    }

}
