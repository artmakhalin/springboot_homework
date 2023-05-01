package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.CrewAircraftCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewAircraftReadDto;
import com.makhalin.springboot_homework.entity.CrewAircraft;
import com.makhalin.springboot_homework.exception.NotFoundException;
import com.makhalin.springboot_homework.mapper.CrewAircraftMapper;
import com.makhalin.springboot_homework.repository.CrewAircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makhalin.springboot_homework.exception.BadRequestException.badRequest;
import static com.makhalin.springboot_homework.exception.BadRequestException.badRequestException;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFound;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewAircraftService {

    private final CrewAircraftRepository crewAircraftRepository;
    private final CrewAircraftMapper crewAircraftMapper;

    public List<CrewAircraftReadDto> findAll() {
        return crewAircraftRepository.findAll()
                                     .stream()
                                     .map(crewAircraftMapper::mapRead)
                                     .toList();
    }

    public List<CrewAircraftReadDto> findAllByCrewId(Integer id) {
        return crewAircraftRepository.findAllByCrewId(id)
                                     .stream()
                                     .map(crewAircraftMapper::mapRead)
                                     .toList();
    }

    public CrewAircraftReadDto findById(Long id) {
        return crewAircraftRepository.findById(id)
                .map(crewAircraftMapper::mapRead)
                .orElseThrow(notFound("Permit not found with id " + id));
    }

    @Transactional
    public CrewAircraftReadDto create(CrewAircraftCreateEditDto crewAircraftDto) {
        return Optional.of(crewAircraftDto)
                .map(crewAircraftMapper::mapCreate)
                .map(this::validatePermitDate)
                .map(crewAircraftRepository::save)
                .map(crewAircraftMapper::mapRead)
                .orElseThrow(badRequest("Error during save permit"));
    }

    @Transactional
    public CrewAircraftReadDto update(Long id, CrewAircraftCreateEditDto crewAircraftDto) {
        return crewAircraftRepository.findById(id)
                .map(entity -> crewAircraftMapper.mapUpdate(crewAircraftDto, entity))
                .map(this::validatePermitDate)
                .map(crewAircraftRepository::saveAndFlush)
                .map(crewAircraftMapper::mapRead)
                .orElseThrow(notFound("Permit not found with id " + id));
    }

    @Transactional
    public void delete(Long id) {
        crewAircraftRepository.findById(id)
                .ifPresentOrElse(entity -> {
                    crewAircraftRepository.delete(entity);
                    crewAircraftRepository.flush();
                }, () -> {
                    throw NotFoundException.notFoundException("Permit not found with id " + id);
                });
    }

    private CrewAircraft validatePermitDate(CrewAircraft crewAircraft) {
        if (crewAircraft.getPermitDate().isBefore(crewAircraft.getCrew().getEmploymentDate())) {
            throw badRequestException("Permit date could not be before employment date");
        }

        return crewAircraft;
    }
}
