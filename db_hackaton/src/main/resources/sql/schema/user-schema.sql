begin;

drop schema if exists game cascade;
create schema game;

drop table if exists game.user;
create table game.user (
  id    serial             not null,
  login varchar(20) unique not null,
  password varchar(40)     not null,
  registration_date timestamp not null,
  primary key (id)
);

drop table if exists game.token;
create table game.token (
  id        serial       not null,
  user_id   integer      references game.user on delete cascade on update cascade,
  token int8 not null,

  primary key (id)
);

commit;