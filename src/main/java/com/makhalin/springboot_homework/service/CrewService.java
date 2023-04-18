package com.makhalin.springboot_homework.service;

import com.makhalin.springboot_homework.dto.CrewCreateEditDto;
import com.makhalin.springboot_homework.dto.CrewReadDto;
import com.makhalin.springboot_homework.mapper.CrewMapper;
import com.makhalin.springboot_homework.repository.CrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.makhalin.springboot_homework.exception.BadRequestException.badRequestException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrewService implements UserDetailsService {

    private final CrewRepository crewRepository;
    private final CrewMapper crewMapper;

    @Transactional
    public CrewReadDto create(CrewCreateEditDto crewDto) {
        if (crewDto.getEmploymentDate().isBefore(crewDto.getBirthDate().plusYears(18))) {
            throw badRequestException("Employee must be 18 years old");
        }
        return Optional.of(crewDto)
                .map(crewMapper::mapCreate)
                .map(crewRepository::save)
                .map(crewMapper::mapRead)
                .orElseThrow();
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
}
