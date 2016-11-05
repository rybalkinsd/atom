-- DROP TABLE Matches;

CREATE TABLE IF NOT EXISTS Matches
(
    users    SERIAL PRIMARY KEY NOT NULL,
    token       INTEGER            NOT NULL
);