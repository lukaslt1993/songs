package com.github.lukaslt1993.songs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.lukaslt1993.songs.model.User;
import com.github.lukaslt1993.songs.repository.ArtistRepository;
import com.github.lukaslt1993.songs.repository.SongRepository;
import com.github.lukaslt1993.songs.repository.UserRepository;
import com.github.lukaslt1993.songs.security.SecurityConstants.Role;
import com.github.lukaslt1993.songs.service.Parser;

import java.io.IOException;

@SpringBootApplication
public class SongsApplication {

    public static final String ADMIN = "admin";
    private static UserRepository userRepo;
    private static SongRepository songRepo;
    private static ArtistRepository artistRepo;
    private static Parser parser;

    public SongsApplication(UserRepository userRepo,
                            SongRepository songRepo,
                            ArtistRepository artistRepo,
                            Parser parser) {

        this.userRepo = userRepo;
        this.songRepo = songRepo;
        this.artistRepo = artistRepo;
        this.parser = parser;
    }

    public static void loadAdminUser() {
        if (userRepo.findFirstByUserName(ADMIN) == null) {
            User user = new User();
            user.setUserName(ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode(ADMIN));
            user.setRole(Role.ADMIN);
            userRepo.save(user);
        }
    }

    public static void loadSongs() throws IOException {
        songRepo.saveAll(parser.getSongs());
    }

    public static void loadArtists() throws IOException {
        artistRepo.saveAll(parser.getArtists());
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SongsApplication.class, args);
        loadAdminUser();
        loadSongs();
        loadArtists();
    }

}
