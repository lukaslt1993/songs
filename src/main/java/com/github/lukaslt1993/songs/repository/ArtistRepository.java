package com.github.lukaslt1993.songs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.lukaslt1993.songs.model.Artist;

import java.util.List;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {

    List<Artist> findAllByArtistName(String name);

}
