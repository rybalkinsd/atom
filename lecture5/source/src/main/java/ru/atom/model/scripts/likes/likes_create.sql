-- psql -d tinderdb -U user -f likes_create.sql
-- DROP TABLE likes;

CREATE TABLE IF NOT EXISTS likes
(
    id     SERIAL PRIMARY KEY NOT NULL,
    source INTEGER            NOT NULL,
    target INTEGER            NOT NULL
);