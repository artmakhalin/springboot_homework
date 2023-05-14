--liquibase formatted sql

--changeset makhalin:1
ALTER TABLE airport
    ADD COLUMN id SERIAL;

--changeset makhalin:2
ALTER TABLE flight
    DROP CONSTRAINT flight_arrival_airport_code_fkey;

--changeset makhalin:3
ALTER TABLE flight
    DROP CONSTRAINT flight_departure_airport_code_fkey;

--changeset makhalin:4
ALTER TABLE flight
    DROP CONSTRAINT flight_transit_airport_code_fkey;

--changeset makhalin:5
ALTER TABLE airport
    DROP CONSTRAINT airport_pkey;

--changeset makhalin:6
ALTER TABLE airport
    ADD PRIMARY KEY (id);

--changeset makhalin:7
ALTER TABLE flight
    RENAME COLUMN departure_airport_code TO departure_airport_id;

--changeset makhalin:8
ALTER TABLE flight
    RENAME COLUMN arrival_airport_code TO arrival_airport_id;

--changeset makhalin:9
ALTER TABLE flight
    RENAME COLUMN transit_airport_code TO transit_airport_id;

--changeset makhalin:10
ALTER TABLE flight
    ALTER COLUMN departure_airport_id TYPE INTEGER USING departure_airport_id::INTEGER;

--changeset makhalin:11
ALTER TABLE flight
    ALTER COLUMN arrival_airport_id TYPE INTEGER USING arrival_airport_id::INTEGER;

--changeset makhalin:12
ALTER TABLE flight
    ALTER COLUMN transit_airport_id TYPE INTEGER USING transit_airport_id::INTEGER;

--changeset makhalin:13
ALTER TABLE flight
    ADD CONSTRAINT flight_arrival_airport_id_fkey
        FOREIGN KEY (arrival_airport_id)
            REFERENCES airport (id);

--changeset makhalin:14
ALTER TABLE flight
    ADD CONSTRAINT flight_departure_airport_id_fkey
        FOREIGN KEY (departure_airport_id)
            REFERENCES airport (id);

--changeset makhalin:15
ALTER TABLE flight
    ADD CONSTRAINT flight_transit_airport_id_fkey
        FOREIGN KEY (transit_airport_id)
            REFERENCES airport (id);

--changeset makhalin:16
ALTER TABLE airport
    ADD CONSTRAINT airport_code_unique UNIQUE (code);
