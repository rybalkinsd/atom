begin;

drop schema if exists auth cascade;
create schema auth;

drop table if exists auth.reguser;
create table auth.reguser (
  id    serial             not null,
  login varchar(20) unique not null,
  password  varchar(20) not null,
  time   timestamp    not null,
  primary key (id)
);

drop table if exists auth.loguser;
create table auth.loguser (
  id     serial       not null,
  reg_user_id integer      not null references auth.reguser on delete cascade,
  token  integer not null,
  primary key (id)
);

commit;