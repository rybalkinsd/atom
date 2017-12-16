--- Data
insert into chat.user (login)
values ('user1');

insert into chat.user (login)
values ('user2');

insert into chat.message ("user", time, value)
values (1, now(), "Hello");

--- Test constraints
insert into chat.user (login)
values ('admin');


insert into chat.message ("user", time, value)
values (currval('chat.user_id_seq'), now(), 'my super user message');

delete from chat.user where login = 'admin';

--- select test
select *
from chat.message
where time > '2017-03-25';

