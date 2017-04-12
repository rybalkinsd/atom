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
  value  varchar(140) not null,
  create_at   timestamp    not null,

  primary key (id)
);

drop table if exists game.game;
create table game.game (
  id    serial             not null,
  player1 integer      not null references game.user,
  player2 integer      not null references game.user,
  player3 integer      not null references game.user,
  player4 integer      not null references game.user,
  create_at   timestamp    not null,

  primary key (id)
);

drop table if exists game.score;
create table game.score (
  id    serial             not null,
  user_id integer      not null references game.user on delete cascade,
  score integer default 0,
  game_id integer not null references game.game,

  primary key (id)
);

commit;
