begin;

drop schema if exists game cascade;
create schema game;

drop table if exists game.user;
create table game.user (
  id    serial                not null,
  login varchar(20) unique    not null,
  password varchar(150)        not null,
  registration_date timestamp not null,
  primary key (id)
);

drop table if exists game.token;
create table game.token (
  id        serial       not null,
  user_id   integer,
  token int8 not null,
  foreign key (user_id) references game.user on delete cascade on update cascade,
  primary key (id)
);

drop table if exists game.result;
create table game.result (
  id        serial          not null,
  user_id   integer,
  game_id   integer         not null,
  score     integer         not null,
  foreign key (user_id) references game.user on update cascade,
  primary key (id)
);

commit;