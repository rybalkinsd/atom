---
# Java
lecture 6
## Java + DB 


---
## Отметьтесь на портале
https://sphere.mail.ru/


---
### get ready #1
[https://github.com/rybalkinsd/atom](https://github.com/rybalkinsd/atom)
```bash
> git fetch upstream
> git checkout -b lecture06 upstream/lecture06
> cd lecture06

Refresh gradle project
```

Refresh gradle project


---
### get ready #2
PostgreSQL client

linux
```bash
apt-get install postgresql-9.5
```

windows [[Download page]](https://www.postgresql.org/download/windows/)


mac
```bash
> brew install postgres
``` 

---
### get ready #3
```bash
> psql -h 54.224.37.210 -U atomN -d chatdb_atomN
> enter your password
>> psql (9.6.2, server 9.2.18)
>> Type "help" for help.
```
```postgresql
select * from pg_catalog.pg_tables;
```

---
### psql commands
```bash
#exit
\q 
#list all schemas
\dn
#list all tables in all schemas
\dt *.
#describe table
\d+ tablename
```


---
### Agenda
1. DB or not DB
1. Database baseline
1. SQL baseline
1. Java + DB

---
### Agenda
1. **[DB or not DB]**
1. Database baseline
1. SQL baseline
1. Java + DB

---
### Storage comparison
**RAM** vs **File**  
- Capacity
- Speed
- Durability


---
### Storage comparison
**File** vs **Database**
- Store overhead
- Consistency
- Guarantees
- Speed

---
### Database (RDBMS)
Is a
    
    Collection of related data
    
Where

    Data can be easily accessed

Within

    Management system


---
### Many different types of DBs
- SQL/[NoSQL](https://en.wikipedia.org/wiki/NoSQL)
- In-memory/disk storage
- stand-alone/embedded

---
### Transaction
Transaction is a unit of work

- Recovery
- ACID
    - Atomicity
    - Consistency
    - Isolation
    - Durability


---
## All examples below are in PostgreSQL

[[Official doc]](https://www.postgresql.org/docs/9.2/static/index.html)

---
### Table
```postgresql
create table user (
  id    serial             not null,
  login varchar(20) unique not null
);
```
**There is an error in create query**

[[Read more about `serial`]](https://www.tutorialspoint.com/postgresql/postgresql_using_autoincrement.htm)


---
### Primary key
Indicates that a column or group of columns can be used as a unique identifier for rows in the table

```postgresql
create table chat.user (
  id    serial             not null,
  login varchar(20) unique not null,

  primary key (id)
);
```


---
### Schema
A schema is essentially a namespace.

```postgresql
create schema chat;
```

```postgresql
create table chat.user (

)
```

[[Read more]](https://www.postgresql.org/docs/9.3/static/sql-createschema.html)


---
### First schema
@See resources/sql/schema/schema-1-simple.sql


---
### CRUD
1. **insert** for create
1. **select** for read
1. **update** for update
1. **delete** for delete 


---
### select
```postgresql
select *
from chat.message
where time > '2017-03-25';
```

[[Read more]](https://www.postgresql.org/docs/9.2/static/sql-select.html)

---
### insert
```postgresql
insert into chat.user (login)
values ('admin');
```

[[Read more]](https://www.postgresql.org/docs/9.2/static/sql-insert.html)

---
### delete
```postgresql
delete from chat.user 
where login = 'admin';
```

[[Read more]](https://www.postgresql.org/docs/9.2/static/sql-delete.html)


---
### Constraints
Imagine a user with lots of messages in history.

What happens when we delete this user?


---
### Constraints
```postgresql
drop table if exists chat.message;
create table chat.message (
  id     serial       not null,
  "user" integer      not null references chat.user on delete cascade,
  time   timestamp    not null,
  value  varchar(140) not null,

  primary key (id)
);
```

What if chat.user has a complex pk?

[[Read more]](https://www.postgresql.org/docs/9.2/static/ddl-constraints.html)


---
### Second schema
@See resources/sql/schema/schema-2-constraints.sql


---
### What if one of queries is broken?
```postgresql
create table "user"();

create table bullshit.message(); 

insert into message ("user", time, value) 
values ('admin', now(), 'super message') 
```


---
### Transaction
```postgresql
begin;
{statements}
commit;
```


---
### Third schema
@See resources/sql/schema/schema-3-transaction.sql


---
### Java Database Connectivity
<img src="lecture06/presentation/assets/img/jdbc.png" alt="process" style="width: 750px;"/>


---
### Connection
```java
import java.sql.*;

Class.forName("org.postgresql.Driver");
Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
Statement stm = con.createStatement();
ResultSet rs = stm.executeQuery("select * from chat.user");
```

---
### Dao
@See ru.atom.lecture06.server.model
@See ru.atom.lecture06.server.dao

- dao
- model
- dbConnection


---
### Types mapping
<img src="lecture06/presentation/assets/img/dataType.png" alt="process" style="width: 750px;"/>

---
### Practice
0) Check that DbConnector uses right **login**, **password** and **database**

1) **ChatResource.login()** does not prompt message that user is logined
Implement it using **MessageDao**

2) Now we want to see messages only by logined users
**ChatResource.chat()** now requires name to show chat messages
**Implement UserDao.getByName(String name)**


---
### Summary
1. JDBC is simple
1. JDBC leads to tones of boiler plate code
1. Mapper mapper mapper mapper mapper
1. Use transactions for atomic operations


---
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
