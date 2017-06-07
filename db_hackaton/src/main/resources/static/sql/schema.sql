BEGIN;

DROP SCHEMA IF EXISTS game CASCADE;
CREATE SCHEMA game;

DROP TABLE IF EXISTS game.usser;
CREATE TABLE game.usser (
  id       serial              NOT NULL,
  name     VARCHAR(20) UNIQUE  NOT NULL,
  password VARCHAR(256) NOT NULL,
  regdate  TIMESTAMP,
  token    VARCHAR(20) UNIQUE,

  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS game.result;
CREATE TABLE game.result (
  id      SERIAL  NOT NULL,
  game_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL REFERENCES game.usser on DELETE CASCADE,
  score   INTEGER,

  PRIMARY KEY (id)
);

COMMIT;