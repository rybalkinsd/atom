-- DROP TABLE persons;

CREATE TABLE IF NOT EXISTS person
(
    id      SERIAL PRIMARY KEY NOT NULL,
    name    VARCHAR(100)       NOT NULL,
    gender  VARCHAR(10)        NOT NULL,
    age     INTEGER            NOT NULL
);
