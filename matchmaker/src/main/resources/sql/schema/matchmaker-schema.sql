begin;

drop schema if exists matchmaker cascade;
create schema matchmaker;

drop table if exists matchmaker.player_status;
create table matchmaker.player_status (
    id int4 primary key,
    name varchar(20) unique not null
);

insert into matchmaker.player_status values (1, 'onine'), (2, 'offline');

drop sequence if exists matchmaker.player_id_seq;

create table matchmaker.player (
    id serial primary key,
    login varchar(30) unique not null,
    password varchar(20) not null,
    wins int4 not null,
    status int4 references matchmaker.player_status (id)  on delete restrict
);

commit;