CREATE SCHEMA mm;

CREATE TABLE mm.users (id serial primary key,
                    login varchar unique not null,
                    rank integer not null default 0);

CREATE TABLE mm.game_sessions (id bigint not null primary key,
                            start_date_time timestamp);

CREATE TABLE mm.game_sessions_to_users (game_session_id bigint not null references mm.game_sessions on delete cascade,
                                     user_id integer references mm.users on delete cascade,
                                     primary key (game_session_id, user_id));