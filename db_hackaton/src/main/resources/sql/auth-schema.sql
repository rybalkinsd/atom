begin;

drop schema if exists auth cascade;
create schema auth;

drop table if exists auth.user;
create table auth.user (
  id    serial             not null,
  login varchar(20) unique not null,
  passwordHash int8 not null,
  registrationDate timestamp not null,
  token int8,
  
  primary key (id)
);

commit;
