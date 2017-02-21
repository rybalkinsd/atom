-- DROP TABLE Users;

CREATE TABLE IF NOT EXISTS Users
(
    id     SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(255)       NOT NULL,
    password        VARCHAR(255) NOT NULL,
    mail        VARCHAR(255),
    register_date date
);