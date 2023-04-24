package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface CrewRepository extends
        JpaRepository<Crew, Integer>,
        FilterCrewRepository,
        QuerydslPredicateExecutor<Crew> {

    Optional<Crew> findByEmail(String email);
}
