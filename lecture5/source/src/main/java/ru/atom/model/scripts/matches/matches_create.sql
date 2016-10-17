-- DROP TABLE matches;

CREATE TABLE IF NOT EXISTS matches
(
    id SERIAL PRIMARY KEY NOT NULL,
    a INTEGER             NOT NULL,
    b INTEGER             NOT NULL
);