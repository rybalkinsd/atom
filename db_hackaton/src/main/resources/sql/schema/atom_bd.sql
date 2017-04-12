begin;

drop schema if exists auth cascade;
create schema auth;

drop table if exists auth.user;
create table auth.user (
  id    serial              not null,
  login varchar(20)         unique not null,
  passwd varchar(20)        not null,
  registration_date         TIMESTAMP not null DEFAULT NOW(),
  primary key (id)
);

drop table if exists auth.token;
create table auth.token (
  id    serial      not null,
  token varchar(20) unique not null,
  user_id integer   unique not null references auth.user on delete cascade,
  primary key (id)
);

drop table if exists auth.token;
create table mm.sessions (
  id    serial      not null,
  users integer[][] not null,
  primary key (id);
);

commit;
