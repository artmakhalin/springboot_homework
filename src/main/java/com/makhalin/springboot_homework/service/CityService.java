package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.CityCreateEditDto;
import com.makhalin.springboot_homework.dto.CityFilter;
import com.makhalin.springboot_homework.dto.CityReadDto;
import com.makhalin.springboot_homework.mapper.CityCreateEditMapper;
import com.makhalin.springboot_homework.mapper.CityReadMapper;
import com.makhalin.springboot_homework.repository.CityRepository;
import com.makhalin.springboot_homework.repository.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makhalin.springboot_homework.entity.QCity.city;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;
    private final CityReadMapper cityReadMapper;
    private final CityCreateEditMapper cityCreateEditMapper;

    public Page<CityReadDto> findAll(CityFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                                  .add(filter.getCity(), city.name::containsIgnoreCase)
                                  .add(filter.getCountry(), city.country.name::containsIgnoreCase)
                                  .buildAnd();

        return cityRepository.findAll(predicate, pageable)
                .map(cityReadMapper::map);
    }

    public List<CityReadDto> findAll() {
        return cityRepository.findAll()
                             .stream()
                             .map(cityReadMapper::map)
                             .toList();
    }

    public Optional<CityReadDto> findById(Integer id) {
        return cityRepository.findById(id)
                             .map(cityReadMapper::map);
    }

    public List<CityReadDto> findAllByCountryId(Integer countryId) {
        return cityRepository.findAllByCountryId(countryId)
                             .stream()
                             .map(cityReadMapper::map)
                             .toList();
    }

    @Transactional
    public CityReadDto create(CityCreateEditDto cityDto) {
        return Optional.of(cityDto)
                       .map(cityCreateEditMapper::map)
                       .map(cityRepository::save)
                       .map(cityReadMapper::map)
                       .orElseThrow();
    }

    @Transactional
    public Optional<CityReadDto> update(Integer id, CityCreateEditDto cityDto) {
        return cityRepository.findById(id)
                             .map(entity -> cityCreateEditMapper.map(cityDto, entity))
                             .map(cityRepository::saveAndFlush)
                             .map(cityReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return cityRepository.findById(id)
                             .map(entity -> {
                                 cityRepository.delete(entity);
                                 cityRepository.flush();

                                 return true;
                             })
                             .orElse(false);
    }
}
