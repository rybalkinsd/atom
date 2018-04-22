begin;

drop schema if exists chat cascade;
create schema chat;

drop table if exists chat.player;
create table chat.player (
  id        serial             not null,
  login     varchar(20) unique not null,
  rating    integer            not null,

  primary key (id)
);

commit;
