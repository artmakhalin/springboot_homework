package com.makhalin.springboot_homework.integration;

import com.makhalin.springboot_homework.AbstractTestBase;
import com.makhalin.springboot_homework.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static com.makhalin.springboot_homework.util.TestMocks.AircraftMock.*;
import static com.makhalin.springboot_homework.util.TestMocks.AirportMock.*;
import static com.makhalin.springboot_homework.util.TestMocks.CityMock.*;
import static com.makhalin.springboot_homework.util.TestMocks.CountryMock.*;
import static com.makhalin.springboot_homework.util.TestMocks.CrewAircraftMock.*;
import static com.makhalin.springboot_homework.util.TestMocks.CrewMock.*;
import static com.makhalin.springboot_homework.util.TestMocks.FlightMock.*;

public abstract class IntegrationTestBase extends AbstractTestBase {

    protected Aircraft boeing737 = getBoeing737();
    protected Aircraft boeing777 = getBoeing777();
    protected Aircraft airbus320 = getAirbus320();
    protected Aircraft airbus330 = getAirbus330();
    protected Country usa = getUsa();
    protected Country russia = getRussia();
    protected Country france = getFrance();
    protected Country uk = getUk();
    protected City newYork = getNewYork();
    protected City seattle = getSeattle();
    protected City moscow = getMoscow();
    protected City stPetersburg = getStPetersburg();
    protected City volgograd = getVolgograd();
    protected City paris = getParis();
    protected City lion = getLion();
    protected Airport jfk = getJfk();
    protected Airport sea = getSea();
    protected Airport svo = getSvo();
    protected Airport led = getLed();
    protected Airport vog = getVog();
    protected Airport cdg = getCdg();
    protected Airport ewr = getEwr();
    protected Crew alex = getAlex();
    protected Crew jake = getJake();
    protected Crew bob = getBob();
    protected Crew marta = getMarta();
    private CrewAircraft alexBoeing777 = getAlexBoeing777();
    private CrewAircraft alexAirbus320 = getAlexAirbus320();
    private CrewAircraft jakeBoeing737 = getJakeBoeing737();
    private CrewAircraft bobAirbus320 = getBobAirbus320();
    protected CrewAircraft bobBoeing777 = getBobBoeing777();
    protected Flight svoJfk = getSvoJfk();
    protected Flight svoLed = getSvoLed();
    protected Flight svoVog = getSvoVog();
    protected Flight svoCdg = getSvoCdg();
    protected Flight ledVog = getLedVog();
    protected Flight jfkCdg = getJfkCdg();
    protected FlightCrew ledVogAlex;

    @Autowired
    protected EntityManager entityManager;

    @BeforeEach
    void prepareDatabase() {
        saveAircraft();
        saveCountries();
        saveCities();
        saveAirports();
        saveCrew();
        saveCrewAircraft();
        saveFlights();
        saveFlightCrew();
    }

    private void saveAircraft() {
        entityManager.persist(boeing737);
        entityManager.persist(boeing777);
        entityManager.persist(airbus320);
        entityManager.persist(airbus330);
    }

    private void saveCountries() {
        entityManager.persist(usa);
        entityManager.persist(russia);
        entityManager.persist(france);
        entityManager.persist(uk);
    }

    private void saveCities() {
        newYork.setCountry(usa);
        entityManager.persist(newYork);
        seattle.setCountry(usa);
        entityManager.persist(seattle);
        moscow.setCountry(russia);
        entityManager.persist(moscow);
        stPetersburg.setCountry(russia);
        entityManager.persist(stPetersburg);
        volgograd.setCountry(russia);
        entityManager.persist(volgograd);
        paris.setCountry(france);
        entityManager.persist(paris);
        lion.setCountry(france);
        entityManager.persist(lion);
    }

    private void saveAirports() {
        jfk.setCity(newYork);
        entityManager.persist(jfk);
        sea.setCity(seattle);
        entityManager.persist(sea);
        svo.setCity(moscow);
        entityManager.persist(svo);
        led.setCity(stPetersburg);
        entityManager.persist(led);
        vog.setCity(volgograd);
        entityManager.persist(vog);
        cdg.setCity(paris);
        entityManager.persist(cdg);
        ewr.setCity(newYork);
        entityManager.persist(ewr);
    }

    private void saveCrew() {
        entityManager.persist(alex);
        entityManager.persist(bob);
        entityManager.persist(jake);
        entityManager.persist(marta);
    }

    private void saveCrewAircraft() {
        alexBoeing777.setCrew(alex);
        alexBoeing777.setAircraft(boeing777);
        entityManager.persist(alexBoeing777);
        alexAirbus320.setCrew(alex);
        alexAirbus320.setAircraft(airbus320);
        entityManager.persist(alexAirbus320);
        jakeBoeing737.setCrew(jake);
        jakeBoeing737.setAircraft(boeing737);
        entityManager.persist(jakeBoeing737);
        bobAirbus320.setCrew(bob);
        bobAirbus320.setAircraft(airbus320);
        entityManager.persist(bobAirbus320);
        bobBoeing777.setCrew(bob);
        bobBoeing777.setAircraft(boeing777);
        entityManager.persist(bobBoeing777);
    }

    private void saveFlights() {
        svoJfk.setDepartureAirport(svo);
        svoJfk.setArrivalAirport(jfk);
        svoJfk.setAircraft(boeing777);
        entityManager.persist(svoJfk);
        svoLed.setDepartureAirport(svo);
        svoLed.setArrivalAirport(led);
        svoLed.setAircraft(airbus320);
        entityManager.persist(svoLed);
        svoVog.setDepartureAirport(svo);
        svoVog.setArrivalAirport(vog);
        svoVog.setAircraft(airbus320);
        entityManager.persist(svoVog);
        svoCdg.setDepartureAirport(svo);
        svoCdg.setArrivalAirport(cdg);
        svoCdg.setAircraft(airbus320);
        entityManager.persist(svoCdg);
        ledVog.setDepartureAirport(led);
        ledVog.setArrivalAirport(vog);
        ledVog.setAircraft(airbus320);
        entityManager.persist(ledVog);
        jfkCdg.setDepartureAirport(jfk);
        jfkCdg.setArrivalAirport(cdg);
        jfkCdg.setAircraft(boeing777);
        entityManager.persist(jfkCdg);
    }

    private void saveFlightCrew() {
        entityManager.persist(getSvoJfkAlex());
        entityManager.persist(getSvoLedAlex());
        entityManager.persist(getSvoVogBob());
        entityManager.persist(getSvoVogAlex());
        entityManager.persist(getSvoCdgAlex());
        ledVogAlex = getLedVogAlex();
        entityManager.persist(ledVogAlex);
    }

    protected FlightCrew getJfkSeaAlex() {
        return FlightCrew.builder()
                         .classOfService(ClassOfService.BUSINESS)
                         .crew(alex)
                         .isTurnaround(false)
                         .isPassenger(false)
                         .build();
    }

    private FlightCrew getSvoJfkAlex() {
        return FlightCrew.builder()
                         .classOfService(ClassOfService.BUSINESS)
                         .flight(svoJfk)
                         .crew(alex)
                         .isTurnaround(false)
                         .isPassenger(false)
                         .build();
    }

    private FlightCrew getSvoLedAlex() {
        return FlightCrew.builder()
                         .classOfService(ClassOfService.BUSINESS)
                         .flight(svoLed)
                         .crew(alex)
                         .isTurnaround(true)
                         .isPassenger(false)
                         .build();
    }

    private FlightCrew getSvoVogBob() {
        return FlightCrew.builder()
                         .classOfService(ClassOfService.ECONOMY)
                         .flight(svoVog)
                         .crew(bob)
                         .isTurnaround(true)
                         .isPassenger(false)
                         .build();
    }

    private FlightCrew getSvoVogAlex() {
        return FlightCrew.builder()
                         .classOfService(ClassOfService.BUSINESS)
                         .flight(svoVog)
                         .crew(alex)
                         .isTurnaround(true)
                         .isPassenger(false)
                         .build();
    }

    private FlightCrew getSvoCdgAlex() {
        return FlightCrew.builder()
                         .classOfService(ClassOfService.BUSINESS)
                         .flight(svoCdg)
                         .crew(alex)
                         .isTurnaround(true)
                         .isPassenger(false)
                         .build();
    }

    private FlightCrew getLedVogAlex() {
        return FlightCrew.builder()
                         .classOfService(ClassOfService.BUSINESS)
                         .flight(ledVog)
                         .crew(alex)
                         .isTurnaround(true)
                         .isPassenger(false)
                         .build();
    }
}
