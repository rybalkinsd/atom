begin;

drop schema if exists auth cascade;
create schema auth;

drop table if exists auth.user;
create table auth.user (
  id    serial             not null,
  login varchar(20) unique not null,
  password varchar(30) not null,

  primary key (id)
);

drop table if exists auth.token;
create table auth.token (
  id serial not null,
  token long not null,
  username varchar(20) not null,

  primary key (id)
);

commit;
