package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.*;
import com.makhalin.springboot_homework.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/flights")
@RequiredArgsConstructor
public class FlightRestController {

    private final FlightService flightService;

    @GetMapping
    public PageResponse<FlightReadDto> findAll(FlightFilter filter, Pageable pageable) {
        return PageResponse.of(flightService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightReadDto> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                             .body(flightService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FlightReadDto> create(@Valid @RequestBody FlightCreateEditDto flight) {
        return new ResponseEntity<>(flightService.create(flight), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightReadDto> update(@PathVariable("id") Long id,
                                                @Valid @RequestBody FlightCreateEditDto flight) {
        return ResponseEntity.ok()
                             .body(flightService.update(id, flight));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        flightService.delete(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
