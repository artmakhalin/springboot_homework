package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class CityRestControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/api/v1/cities"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.metadata.totalElements").value(7)
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/cities/" + newYork.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(newYork.getId()),
                       jsonPath("$.name").value(newYork.getName()),
                       jsonPath("$.country.id").value(usa.getId()),
                       jsonPath("$.country.name").value(usa.getName())
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/cities")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "name": "Miami",
                                                   "countryId": %d
                                               }
                                               """,
                                       usa.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.name").value("Miami"),
                       jsonPath("$.country.id").value(usa.getId()),
                       jsonPath("$.country.name").value(usa.getName())
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(put("/api/v1/cities/" + newYork.getId())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "name": "Chicago",
                                                   "countryId": %d
                                               }
                                               """,
                                       usa.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.name").value("Chicago"),
                       jsonPath("$.country.id").value(usa.getId()),
                       jsonPath("$.country.name").value(usa.getName())
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/cities/" + lion.getId()))
                .andExpect(status().isNoContent());
    }
}