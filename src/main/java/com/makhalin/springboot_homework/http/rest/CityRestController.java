package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.dto.CityFilter;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.dto.PageResponse;
import com.makhalin.springboot_homework.service.CityService;
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
@RequestMapping("/api/v1/admin/cities")
@RequiredArgsConstructor
public class CityRestController {

    private final CityService cityService;

    @GetMapping
    public PageResponse<CityReadDto> findAll(CityFilter filter, Pageable pageable) {
        return PageResponse.of(cityService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityReadDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok()
                             .body(cityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CityReadDto> create(@Valid @RequestBody CityCreateEditDto city) {
        return new ResponseEntity<>(cityService.create(city), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityReadDto> update(@PathVariable("id") Integer id,
                                              @Valid @RequestBody CityCreateEditDto city) {
        return ResponseEntity.ok()
                             .body(cityService.update(id, city));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        cityService.delete(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
