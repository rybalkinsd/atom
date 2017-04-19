begin;

drop schema if exists auth cascade;
create schema auth;

drop table if exists auth.reguser;
create table auth.reguser (
  id    serial             not null,
  login varchar(20) unique not null,
  hashcode integer not null,
  time   timestamp    not null,
  primary key (id)
);

drop table if exists auth.loguser;
create table auth.loguser (
  id     serial       not null,
  reguser_id integer not null references auth.reguser on delete cascade,
  token  varchar(100) not null,
  time   timestamp    not null,
  primary key (id)
);

drop table if exists game;
create table game (
  id     serial        not null,
  player integer       not null references auth.loguser on delete cascade,
  time   timestamp     not null,
  primary key(id)
);
commit;