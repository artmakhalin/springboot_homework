package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.CountryCreateEditDto;
import com.makhalin.springboot_homework.dto.CountryReadDto;
import com.makhalin.springboot_homework.mapper.CountryCreateEditMapper;
import com.makhalin.springboot_homework.mapper.CountryReadMapper;
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
    private final CountryReadMapper countryReadMapper;
    private final CountryCreateEditMapper countryCreateEditMapper;

    public List<CountryReadDto> findAll() {
        return countryRepository.findAll()
                                .stream()
                                .map(countryReadMapper::map)
                                .toList();
    }

    public Optional<CountryReadDto> findById(Integer id) {
        return countryRepository.findById(id)
                                .map(countryReadMapper::map);
    }

    @Transactional
    public CountryReadDto create(CountryCreateEditDto countryDto) {
        return Optional.of(countryDto)
                       .map(countryCreateEditMapper::map)
                       .map(countryRepository::save)
                       .map(countryReadMapper::map)
                       .orElseThrow();
    }

    @Transactional
    public Optional<CountryReadDto> update(Integer id, CountryCreateEditDto countryDto) {
        return countryRepository.findById(id)
                                .map(entity -> countryCreateEditMapper.map(countryDto, entity))
                                .map(countryRepository::saveAndFlush)
                                .map(countryReadMapper::map);
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
