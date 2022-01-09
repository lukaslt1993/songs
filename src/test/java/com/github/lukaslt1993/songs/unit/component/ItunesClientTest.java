package com.github.lukaslt1993.songs.unit.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.github.lukaslt1993.songs.model.Artist;
import com.github.lukaslt1993.songs.model.Song;
import com.github.lukaslt1993.songs.component.ItunesClient;
import com.github.lukaslt1993.songs.component.Parser;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ItunesClientTest {

    @Mock
    private RestTemplate restTemplateMock;

    private Parser parser = new Parser();

    private ItunesClient itunesClient = new ItunesClient(parser);

    public ItunesClientTest() throws IOException {
        itunesClient.setSearchUrl("https://itunes.apple.com/search");
        itunesClient.setLookupUrl("https://itunes.apple.com/lookup");
    }

    private String getJson() {
        return new String(parser.getInput());
    }

    private URI getUri(String searchFor) {
        return ArgumentMatchers.argThat(uri -> uriContains(uri, searchFor));
    }

    private boolean uriContains(URI uri, String searchFor) {
        String uriString = uri.toString().toLowerCase();

        for (String s : searchFor.toLowerCase().split(" ")) {
            if (!uriString.contains(s)) {
                return false;
            }
        }

        return true;
    }

    private void mockGetForObject(Object searchFor) {
        Mockito
                .when(
                        restTemplateMock.getForObject(
                                getUri(searchFor.toString()),
                                ArgumentMatchers.<Class<String>>any()
                        )
                ).thenReturn(getJson());
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(itunesClient, "restTemplate", restTemplateMock);
    }

    @Test
    void getSong() throws IOException {
        Long id = 1440743961L;
        mockGetForObject(1440743961L);
        Song song = itunesClient.getSong(id);
        Assertions.assertNotNull(song);
        Assertions.assertEquals(song.getSongId(), id);
        Assertions.assertEquals(song.getArtistName(), "The Animals");
    }

    @Test
    void getSongs() throws IOException {
        mockGetForObject("The House of the Rising Sun");
        List<Song> list = itunesClient.getSongs("The House of the Rising Sun");
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.stream().anyMatch(song -> song.getArtistName().equals("The Animals")));
    }

    @Test
    void getArtist() throws IOException {
        mockGetForObject(45583837L);
        Artist artist = itunesClient.getArtist(45583837L);
        Assertions.assertNotNull(artist);
        Assertions.assertEquals(artist.getArtistId(), 45583837);
        Assertions.assertEquals(artist.getArtistName(), "Desperation Band");
    }

    @Test
    void getArtists() throws IOException {
        mockGetForObject("Desperation Band");
        List<Artist> list = itunesClient.getArtists("Desperation Band");
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertTrue(list.stream().anyMatch(artist -> artist.getArtistId() == 45583837));
    }

}
