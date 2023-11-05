package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.*;
import com.makhalin.springboot_homework.entity.Flight;
import com.makhalin.springboot_homework.mapper.FlightMapper;
import com.makhalin.springboot_homework.repository.FlightRepository;
import com.makhalin.springboot_homework.repository.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static com.makhalin.springboot_homework.entity.QFlight.flight;
import static com.makhalin.springboot_homework.exception.BadRequestException.badRequest;
import static com.makhalin.springboot_homework.exception.BadRequestException.badRequestException;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFound;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFoundException;
import static com.makhalin.springboot_homework.util.FlightTimeUtil.hoursFromSec;
import static com.makhalin.springboot_homework.util.FlightTimeUtil.minFromSec;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public Page<FlightReadDto> findAll(FlightFilter filter, Pageable pageable) {
        if (filter.getYear() == null && filter.getMonth() != null) {
            throw badRequestException("Invalid filter: year could not be null");
        }
        var predicate = QPredicate.builder()
                                  .add(filter.getArrivalAirport(), flight.arrivalAirport.code::containsIgnoreCase)
                                  .add(filter.getDepartureAirport(), flight.departureAirport.code::containsIgnoreCase)
                                  .add(filter.getAircraft(), flight.aircraft.model::containsIgnoreCase)
                                  .add(filter.getYear(), year -> {
                                      LocalDate startPeriod;
                                      LocalDate endPeriod;
                                      if (filter.getMonth() != null) {
                                          startPeriod = LocalDate.of(year, filter.getMonth(), 1);
                                          endPeriod = startPeriod.plusDays(startPeriod.lengthOfMonth() - 1);
                                      } else {
                                          startPeriod = LocalDate.of(year, 1, 1);
                                          endPeriod = LocalDate.of(year, 12, 31);
                                      }

                                      return flight.departureDate.between(
                                              startPeriod,
                                              endPeriod
                                      );
                                  })
                                  .buildAnd();

        return flightRepository.findAll(predicate, pageable)
                               .map(flightMapper::mapRead);
    }

    public MonthlyFlightsReadDto findAllByCrewAndMonth(FlightFilter filter) {
        var flights = flightRepository.findByCrewAndMonth(filter)
                                      .stream()
                                      .toList();
        var totalTime = flights.stream()
                               .map(Flight::getTime)
                               .mapToLong(Long::longValue)
                               .sum();
        var flightTime = new FlightTimeReadDto(hoursFromSec(totalTime),
                minFromSec(totalTime));
        return new MonthlyFlightsReadDto(
                flightTime,
                flights.stream()
                       .map(flightMapper::mapRead)
                       .toList()
        );
    }

    public YearStatisticsReadDto findStatisticsByCrewAndYear(FlightFilter filter) {
        var result = new HashMap<Integer, FlightTimeReadDto>();
        var totalTime = new AtomicLong(0L);
        flightRepository.findMonthlyFlightTimeStatisticsByCrewAndYear(filter)
                        .forEach((key, value) -> {
                                    result.put(
                                            key,
                                            new FlightTimeReadDto(
                                                    hoursFromSec(value),
                                                    minFromSec(value)
                                            )
                                    );
                                    totalTime.addAndGet(value);
                                }
                        );

        return new YearStatisticsReadDto(result, new FlightTimeReadDto(
                hoursFromSec(totalTime.get()),
                minFromSec(totalTime.get())
        ));
    }

    public List<FlightReadDto> findAll() {
        return flightRepository.findAll()
                               .stream()
                               .map(flightMapper::mapRead)
                               .toList();
    }

    public FlightReadDto findById(Long id) {
        return flightRepository.findById(id)
                               .map(flightMapper::mapRead)
                               .orElseThrow(notFound("Flight not found with id " + id));
    }

    @Transactional
    public FlightReadDto create(FlightCreateEditDto flightDto) {
        return Optional.of(flightDto)
                       .map(flightMapper::mapCreate)
                       .map(flightRepository::save)
                       .map(flightMapper::mapRead)
                       .orElseThrow(badRequest("Error during save flight"));
    }

    @Transactional
    public FlightReadDto update(Long id, FlightCreateEditDto flightDto) {
        return flightRepository.findById(id)
                               .map(entity -> flightMapper.mapUpdate(flightDto, entity))
                               .map(flightRepository::saveAndFlush)
                               .map(flightMapper::mapRead)
                               .orElseThrow(notFound("Flight not found with id " + id));
    }

    @Transactional
    public void delete(Long id) {
        flightRepository.findById(id)
                        .ifPresentOrElse(entity -> {
                            flightRepository.delete(entity);
                            flightRepository.flush();
                        }, () -> {
                            throw notFoundException("Flight not found with id " + id);
                        });
    }
}
