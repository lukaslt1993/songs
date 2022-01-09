package com.github.lukaslt1993.songs.integration.controller.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.lukaslt1993.songs.controller.EndpointNames;

import com.github.lukaslt1993.songs.component.DataLoader;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataLoader dataLoader;

    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @Order(1)
    public void adminLogin() throws Exception {
        dataLoader.loadAdminUser();
        ObjectNode admin = mapper.createObjectNode();
        admin.put("userName", DataLoader.ADMIN);
        admin.put("password", DataLoader.ADMIN);
        String json = mapper.writeValueAsString(admin);
        mvc.perform(MockMvcRequestBuilders.post(EndpointNames.LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    public void createUser() throws Exception {
        ObjectNode user = mapper.createObjectNode();
        user.put("userName", "user");
        user.put("password", "user");
        String json = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post(EndpointNames.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    @Order(3)
    public void userCanNotSeeOthers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(EndpointNames.USER))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    @Order(4)
    public void adminCanSeeOthers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(EndpointNames.USER))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
