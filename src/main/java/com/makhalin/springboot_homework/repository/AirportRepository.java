package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, String> {

    /*Выводит все аэропорты указанной страны*/

    @Query("select a from Airport a " +
            "join fetch a.city ct " +
            "join fetch ct.country cn " +
            "where cn.name = :countryName")
   List<Airport> findByCountryName(String countryName);
}
