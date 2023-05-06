package com.makhalin.springboot_homework.integration.http.rest;

import com.makhalin.springboot_homework.integration.IntegrationTestBase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.makhalin.springboot_homework.util.FlightTimeUtil.hoursFromSec;
import static com.makhalin.springboot_homework.util.FlightTimeUtil.minFromSec;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class FlightRestControllerIT extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll() {
        mockMvc.perform(get("/api/v1/admin/flights"))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.metadata.totalElements").value(6)
               );
    }

    @Test
    @SneakyThrows
    void findById() {
        mockMvc.perform(get("/api/v1/admin/flights/" + svoJfk.getId()))
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(svoJfk.getId()),
                       jsonPath("$.flightNo").value(svoJfk.getFlightNo()),
                       jsonPath("$.departureAirport.id").value(svoJfk.getDepartureAirport()
                                                                     .getId()),
                       jsonPath("$.arrivalAirport.id").value(svoJfk.getArrivalAirport()
                                                                   .getId()),
                       jsonPath("$.hours").value(hoursFromSec(svoJfk.getTime())),
                       jsonPath("$.minutes").value(minFromSec(svoJfk.getTime())),
                       jsonPath("$.aircraft.id").value(svoJfk.getAircraft()
                                                             .getId())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoFlightFound() {
        mockMvc.perform(get("/api/v1/admin/flights/" + 555))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Flight not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void create() {
        mockMvc.perform(post("/api/v1/admin/flights")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "flightNo": "SU555",
                                                   "departureAirportId": %d,
                                                   "arrivalAirportId": %d,
                                                   "transitAirportId": null,
                                                   "departureDate": "2021-01-01",
                                                   "hours": "5",
                                                   "minutes": "25",
                                                   "aircraftId": %d
                                               }
                                               """,
                                       jfk.getId(),
                                       sea.getId(),
                                       airbus320.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.flightNo").value("SU555"),
                       jsonPath("$.departureAirport.id").value(jfk.getId()),
                       jsonPath("$.arrivalAirport.id").value(sea.getId()),
                       jsonPath("$.transitAirport").value(nullValue()),
                       jsonPath("$.departureDate").value("2021-01-01"),
                       jsonPath("$.hours").value("5"),
                       jsonPath("$.minutes").value("25"),
                       jsonPath("$.aircraft.id").value(airbus320.getId())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnBadRequestIfInvalidTimeWhenCreate() {
        mockMvc.perform(post("/api/v1/admin/flights")
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "flightNo": "SU555",
                                                   "departureAirportId": %d,
                                                   "arrivalAirportId": %d,
                                                   "transitAirportId": null,
                                                   "departureDate": "2021-01-01",
                                                   "hours": "-5",
                                                   "minutes": "25",
                                                   "aircraftId": %d
                                               }
                                               """,
                                       jfk.getId(),
                                       sea.getId(),
                                       airbus320.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Flight time could not be negative")
               );
    }

    @Test
    @SneakyThrows
    void update() {
        mockMvc.perform(put("/api/v1/admin/flights/" + jfkCdg.getId())
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "flightNo": "SU555",
                                                   "departureAirportId": %d,
                                                   "arrivalAirportId": %d,
                                                   "transitAirportId": null,
                                                   "departureDate": "2021-01-01",
                                                   "hours": "5",
                                                   "minutes": "25",
                                                   "aircraftId": %d
                                               }
                                               """,
                                       jfk.getId(),
                                       sea.getId(),
                                       airbus320.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is2xxSuccessful(),
                       jsonPath("$.id").value(jfkCdg.getId()),
                       jsonPath("$.flightNo").value("SU555"),
                       jsonPath("$.departureAirport.id").value(jfk.getId()),
                       jsonPath("$.arrivalAirport.id").value(sea.getId()),
                       jsonPath("$.transitAirport").value(nullValue()),
                       jsonPath("$.departureDate").value("2021-01-01"),
                       jsonPath("$.hours").value("5"),
                       jsonPath("$.minutes").value("25"),
                       jsonPath("$.aircraft.id").value(airbus320.getId())
               );
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoFlightFoundWhenUpdate() {
        mockMvc.perform(put("/api/v1/admin/flights/" + 555)
                       .with(csrf())
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(String.format("""
                                               {
                                                   "flightNo": "SU555",
                                                   "departureAirportId": %d,
                                                   "arrivalAirportId": %d,
                                                   "transitAirportId": null,
                                                   "departureDate": "2021-01-01",
                                                   "hours": "5",
                                                   "minutes": "25",
                                                   "aircraftId": %d
                                               }
                                               """,
                                       jfk.getId(),
                                       sea.getId(),
                                       airbus320.getId()
                               )
                       )
               )
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Flight not found with id 555")
               );
    }

    @Test
    @SneakyThrows
    void remove() {
        mockMvc.perform(delete("/api/v1/admin/flights/" + jfkCdg.getId())
                       .with(csrf()))
               .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void shouldReturnNotFoundStatusIfNoAirportFoundWhenRemove() {
        mockMvc.perform(delete("/api/v1/admin/flights/" + 555)
                       .with(csrf()))
               .andExpectAll(
                       status().is4xxClientError(),
                       jsonPath("$.message").value("Flight not found with id 555")
               );
    }
}
