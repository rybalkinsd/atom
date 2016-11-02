-- DROP TABLE likes;

CREATE TABLE IF NOT EXISTS Like_entity
(
    id     SERIAL PRIMARY KEY NOT NULL,
    source INTEGER            NOT NULL,
    target INTEGER            NOT NULL
);