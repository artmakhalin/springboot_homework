package com.makhalin.springboot_homework.integration.http.rest;

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
class CityRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/api/v1/admin/cities"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.metadata.totalElements").value(7)
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/admin/cities/" + newYork.getId()))
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
    void shouldReturnNotFoundStatusIfNoCityFound() {
        mockMvc.perform(get("/api/v1/admin/cities/" + 555))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("City not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/admin/cities")
                       .with(csrf())
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
        mockMvc.perform(put("/api/v1/admin/cities/" + newYork.getId())
                       .with(csrf())
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
    void shouldReturnNotFoundStatusIfNoCityFoundWhenUpdate() {
        mockMvc.perform(put("/api/v1/admin/cities/" + 555)
                       .with(csrf())
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
                       status().is4xxClientError(),
                       jsonPath("$.message").value("City not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/admin/cities/" + lion.getId())
                       .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoCityFoundWhenRemove() {
        mockMvc.perform(delete("/api/v1/admin/cities/" + 555)
                       .with(csrf()))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("City not found with id 555")
               );
    }
}