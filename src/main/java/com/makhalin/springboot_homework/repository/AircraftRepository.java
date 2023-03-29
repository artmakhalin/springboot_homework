package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Aircraft;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AircraftRepository extends RepositoryBase<Integer, Aircraft> {

    public AircraftRepository(EntityManager entityManager) {
        super(Aircraft.class, entityManager);
    }
}
