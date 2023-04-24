package com.makhalin.springboot_homework.http.rest;

import com.makhalin.springboot_homework.dto.CrewFilter;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.dto.PageResponse;
import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin/crew")
@RequiredArgsConstructor
public class CrewRestController {

    private final CrewService crewService;

    @GetMapping
    public PageResponse<CrewReadDto> findAll(CrewFilter filter, Pageable pageable) {
        return PageResponse.of(crewService.findAllByFilter(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrewReadDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok()
                             .body(crewService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrewReadDto> update(@PathVariable("id") Integer id,
                                              @Valid @RequestBody CrewCreateEditDto crew) {
        return ResponseEntity.ok()
                             .body(crewService.update(id, crew));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        crewService.delete(id);

        return ResponseEntity.noContent()
                             .build();
    }
}
