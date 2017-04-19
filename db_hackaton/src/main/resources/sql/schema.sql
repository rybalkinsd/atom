begin;

drop schema if exists bombergirl cascade;
create schema bombergirl;

drop table if exists bombergirl.user;
create table bombergirl.user (
  id                     serial                not null,
  login                  varchar(20)   unique  not null,
  password               varchar(200)           not null,
  registrationDate       timestamp             not null,

  primary key (id)
);

drop table if exists bombergirl.token;
create table bombergirl.token (
  idToken                serial                not null,
  value                  bigint      unique    not null,
  idUser                 integer               not null,
  primary key (idToken),
  foreign key (idUser) references bombergirl.user(id) on delete cascade
);


commit;
