package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.mapper.CountryMapper;
import com.makhalin.springboot_homework.repository.CountryRepository;
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
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public List<CountryReadDto> findAll() {
        return countryRepository.findAll()
                                .stream()
                                .map(countryMapper::mapRead)
                                .toList();
    }

    public CountryReadDto findById(Integer id) {
        return countryRepository.findById(id)
                                .map(countryMapper::mapRead)
                                .orElseThrow(notFound("Country not found"));
    }

    @Transactional
    public CountryReadDto create(CountryCreateEditDto countryDto) {
        return Optional.of(countryDto)
                       .map(countryMapper::mapCreate)
                       .map(countryRepository::save)
                       .map(countryMapper::mapRead)
                       .orElseThrow(badRequest("Bad request"));
    }

    @Transactional
    public CountryReadDto update(Integer id, CountryCreateEditDto countryDto) {
        return countryRepository.findById(id)
                                .map(entity -> countryMapper.mapUpdate(countryDto, entity))
                                .map(countryRepository::saveAndFlush)
                                .map(countryMapper::mapRead)
                                .orElseThrow(notFound("Country not found"));
    }

    @Transactional
    public void delete(Integer id) {
        countryRepository.findById(id)
                         .ifPresentOrElse(entity -> {
                             countryRepository.delete(entity);
                             countryRepository.flush();
                         }, () -> {
                             throw notFoundException("Country not found");
                         });
    }
}
