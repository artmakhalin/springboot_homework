package com.makhalin.springboot_homework.repository;

import com.makhalin.springboot_homework.entity.Airport;
import com.makhalin.springboot_homework.entity.City;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.makhalin.springboot_homework.entity.QAirport.airport;
import static com.makhalin.springboot_homework.entity.QCity.city;
import static com.makhalin.springboot_homework.entity.QCountry.country;
import static org.hibernate.graph.GraphSemantic.LOAD;

@Repository
public class AirportRepository extends RepositoryBase<String, Airport> {

    public AirportRepository(EntityManager entityManager) {
        super(Airport.class, entityManager);
    }

    /*Выводит все аэропорты указанной страны*/

    public List<Airport> findByCountryName(String countryName) {
        var airportGraph = entityManager.createEntityGraph(Airport.class);
        airportGraph.addAttributeNodes("city");
        var citySubgraph = airportGraph.addSubgraph("city", City.class);
        citySubgraph.addAttributeNodes("country");

        return new JPAQuery<Airport>(entityManager)
                .select(airport)
                .from(airport)
                .join(airport.city, city)
                .join(city.country, country)
                .where(country.name.toUpperCase().eq(countryName.toUpperCase()))
                .setHint(LOAD.getJpaHintName(), airportGraph)
                .fetch();
    }
}
