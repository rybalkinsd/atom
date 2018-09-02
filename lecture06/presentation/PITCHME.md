#HSLIDE
# Java
lecture 6
## Java + DB 


#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/


#HSLIDE
### get ready #1
```bash
> git fetch upstream
> git checkout -b lecture06 upstream/lecture06

Refresh gradle project
```

Refresh gradle project


#HSLIDE
### get ready #2
PostgreSQL client

linux
```bash
apt-get install postgresql-9.4
```

windows [[Download page]](https://www.postgresql.org/download/windows/)


mac
```bash
> brew install postgres
``` 

#HSLIDE
### get ready #3
```bash
> psql -h 34.229.108.81 -U <your user> -d atomN
> enter your password
>> psql (9.6.2, server 9.2.18)
>> Type "help" for help.
```
```postgresql
select * from pg_catalog.pg_tables;
```

`\q to exit :)`

#HSLIDE
### Agenda
1. Retrospective
1. DB or not DB
1. Database baseline
1. SQL baseline
1. Java + DB


#HSLIDE
<img src="lecture06/presentation/assets/img/retrospective.png" alt="process" style="width: 500px;"/>


#HSLIDE
### Storage comparison
**RAM** vs **File**  
- Capacity
- Speed
- Durability


#HSLIDE
### Storage comparison
**File** vs **Database**
- Store overhead
- Consistency
- Guarantees
- Speed

#HSLIDE
### Database (RDBMS)
Is a
    
    Collection of related data
    
Where

    Data can be easily accessed

Within

    Management system


#HSLIDE
### DB types
- SQL
- NoSQL
- In-memory
- embedded

#HSLIDE
### Transaction
Transaction is a unit of work

- Recovery
- ACID
    - Atomicity
    - Consistency
    - Isolation
    - Durability


#HSLIDE
## All examples below are in PostgreSQL

[[Official doc]](https://www.postgresql.org/docs/9.2/static/index.html)

#HSLIDE
### Table
```postgresql
create table user (
  id    serial             not null,
  login varchar(20) unique not null,
);
```
**There is an error in create query**

[[Read more about `serial`]](https://www.tutorialspoint.com/postgresql/postgresql_using_autoincrement.htm)


#HSLIDE
### Primary key
Indicates that a column or group of columns can be used as a unique identifier for rows in the table

```postgresql
create table chat.user (
  id    serial             not null,
  login varchar(20) unique not null,

  primary key (id)
);
```


#HSLIDE
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


#HSLIDE
### First schema
@See resources/sql/schema/schema-1-simple.sql


#HSLIDE
### CRUD
1. **insert** for create
1. **select** for read
1. **update** for update
1. **delete** for delete 


#HSLIDE
### select
```postgresql
select *
from chat.message
where time > '2017-03-25';
```

[[Read more]](https://www.postgresql.org/docs/9.2/static/sql-select.html)

#HSLIDE
### insert
```postgresql
insert into chat.user (login)
values ('admin');
```

[[Read more]](https://www.postgresql.org/docs/9.2/static/sql-insert.html)

#HSLIDE
### delete
```postgresql
delete from chat.user 
where login = 'admin';
```

[[Read more]](https://www.postgresql.org/docs/9.2/static/sql-delete.html)


#HSLIDE
### Constraints
Imagine a user with lots of messages in history.

What happens when we delete this user?


#HSLIDE
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


#HSLIDE
### Second schema
@See resources/sql/schema/schema-2-constraints.sql


#HSLIDE
### What if one of queries is broken?
```postgresql
create table "user"();

create table bullshit.message(); 

insert into message ("user", time, value) 
values ('admin', now(), 'super message') 
```


#HSLIDE
### Transation
```postgresql
begin;
{statements}
commit;
```


#HSLIDE
### Third schema
@See resources/sql/schema/schema-3-transaction.sql


#HSLIDE
### Java Database Connectivity
<img src="lecture06/presentation/assets/img/jdbc.png" alt="process" style="width: 750px;"/>


#HSLIDE
### Connection
```java
import java.sql.*;

Class.forName("org.postgresql.Driver");
Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
Statement stm = con.createStatement();
ResultSet rs = stm.executeQuery("select * from chat.user");
```

#HSLIDE
### Dao
@See ru.atom.lecture06.server.model
@See ru.atom.lecture06.server.dao

- dao
- model
- dbConnection


#HSLIDE
### Types mapping
<img src="lecture06/presentation/assets/img/dataType.png" alt="process" style="width: 750px;"/>

#HSLIDE
### Practice
0) Check that DbConnector uses right **login**, **password** and **database**

1) **ChatResource.login()** does not prompt message that user is logined
Implement it using **MessageDao**

2) Now we want to see messages only by logined users
**ChatResource.chat()** now requires name to show chat messages
**Implement UserDao.getByName(String name)**


#HSLIDE
### Summary
1. JDBC is simple
1. JDBC leads to tones of boiler plate code
1. Mapper mapper mapper mapper mapper
1. Use transactions for atomic operations


#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
