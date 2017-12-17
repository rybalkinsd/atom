begin;

drop schema if exists mm cascade;
create schema mm;

drop table if exists mm.games;
create table mm.games (
  id     BIGINT    not null,
  time   timestamp    not null,

  primary key (id)
);

drop table if exists mm.players;
create table mm.players (
  id      serial                not null,
  name    varchar(20)  unique   not null,
  game_id BIGINT                  not null references mm.games on delete cascade,

  primary key (id)
);


commit;