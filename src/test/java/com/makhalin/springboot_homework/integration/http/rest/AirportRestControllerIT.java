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
class AirportRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/api/v1/admin/airports"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.metadata.totalElements").value(7)
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/admin/airports/" + jfk.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(jfk.getId()),
                       jsonPath("$.code").value(jfk.getCode()),
                       jsonPath("$.city.id").value(newYork.getId()),
                       jsonPath("$.city.name").value(newYork.getName())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAirportFound() {
        mockMvc.perform(get("/api/v1/admin/airports/" + 5555))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Airport not found with id 5555")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/admin/airports")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "code": "LGA",
                                                   "cityId": %d
                                               }
                                               """,
                                       newYork.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.code").value("LGA"),
                       jsonPath("$.city.id").value(newYork.getId()),
                       jsonPath("$.city.name").value(newYork.getName())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestIfInvalidLengthCode() {
        mockMvc.perform(post("/api/v1/admin/airports")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "code": "LGAX",
                                                   "cityId": %d
                                               }
                                               """,
                                       newYork.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Should contain only 3 English letters")
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestIfInvalidPatternCode() {
        mockMvc.perform(post("/api/v1/admin/airports")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "code": "EG1",
                                                   "cityId": %d
                                               }
                                               """,
                                       newYork.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Should contain only 3 English letters")
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestIfCodeNotUnique() {
        mockMvc.perform(post("/api/v1/admin/airports")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "code": "JFK",
                                                   "cityId": %d
                                               }
                                               """,
                                       newYork.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("could not execute statement")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(put("/api/v1/admin/airports/" + jfk.getId())
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "code": "XXX",
                                                   "cityId": %d
                                               }
                                               """,
                                       moscow.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(jfk.getId()),
                       jsonPath("$.code").value("XXX"),
                       jsonPath("$.city.id").value(moscow.getId()),
                       jsonPath("$.city.name").value(moscow.getName())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAirportFoundWhenUpdate() {
        mockMvc.perform(put("/api/v1/admin/airports/" + 555)
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "code": "XXX",
                                                   "cityId": %d
                                               }
                                               """,
                                       svo.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Airport not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/admin/airports/" + ewr.getId())
                       .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAirportFoundWhenRemove() {
        mockMvc.perform(delete("/api/v1/admin/airports/" + 555)
                       .with(csrf()))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Airport not found with id 555")
               );
    }
}
