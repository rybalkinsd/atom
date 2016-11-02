-- DROP TABLE persons;

CREATE TABLE IF NOT EXISTS person
(
    id          SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(255)       NOT NULL,
    gender      VARCHAR(255)       NOT NULL,
    age         INTEGER            NOT NULL,
    url         VARCHAR(1000)      NOT NULL,
    width       INTEGER            NOT NULL,
    height      INTEGER            NOT NULL,
    latitude    DOUBLE PRECISION   NOT NULL,
    longitude   DOUBLE PRECISION   NOT NULL,
    instagramurl VARCHAR(1000)
);
