package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.CrewAircraft;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CrewAircraftRepository extends RepositoryBase<Long, CrewAircraft> {

    public CrewAircraftRepository(EntityManager entityManager) {
        super(CrewAircraft.class, entityManager);
    }
}
