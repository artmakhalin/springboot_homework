--liquibase formatted sql

--changeset makhalin:1
ALTER TABLE flight
    ADD COLUMN version BIGINT;