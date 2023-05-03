package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.FlightCrewCreateEditDto;
import com.makhalin.springboot_homework.dto.FlightCrewReadDto;
import com.makhalin.springboot_homework.entity.FlightCrew;
import com.makhalin.springboot_homework.mapper.FlightCrewMapper;
import com.makhalin.springboot_homework.repository.CrewAircraftRepository;
import com.makhalin.springboot_homework.repository.FlightCrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makhalin.springboot_homework.exception.BadRequestException.badRequest;
import static com.makhalin.springboot_homework.exception.BadRequestException.badRequestException;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFound;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightCrewService {

    private final FlightCrewRepository flightCrewRepository;
    private final CrewAircraftRepository crewAircraftRepository;
    private final FlightCrewMapper flightCrewMapper;

    public List<FlightCrewReadDto> findAll() {
        return flightCrewRepository.findAll()
                                   .stream()
                                   .map(flightCrewMapper::mapRead)
                                   .toList();
    }

    public List<FlightCrewReadDto> findAllByFlightId(Long id) {
        return flightCrewRepository.findAllByFlightId(id)
                                   .stream()
                                   .map(flightCrewMapper::mapRead)
                                   .toList();
    }

    public FlightCrewReadDto findById(Long id) {
        return flightCrewRepository.findById(id)
                                   .map(flightCrewMapper::mapRead)
                                   .orElseThrow(notFound("Assignment not found with id " + id));
    }

    @Transactional
    public FlightCrewReadDto create(FlightCrewCreateEditDto flightCrewDto) {
        return Optional.of(flightCrewDto)
                       .map(flightCrewMapper::mapCreate)
                       .map(this::validateAssignment)
                       .map(flightCrewRepository::save)
                       .map(flightCrewMapper::mapRead)
                       .orElseThrow(badRequest("Error during saving assignment"));
    }

    @Transactional
    public FlightCrewReadDto update(Long id, FlightCrewCreateEditDto flightCrewDto) {
        return flightCrewRepository.findById(id)
                                   .map(entity -> flightCrewMapper.mapUpdate(flightCrewDto, entity))
                                   .map(this::validateAssignment)
                                   .map(flightCrewRepository::saveAndFlush)
                                   .map(flightCrewMapper::mapRead)
                                   .orElseThrow(notFound("Assignment not found with id " + id));
    }

    @Transactional
    public void delete(Long id) {
        flightCrewRepository.findById(id)
                            .ifPresentOrElse(entity -> {
                                flightCrewRepository.delete(entity);
                                flightCrewRepository.flush();
                            }, () -> {
                                throw notFoundException("Assignment not found with id " + id);
                            });
    }

    private FlightCrew validateAssignment(FlightCrew flightCrew) {
        if (!flightCrew.isPassenger()) {
            crewAircraftRepository.findByCrewIdAndAircraftId(
                                          flightCrew.getCrew()
                                                    .getId(),
                                          flightCrew.getFlight()
                                                    .getAircraft()
                                                    .getId()
                                  )
                                  .ifPresentOrElse(permit -> {
                                      if (permit.getPermitDate()
                                                .isAfter(flightCrew.getFlight()
                                                                   .getDepartureDate())) {
                                          throw badRequestException("Permit date is after flight date");
                                      }
                                  }, () -> {
                                      throw badRequestException("This crew has no permit to aircraft");
                                  });
        }

        return flightCrew;
    }
}
