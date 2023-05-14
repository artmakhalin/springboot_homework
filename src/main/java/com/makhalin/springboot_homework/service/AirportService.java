package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.AirportCreateEditDto;
import com.makhalin.springboot_homework.dto.AirportFilter;
import com.makhalin.springboot_homework.dto.AirportReadDto;
import com.makhalin.springboot_homework.mapper.AirportMapper;
import com.makhalin.springboot_homework.repository.AirportRepository;
import com.makhalin.springboot_homework.repository.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makhalin.springboot_homework.entity.QAirport.airport;
import static com.makhalin.springboot_homework.exception.BadRequestException.badRequest;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFound;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    public Page<AirportReadDto> findAll(AirportFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                                  .add(filter.getAirport(), airport.code::containsIgnoreCase)
                                  .add(filter.getCity(), airport.city.name::containsIgnoreCase)
                                  .add(filter.getCountry(), airport.city.country.name::containsIgnoreCase)
                                  .buildAnd();

        return airportRepository.findAll(predicate, pageable)
                                .map(airportMapper::mapRead);
    }

    public List<AirportReadDto> findAll() {
        return airportRepository.findAll()
                                .stream()
                                .map(airportMapper::mapRead)
                                .toList();
    }

    public AirportReadDto findById(Integer id) {
        return airportRepository.findById(id)
                                .map(airportMapper::mapRead)
                                .orElseThrow(notFound("Airport not found with id " + id));
    }

    public List<AirportReadDto> findAllByCityId(Integer cityId) {
        return airportRepository.findAllByCityId(cityId)
                                .stream()
                                .map(airportMapper::mapRead)
                                .toList();
    }

    @Transactional
    public AirportReadDto create(AirportCreateEditDto airportDto) {
        return Optional.of(airportDto)
                       .map(airportMapper::mapCreate)
                       .map(airportRepository::save)
                       .map(airportMapper::mapRead)
                       .orElseThrow(badRequest("Error during save airport"));
    }

    @Transactional
    public AirportReadDto update(Integer id, AirportCreateEditDto airportDto) {
        return airportRepository.findById(id)
                                .map(entity -> airportMapper.mapUpdate(airportDto, entity))
                                .map(airportRepository::saveAndFlush)
                                .map(airportMapper::mapRead)
                                .orElseThrow(notFound("Airport not found with id " + id));
    }

    @Transactional
    public void delete(Integer id) {
        airportRepository.findById(id)
                         .ifPresentOrElse(entity -> {
                             airportRepository.delete(entity);
                             airportRepository.flush();
                         }, () -> {
                             throw notFoundException("Airport not found with id " + id);
                         });
    }
}
