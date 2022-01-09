package com.github.lukaslt1993.songs.component;

import com.github.lukaslt1993.songs.model.User;
import com.github.lukaslt1993.songs.repository.ArtistRepository;
import com.github.lukaslt1993.songs.repository.SongRepository;
import com.github.lukaslt1993.songs.repository.UserRepository;
import com.github.lukaslt1993.songs.security.SecurityConstants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DataLoader {

    public static final String ADMIN = "admin";

    private UserRepository userRepo;
    private SongRepository songRepo;
    private ArtistRepository artistRepo;
    private Parser parser;

    public DataLoader(UserRepository userRepo, SongRepository songRepo, ArtistRepository artistRepo, Parser parser) {
        this.userRepo = userRepo;
        this.songRepo = songRepo;
        this.artistRepo = artistRepo;
        this.parser = parser;
    }

    public void loadAdminUser() {
        if (userRepo.findFirstByUserName(ADMIN) == null) {
            User user = new User();
            user.setUserName(ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode(ADMIN));
            user.setRole(SecurityConstants.Role.ADMIN);
            userRepo.save(user);
        }
    }

    public void loadSongs() throws IOException {
        songRepo.saveAll(parser.getSongs());
    }

    public void loadArtists() throws IOException {
        artistRepo.saveAll(parser.getArtists());
    }

}
