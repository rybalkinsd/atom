begin;

drop schema if exists auth cascade;
create schema auth;

drop table if exists auth.reguser;
create table auth.reguser (
  id               serial               not null,
  login            varchar(20) unique   not null,
  hashcode         integer              not null,
  time             timestamp            not null,
  primary key (id)
);

drop table if exists auth.loguser;
create table auth.loguser (
  id             serial            not null,
  reguser_id     integer           not null     references auth.reguser on delete cascade,
  token          varchar(100)      not null,
  time           timestamp         not null,
  primary key (id)
);


drop schema if exists mm cascade;
create schema mm;

drop table if exists mm.result;
create table mm.result (
  id            serial          not null,
  match_id      integer         not null,
  user_id       integer         not null        references auth.reguser,
  score         integer         not null,
  primary key (id)
);

drop table if exists mm.match;
create table mm.match (
  id            serial          not null,
  match_date    TIMESTAMP       not null,
  primary key (id)
);
commit;