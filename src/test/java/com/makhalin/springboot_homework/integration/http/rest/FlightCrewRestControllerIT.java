package com.makhalin.springboot_homework.integration.http.rest;

import com.makhalin.springboot_homework.entity.ClassOfService;
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
class FlightCrewRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/admin/flightCrew/" + ledVogAlex.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(ledVogAlex.getId()),
                       jsonPath("$.flight.id").value(ledVogAlex.getFlight().getId()),
                       jsonPath("$.crew.id").value(ledVogAlex.getCrew()
                                                                     .getId()),
                       jsonPath("$.classOfService").value(ledVogAlex.getClassOfService()
                                                                   .toString()),
                       jsonPath("$.isTurnaround").value(ledVogAlex.getIsTurnaround().toString()),
                       jsonPath("$.isPassenger").value(ledVogAlex.getIsPassenger().toString())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoFlightCrewFound() {
        mockMvc.perform(get("/api/v1/admin/flightCrew/" + 555))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Assignment not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/admin/flightCrew")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "flightId": %d,
                                                   "crewId": %d,
                                                   "classOfService": "ECONOMY",
                                                   "isTurnaround": false,
                                                   "isPassenger": false
                                               }
                                               """,
                                       svoJfk.getId(),
                                       bob.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.flight.id").value(svoJfk.getId()),
                       jsonPath("$.crew.id").value(bob.getId()),
                       jsonPath("$.classOfService").value(ClassOfService.ECONOMY.toString()),
                       jsonPath("$.isTurnaround").value("false"),
                       jsonPath("$.isPassenger").value("false")
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestIfInvalidPermitDate() {
        mockMvc.perform(post("/api/v1/admin/flightCrew")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "flightId": %d,
                                                   "crewId": %d,
                                                   "classOfService": "ECONOMY",
                                                   "isTurnaround": false,
                                                   "isPassenger": false
                                               }
                                               """,
                                       jfkCdg.getId(),
                                       bob.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Permit date is after flight date")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(put("/api/v1/admin/flightCrew/" + ledVogAlex.getId())
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "flightId": %d,
                                                   "crewId": %d,
                                                   "classOfService": "ECONOMY",
                                                   "isTurnaround": false,
                                                   "isPassenger": true
                                               }
                                               """,
                                       ledVog.getId(),
                                       alex.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.flight.id").value(ledVog.getId()),
                       jsonPath("$.crew.id").value(alex.getId()),
                       jsonPath("$.classOfService").value(ClassOfService.ECONOMY.toString()),
                       jsonPath("$.isTurnaround").value("false"),
                       jsonPath("$.isPassenger").value("true")
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/admin/flightCrew/" + ledVogAlex.getId())
                       .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAssignmentFoundWhenRemove() {
        mockMvc.perform(delete("/api/v1/admin/flightCrew/" + 555)
                       .with(csrf()))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Assignment not found with id 555")
               );
    }
}
