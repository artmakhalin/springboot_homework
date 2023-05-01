--liquibase formatted sql

--changeset makhalin:1
ALTER TABLE crew_aircraft
    ADD CONSTRAINT crew_aircraft_unique
        UNIQUE (crew_id, aircraft_id);