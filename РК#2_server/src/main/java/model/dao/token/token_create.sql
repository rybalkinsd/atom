-- DROP TABLE Tokens;

CREATE TABLE IF NOT EXISTS Tokens
(
    id     SERIAL PRIMARY KEY NOT NULL,
    register_date date
);