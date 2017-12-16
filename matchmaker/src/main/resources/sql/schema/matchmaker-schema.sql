begin;

drop schema if exists matchmaker cascade;
create schema matchmaker;

drop table if exists matchmaker.game_status;
create table matchmaker.game_status (
    id int4 primary key,
    name varchar(20) unique not null
);

insert into matchmaker.game_status values (1, 'awaiting'), (2, 'launched'), (3, 'finished');

drop table if exists matchmaker.player_status;
create table matchmaker.player_status (
    id int4 primary key,
    name varchar(20) unique not null
);

insert into matchmaker.player_status values (1, 'onine'), (2, 'offline');

drop table if exists matchmaker.game;
create table matchmaker.game (
    id int8 primary key,
    status int4 references matchmaker.game_status (id) on delete restrict
);

drop sequence if exists matchmaker.player_id_seq;
create table matchmaker.player (
    id serial primary key,
    login varchar(30) unique not null,
    password varchar(20) not null,
    wins int4 not null,
    status int4 references matchmaker.player_status (id)  on delete restrict
);

drop table if exists matchmaker.match;
create table matchmaker.match (
    game_id int8 references matchmaker.game (id) on delete cascade,
    player_id int4 references matchmaker.player (id) on delete restrict,
    primary key (game_id, player_id)
);

commit;