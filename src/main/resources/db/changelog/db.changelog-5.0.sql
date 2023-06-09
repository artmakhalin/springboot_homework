--liquibase formatted sql

--changeset makhalin:1
ALTER TABLE crew_aircraft
    ADD CONSTRAINT crew_aircraft_unique
        UNIQUE (crew_id, aircraft_id);

--changeset makhalin:2
ALTER TABLE flight
    ADD CONSTRAINT flight_date_unique
        UNIQUE (flight_no, departure_date);

--changeset makhalin:3
ALTER TABLE flight_crew
    ADD CONSTRAINT flight_crew_unique
        UNIQUE (flight_id, crew_id);