package com.github.lukaslt1993.songs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {

    @Id
    private Long artistId;

    private Long amgArtistId;
    private String artistName;

    public Artist() {

    }

    public Artist(Long amgArtistId, Long artistId, String artistName) {
        this.amgArtistId = amgArtistId;
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public Long getAmgArtistId() {
        return amgArtistId;
    }

    public void setAmgArtistId(Long amgArtistId) {
        this.amgArtistId = amgArtistId;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
