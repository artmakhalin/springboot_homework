package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewFilter;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.mapper.CrewMapper;
import com.makhalin.springboot_homework.repository.CrewRepository;
import com.makhalin.springboot_homework.repository.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.makhalin.springboot_homework.entity.QCrew.crew;
import static com.makhalin.springboot_homework.exception.BadRequestException.badRequest;
import static com.makhalin.springboot_homework.exception.BadRequestException.badRequestException;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFound;
import static com.makhalin.springboot_homework.exception.NotFoundException.notFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewService implements UserDetailsService {

    private final CrewRepository crewRepository;
    private final CrewMapper crewMapper;

    @Transactional
    public CrewReadDto create(CrewCreateEditDto crewDto) {
        validateCrewEmploymentDate(crewDto);
        return Optional.of(crewDto)
                       .map(crewMapper::mapCreate)
                       .map(crewRepository::save)
                       .map(crewMapper::mapRead)
                       .orElseThrow(badRequest("Error during save crew"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return crewRepository.findByEmail(email)
                             .map(crew -> new User(
                                     crew.getEmail(),
                                     crew.getPassword(),
                                     Collections.singleton(crew.getRole())
                             ))
                             .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + email));
    }

    public CrewReadDto findByEmail(String email) {
        return crewRepository.findByEmail(email)
                .map(crewMapper::mapRead)
                .orElseThrow(notFound("Crew not found with email " + email));
    }

    public Page<CrewReadDto> findAllByFilter(CrewFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                                  .add(filter.getEmail(), crew.email::containsIgnoreCase)
                                  .add(filter.getFirstname(), crew.personalInfo.firstname::containsIgnoreCase)
                                  .add(filter.getLastname(), crew.personalInfo.lastname::containsIgnoreCase)
                                  .buildAnd();

        return crewRepository.findAll(predicate, pageable)
                             .map(crewMapper::mapRead);
    }

    public CrewReadDto findById(Integer id) {
        return crewRepository.findById(id)
                             .map(crewMapper::mapRead)
                             .orElseThrow(notFound("Crew not found with id " + id));
    }

    @Transactional
    public CrewReadDto update(Integer id, CrewCreateEditDto crewDto) {
        validateCrewEmploymentDate(crewDto);
        return crewRepository.findById(id)
                .map(entity -> crewMapper.mapUpdate(crewDto, entity))
                .map(crewRepository::saveAndFlush)
                .map(crewMapper::mapRead)
                .orElseThrow(notFound("Crew not found with id " + id));
    }

    @Transactional
    public void delete(Integer id) {
        crewRepository.findById(id)
                .ifPresentOrElse(entity -> {
                    crewRepository.delete(entity);
                    crewRepository.flush();
                }, () -> {
                    throw notFoundException("Crew not found with id " + id);
                });
    }

    private void validateCrewEmploymentDate(CrewCreateEditDto crewDto) {
        if (crewDto.getEmploymentDate()
                   .isBefore(crewDto.getBirthDate()
                                    .plusYears(18))) {
            throw badRequestException("Employee must be 18 years old");
        }
    }
}
