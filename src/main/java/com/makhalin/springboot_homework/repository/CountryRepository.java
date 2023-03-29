package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CountryRepository extends RepositoryBase<Integer, Country> {

    public CountryRepository(EntityManager entityManager) {
        super(Country.class, entityManager);
    }
}
