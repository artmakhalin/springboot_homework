package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.City;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.makhalin.springboot_homework.entity.QAirport.airport;
import static com.makhalin.springboot_homework.entity.QCity.city;
import static com.makhalin.springboot_homework.entity.QCrew.crew;
import static com.makhalin.springboot_homework.entity.QFlight.flight;
import static com.makhalin.springboot_homework.entity.QFlightCrew.flightCrew;

@Repository
public class CityRepository extends RepositoryBase<Integer, City> {

    public CityRepository(EntityManager entityManager) {
        super(City.class, entityManager);
    }

    /*Выводит количество посещений городов указанным БП*/

    List<Tuple> findCityAndCountOfVisitsByCrew(String email) {
        return new JPAQuery<>(entityManager)
                .select(city, city.count())
                .from(city)
                .join(city.airports, airport)
                .join(airport.arrivalFlights, flight)
                .join(flight.flightCrews, flightCrew)
                .join(flightCrew.crew, crew)
                .where(crew.email.eq(email))
                .groupBy(city)
                .orderBy(city.count()
                             .asc())
                .fetch();
    }
}
