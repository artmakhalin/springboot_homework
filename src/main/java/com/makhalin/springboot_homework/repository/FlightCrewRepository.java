package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.FlightCrew;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class FlightCrewRepository extends RepositoryBase<Long, FlightCrew> {

    public FlightCrewRepository(EntityManager entityManager) {
        super(FlightCrew.class, entityManager);
    }
}
