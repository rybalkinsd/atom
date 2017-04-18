begin;

drop schema if exists bomber cascade;
create schema bomber;

drop table if exists bomber.user;
create table bomber.user (
  id    serial             not null,
  login varchar(20) unique not null,
  passwordHash int8 not null,
  registrationDate timestamp not null,
  token int8,

  primary key (id)
);

drop table if exists bomber.result;
create table bomber.result (
  gameId  integer not null,
  userId  integer not null references bomber.user (id),
  result  integer not null,

  primary key (gameId, userId)
);

commit;
