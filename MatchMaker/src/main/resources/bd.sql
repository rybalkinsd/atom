begin;

drop schema if exists game cascade;
create schema game;

drop table if exists game;
create table game (
  id integer unique not null,
  login varchar(20) unique not null
);

commit;