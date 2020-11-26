package com.github.lukaslt1993.songs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Song {

    public static final String TRACK_ID = "trackId";
    public static final String TRACK_NAME = "trackName";
    public static final String COLLECTION_NAME = "collectionName";

    @Id
    @JsonProperty(TRACK_ID)
    private Long songId;

    @JsonProperty(TRACK_NAME)
    private String songName;

    @JsonProperty(COLLECTION_NAME)
    private String albumName;

    private String artistName;

    public Song() {

    }

    public Song(Long songId, String songName, String albumName, String artistName) {
        this.songId = songId;
        this.songName = songName;
        this.albumName = albumName;
        this.artistName = artistName;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
