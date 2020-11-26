package com.github.lukaslt1993.songs.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.lukaslt1993.songs.EndpointNames;
import com.github.lukaslt1993.songs.SongsApplication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class SongControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    public SongControllerTest() {
    }

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithMockUser
    @Order(1)
    public void userCanObtainSong() throws Exception {
        SongsApplication.loadSongs();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(EndpointNames.SONG))
                .andReturn();
        Assertions.assertTrue(result.getResponse().getStatus() == 200);
        Assertions.assertFalse(mapper.readTree(result.getResponse().getContentAsString()).isEmpty());
    }

    @Test
    @WithMockUser
    @Order(2)
    public void userCanNotCreateSong() throws Exception {
        ObjectNode song = mapper.createObjectNode();
        song.put("trackId", 99999999999L);
        song.put("trackName", "The Lucky Nines");
        song.put("collectionName", "The Best of the Best of the Best");
        song.put("artistName", "The Lucky Nine");
        String json = mapper.writeValueAsString(song);
        mvc.perform(MockMvcRequestBuilders.post(EndpointNames.SONG)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @Order(3)
    public void adminCanCreateSong() throws Exception {
        ObjectNode song = mapper.createObjectNode();
        song.put("trackId", 99999999999L);
        song.put("trackName", "The Lucky Nines");
        song.put("collectionName", "The Best of the Best of the Best");
        song.put("artistName", "The Lucky Nine");
        String json = mapper.writeValueAsString(song);
        mvc.perform(MockMvcRequestBuilders.post(EndpointNames.SONG)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
