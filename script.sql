CREATE TABLE crew
(
    id              SERIAL PRIMARY KEY,
    firstname       VARCHAR(128)        NOT NULL,
    lastname        VARCHAR(128)        NOT NULL,
    email           VARCHAR(128) UNIQUE NOT NULL,
    password        VARCHAR(64)         NOT NULL,
    birth_date      DATE                NOT NULL,
    employment_date DATE                NOT NULL,
    mkk_date        DATE,
    role            VARCHAR(32)         NOT NULL
);
CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL
);
CREATE TABLE city
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(32) UNIQUE          NOT NULL,
    country_id INT REFERENCES country (id) NOT NULL
);
CREATE TABLE airport
(
    code    CHAR(3) PRIMARY KEY,
    city_id INT REFERENCES city (id) NOT NULL
);
CREATE TABLE aircraft
(
    id    SERIAL PRIMARY KEY,
    model VARCHAR(32) UNIQUE NOT NULL
);
CREATE TABLE flight
(
    id                     BIGSERIAL PRIMARY KEY,
    flight_no              VARCHAR(16)                       NOT NULL,
    departure_airport_code CHAR(3) REFERENCES airport (code) NOT NULL,
    transit_airport_code   CHAR(3) REFERENCES airport (code),
    arrival_airport_code   CHAR(3) REFERENCES airport (code) NOT NULL,
    departure_date         DATE                              NOT NULL,
    aircraft_id            INT REFERENCES aircraft (id)      NOT NULL,
    time                   BIGINT                            NOT NULL

);
CREATE TABLE crew_aircraft
(
    id          BIGSERIAL PRIMARY KEY,
    crew_id     INT REFERENCES crew (id) ON DELETE CASCADE     NOT NULL,
    aircraft_id INT REFERENCES aircraft (id) ON DELETE CASCADE NOT NULL,
    permit_date DATE                                           NOT NULL
);
CREATE TABLE flight_crew
(
    id               BIGSERIAL PRIMARY KEY,
    crew_id          INT REFERENCES crew (id) ON DELETE CASCADE NOT NULL,
    flight_id        BIGINT REFERENCES flight (id)              NOT NULL,
    class_of_service VARCHAR(32)                                NOT NULL,
    is_turnaround    BOOLEAN                                    NOT NULL,
    is_passenger     BOOLEAN                                    NOT NULL
)