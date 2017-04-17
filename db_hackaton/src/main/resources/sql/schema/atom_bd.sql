--подключиться к базе:
--psql -h wtfis.ru -U atom41 -d chatdb_atom41
--накатить схему:
--psql -h wtfis.ru -U atom41 -a -d chatdb_atom41 -f ./db_hackaton/src/main/resources/sql/schema/atom_bd.sql
--psql -h wtfis.ru -U atom41 -a -d chatdb_atom41 -f /home/mkai/IdeaProjects/atom/db_hackaton/src/main/resources/sql/schema/atom_bd.sql
--вывести список таблиц и полей:
--select table_name, column_name from information_schema.columns where table_schema='mm' or table_schema='auth';
begin;

drop schema if exists auth cascade;
create schema auth;

drop table if exists auth.user;
create table auth.user (
  id            serial          not null,
  login         varchar(20)     unique not null,
  passwd        varchar(20)     not null,
  registration_date TIMESTAMP   not null    DEFAULT NOW(),
  primary key (id)
);

drop table if exists auth.token;
create table auth.token (
  id            serial          not null,
  token         varchar(20)     unique not null,
  user_id       integer         unique not null     references auth.user on delete cascade,
  primary key (id)
);

drop schema if exists mm cascade;
create schema mm;

drop table if exists mm.match;
create table mm.match (
  id            serial          not null,
  match_date    TIMESTAMP       not null,
  primary key (id)
);

drop table if exists mm.personal_result;
create table mm.personal_result (
  id            serial          not null,
  match_id      integer         not null        references mm.match,
  user_id       integer         not null        references auth.user,
  score         integer         not null,
  primary key (id)
);


commit;
