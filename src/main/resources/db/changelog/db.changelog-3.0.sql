--liquibase formatted sql

--changeset makhalin:1
ALTER TABLE crew
    ALTER COLUMN password TYPE VARCHAR(128);