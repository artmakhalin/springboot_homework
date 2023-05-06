package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.FlightCrewCreateEditDto;
import com.makhalin.springboot_homework.dto.FlightCrewReadDto;
import com.makhalin.springboot_homework.service.FlightCrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/flightCrew")
@RequiredArgsConstructor
public class FlightCrewRestController {

    private final FlightCrewService flightCrewService;

    @GetMapping("/{id}")
    public ResponseEntity<FlightCrewReadDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                             .body(flightCrewService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FlightCrewReadDto> create(@Valid @RequestBody FlightCrewCreateEditDto flightCrew) {
        return new ResponseEntity<>(flightCrewService.create(flightCrew), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightCrewReadDto> update(@PathVariable("id") Long id,
                                                    @Valid @RequestBody FlightCrewCreateEditDto flightCrew) {
        return ResponseEntity.ok()
                             .body(flightCrewService.update(id, flightCrew));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        flightCrewService.delete(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
