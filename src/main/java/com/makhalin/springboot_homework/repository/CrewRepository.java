package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CrewRepository extends
        JpaRepository<Crew, Integer>,
        FilterCrewRepository,
        QuerydslPredicateExecutor<Crew> {
}
