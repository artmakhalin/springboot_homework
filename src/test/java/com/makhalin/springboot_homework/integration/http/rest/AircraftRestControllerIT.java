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
class AircraftRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/api/v1/admin/aircraft"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.length()").value(4)
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/admin/aircraft/" + airbus330.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(airbus330.getId()),
                       jsonPath("$.model").value(airbus330.getModel())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAircraftFound() {
        mockMvc.perform(get("/api/v1/admin/aircraft/" + 555))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Aircraft not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/admin/aircraft")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("""
                               {
                                 "model": "RRJ-95"
                               }
                               """))
               .andExpectAll(
                       status().isCreated(),
                       jsonPath("$.model").value("RRJ-95")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(put("/api/v1/admin/aircraft/" + airbus330.getId())
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("""
                               {
                                 "model": "RRJ-95"
                               }
                               """)
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(airbus330.getId()),
                       jsonPath("$.model").value("RRJ-95")
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAircraftFoundWhenUpdate() {
        mockMvc.perform(put("/api/v1/admin/aircraft/" + 555)
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("""
                               {
                                 "model": "RRJ-95"
                               }
                               """)
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Aircraft not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/admin/aircraft/" + airbus330.getId())
                       .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAircraftFoundWhenRemove() {
        mockMvc.perform(delete("/api/v1/admin/aircraft/" + 555)
                       .with(csrf()))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Aircraft not found with id 555")
               );
    }
}
