package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.service.CountryService;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/countries")
@RequiredArgsConstructor
public class CountryRestController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryReadDto>> findAll() {
        return ResponseEntity.ok()
                             .body(countryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryReadDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok()
                             .body(countryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CountryReadDto> create(@Valid @RequestBody CountryCreateEditDto country) {
        return new ResponseEntity<>(countryService.create(country), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryReadDto> update(@PathVariable("id") Integer id,
                                                 @Valid @RequestBody CountryCreateEditDto country) {
        return ResponseEntity.ok()
                             .body(countryService.update(id, country));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        countryService.delete(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
