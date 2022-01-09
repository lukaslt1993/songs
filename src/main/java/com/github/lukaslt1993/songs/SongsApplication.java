package com.github.lukaslt1993.songs;

import com.github.lukaslt1993.songs.component.DataLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SongsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongsApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData (DataLoader dataLoader) {
        return args -> {
            dataLoader.loadAdminUser();
            dataLoader.loadArtists();
            dataLoader.loadSongs();
        };
    }

}
