begin;

drop schema if exists game cascade;
create schema game;

drop table if exists game.registered_users;
create table game.registered_users (
  id    serial             not null,
  name varchar(20) unique not null,
  password varchar(20) unique not null,
  token bigint unique,

  primary key (id)
);

commit;
