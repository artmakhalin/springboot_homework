package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class CountryRestControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/api/v1/countries"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.length()").value(4)
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/countries/" + usa.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(usa.getId()),
                       jsonPath("$.name").value(usa.getName())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoCountryFound() {
        mockMvc.perform(get("/api/v1/countries/" + 555))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Country not found")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/countries")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("""
                               {
                                 "name": "Cuba"
                               }
                               """))
               .andExpectAll(
                       status().isCreated(),
                       jsonPath("$.name").value("Cuba")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(put("/api/v1/countries/" + usa.getId())
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("""
                               {
                                 "name": "Italy"
                               }
                               """)
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(usa.getId()),
                       jsonPath("$.name").value("Italy")
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoCountryFoundWhenUpdate() {
        mockMvc.perform(put("/api/v1/countries/" + 555)
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("""
                               {
                                 "name": "Italy"
                               }
                               """)
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Country not found")
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/countries/" + uk.getId())
                       .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoCountryFoundWhenRemove() {
        mockMvc.perform(delete("/api/v1/countries/" + 555)
                       .with(csrf()))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Country not found")
               );
    }
}