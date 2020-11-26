package com.github.lukaslt1993.songs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.lukaslt1993.songs.model.Song;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {

    List<Song> findAllBySongName(String name);

}
