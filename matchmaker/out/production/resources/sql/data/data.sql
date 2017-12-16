--- Test constraints
insert into chat.user (login)
values ('admin');


insert into chat.message ("user", time, value)
values (currval('chat.user_id_seq'), now(), 'sos');

delete from chat.user where login = 'admin';

--- select test
select *
from chat.message
where time > '2017-03-25';

