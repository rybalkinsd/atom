insert into mm.users (id, login, rank) values (1, 'User1', 0);
insert into mm.users (id, login, rank) values (2, 'User2', 15);
insert into mm.game_sessions (id, start_date_time)
values (1, parsedatetime('08-04-2018 18:47:52.690', 'dd-MM-yyyy hh:mm:ss.SS'));
insert into mm.game_sessions_to_users (game_session_id, user_id) values (1, 2);