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
public class CrewAircraftRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/admin/crewAircraft/" + alexAirbus320.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(alexAirbus320.getId()),
                       jsonPath("$.crew.email").value(alex.getEmail()),
                       jsonPath("$.aircraft.model").value(airbus320.getModel())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoPermitFound() {
        mockMvc.perform(get("/api/v1/admin/crewAircraft/" + 555))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Permit not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/admin/crewAircraft")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "permitDate": "2021-01-01",
                                                   "crewId": %d,
                                                   "aircraftId": %d
                                               }
                                               """,
                                       alex.getId(),
                                       airbus330.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.permitDate").value("2021-01-01"),
                       jsonPath("$.crew.id").value(alex.getId()),
                       jsonPath("$.aircraft.id").value(airbus330.getId())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestIfPermitDateInFuture() {
        mockMvc.perform(post("/api/v1/admin/crewAircraft")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "permitDate": "2025-01-01",
                                                   "crewId": %d,
                                                   "aircraftId": %d
                                               }
                                               """,
                                       alex.getId(),
                                       airbus330.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Permit date should not be in future")
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestIfInvalidPermitDateBeforeEmploymentDate() {
        mockMvc.perform(post("/api/v1/admin/crewAircraft")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "permitDate": "2005-01-01",
                                                   "crewId": %d,
                                                   "aircraftId": %d
                                               }
                                               """,
                                       alex.getId(),
                                       airbus330.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Permit date could not be before employment date")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(put("/api/v1/admin/crewAircraft/" + alexAirbus320.getId())
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "permitDate": "2021-01-01",
                                                   "crewId": %d,
                                                   "aircraftId": %d
                                               }
                                               """,
                                       alex.getId(),
                                       airbus330.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.permitDate").value("2021-01-01"),
                       jsonPath("$.crew.id").value(alex.getId()),
                       jsonPath("$.aircraft.id").value(airbus330.getId())
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/admin/crewAircraft/" + bobBoeing777.getId())
                       .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAirportFoundWhenRemove() {
        mockMvc.perform(delete("/api/v1/admin/crewAircraft/" + 555)
                       .with(csrf()))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Permit not found with id 555")
               );
    }
}
