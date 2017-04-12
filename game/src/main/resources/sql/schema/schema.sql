begin;

drop schema if exists game cascade;
create schema game;

drop table if exists game.user;
create table game.user (
  id    serial             not null,
  login varchar(20) unique not null,
  password varchar(255) not null,
  create_at   timestamp    not null,

  primary key (id)
);

drop table if exists game.token;
create table game.token (
  id     serial       not null,
  user_id integer      not null references game.user on delete cascade,
  time   timestamp    not null,
  value  varchar(140) not null,

  primary key (id)
);

commit;
