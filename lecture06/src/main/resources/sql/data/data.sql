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

---Test insert
select *
from chat."user";

INSERT into chat.user(login)
VALUES ('Maxim');

INSERT into chat.user(login)
VALUES ('Admin');

SELECT * FROM chat.user;

INSERT INTO chat.message("user", time, value)
VALUES (2, now(), 'hello');

SELECT * FROM chat.message;

