begin;

drop schema if exists bombergirl cascade;
create schema bombergirl;

drop table if exists bombergirl.user;
  create table bombergirl.user (
    id                     serial                not null,
    login                  varchar(20)   unique  not null,
    password               varchar(20)           not null,
    registrationDate       timestamp             not null,

    primary key (id)
  );

commit;
