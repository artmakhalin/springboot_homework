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

    public Optional<CountryReadDto> findById(Integer id) {
        return countryRepository.findById(id)
                                .map(countryMapper::mapRead);
    }

    @Transactional
    public CountryReadDto create(CountryCreateEditDto countryDto) {
        return Optional.of(countryDto)
                       .map(countryMapper::mapCreate)
                       .map(countryRepository::save)
                       .map(countryMapper::mapRead)
                       .orElseThrow();
    }

    @Transactional
    public Optional<CountryReadDto> update(Integer id, CountryCreateEditDto countryDto) {
        return countryRepository.findById(id)
                                .map(entity -> countryMapper.mapUpdate(countryDto, entity))
                                .map(countryRepository::saveAndFlush)
                                .map(countryMapper::mapRead);
    }

    @Transactional
    public boolean delete(Integer id) {
        return countryRepository.findById(id)
                                .map(entity -> {
                                    countryRepository.delete(entity);
                                    countryRepository.flush();

                                    return true;
                                })
                                .orElse(false);
    }
}
