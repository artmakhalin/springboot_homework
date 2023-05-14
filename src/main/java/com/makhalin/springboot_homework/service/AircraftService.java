package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.AircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.AircraftReadDto;
import com.makhalin.springboot_homework.mapper.AircraftMapper;
import com.makhalin.springboot_homework.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makhalin.springboot_homework.exception.BadRequestException.badRequest;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFound;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    public List<AircraftReadDto> findAll() {
        return aircraftRepository.findAll()
                                 .stream()
                                 .map(aircraftMapper::mapRead)
                                 .toList();
    }

    public AircraftReadDto findById(Integer id) {
        return aircraftRepository.findById(id)
                                 .map(aircraftMapper::mapRead)
                                 .orElseThrow(notFound("Aircraft not found with id " + id));
    }

    @Transactional
    public AircraftReadDto create(AircraftCreateEditDto aircraftDto) {
        return Optional.of(aircraftDto)
                       .map(aircraftMapper::mapCreate)
                       .map(aircraftRepository::save)
                       .map(aircraftMapper::mapRead)
                       .orElseThrow(badRequest("Error during save aircraft"));
    }

    @Transactional
    public AircraftReadDto update(Integer id, AircraftCreateEditDto aircraftDto) {
        return aircraftRepository.findById(id)
                                 .map(entity -> aircraftMapper.mapUpdate(aircraftDto, entity))
                                 .map(aircraftRepository::saveAndFlush)
                                 .map(aircraftMapper::mapRead)
                                 .orElseThrow(notFound("Aircraft not found with id " + id));
    }

    @Transactional
    public void delete(Integer id) {
        aircraftRepository.findById(id)
                .ifPresentOrElse(entity -> {
                    aircraftRepository.delete(entity);
                    aircraftRepository.flush();
                }, () -> {
                    throw notFoundException("Aircraft not found with id " + id);
                });
    }
}
