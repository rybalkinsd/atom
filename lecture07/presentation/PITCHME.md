#HSLIDE
# Java
lecture 7
## Hibernate

#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/

#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b lecture07 upstream/lecture07
```
Refresh gradle project

#HSLIDE
## Agenda
1. Intro
1. What is ORM?
1. Hibernate example
1. Hibernate theory
1. Hibernate session
1. Hibernate practice

#HSLIDE
## Agenda
1. **[Intro]**
1. What is ORM?
1. Hibernate example
1. Hibernate theory
1. Hibernate session
1. Hibernate practice

#HSLIDE
### JDBC
JDBC - is low level API for communicating with databases  

| +++                               | ---                          |
|-----------------------------------|------------------------------|
| Pure SQL                          | DBMS-specific                |
| Good and controllable performance | Verbose and 'string-driven'  |
| Good for simple schema            | Complex for complex schema   |

#HSLIDE
### Data in Java and in SQL
**RDBMS** - row in table  
**Java** - object

#HSLIDE
### How to convert object into rows in table?
<img src="lecture07/presentation/assets/img/mapping.png" alt="process" style="width: 700px;"/>

#HSLIDE
### Mapping
<img src="lecture07/presentation/assets/img/mapping_bad.png" alt="process" style="width: 700px;"/>

#HSLIDE
### Mapping problems
- inheritance
- identity
- access level

#HSLIDE
### We need a hero
- To convert data
- To hide sql details
- To reduce boilerplate code
- To get rid of complex request strings
Based on JDBC ‘under the hood’

#HSLIDE
### Hibernate
<img src="lecture07/presentation/assets/img/hero.jpg" alt="process" style="width: 500px;"/>

#HSLIDE
## Agenda
1. Intro
1. Hibernate example
1. **[What is ORM?]**
1. Hibernate theory
1. Hibernate session
1. Hibernate practice

#HSLIDE
### ORM
**ORM** - Object/Relational Mapping  
  
The concept of mapping **Objects** to **Relations** in db  
Ideally we have usual objects that are automatically synchronized with database

#HSLIDE
### JPA
**JPA** - Java Persistence API (API for ORM in Java)
- CRUD API
- object-based query language
- customizable mapping
JPA is just **a bunch of interfaces** which are implemented by third-party **JPA-providers**


#HSLIDE
### ORM Frameworks
There are many ORM frameworks across different languages
- Enterprise JavaBeans Entity Beans
- Java Data Objects
- Castor
- TopLink
- Spring DAO
- MyBatis
- Hibernate
...

#HSLIDE
### Why hibernate?
Hibernate is very popular. It is required to apply 70% of Java positions

#HSLIDE
## Agenda
1. Intro
1. What is ORM?
1. **[Hibernate example]**
1. Hibernate theory
1. Hibernate session
1. Hibernate practice


#HSLIDE
### Magic
Spring boot + Hibernate uses a lot of instrumentation (magic)  
**Wanna see some magic?**

#HSLIDE
### Good old chat
We now rewrite chat persistence from **JDBC** to **Hibernate**  
@see ru/atom/lecture07/server

#HSLIDE
### Service layer
We introduce **service layer** (ChatService.java) in order to encapsulate business logic.  
Service layer implements business logic using DAO and providing guaranties, that resource level expect.  
This is simple and popular web services architecture  
  
#### Overall scheme now looks like this:  
**Resource <--> Service <--> DAO <--> db**


#HSLIDE
## Agenda
1. Intro
1. What is ORM?
1. Hibernate example
1. **[Hibernate theory]**
1. Hibernate session
1. Hibernate practice

#HSLIDE
### Hibernate
[[Home page]](http://hibernate.org/orm/)  
[[github]](https://github.com/hibernate/hibernate-orm)  
[[maven/gradle repository]](https://mvnrepository.com/artifact/org.hibernate/hibernate-core/5.2.9.Final)  
[[Getting started]](http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html)

**Hibernate ORM** - is a component/library providing ORM support to applications and other components/libraries. It also provides an implementation of the **JPA** specification

#HSLIDE
### Hibernate
<img src="lecture07/presentation/assets/img/hibernate.png" alt="process" style="width: 500px;"/>

#HSLIDE
### Hibernate architecture
<img src="lecture07/presentation/assets/img/hibernate-arch.png" alt="process" style="width: 600px;"/>

#HSLIDE
### Hibernate automatic entity management
Once entity becomes managed by **Hibernate**, it is automatically synchronized with database.  
How to make object managed by hibernate? - via **Session**

#HSLIDE
## Agenda
1. Intro
1. What is ORM?
1. Hibernate example
1. Hibernate theory
1. **[Hibernate session]**
1. Hibernate practice

#HSLIDE
### Hibernate session
**Session** is a class, provided by Hibernate API. It extends **EntityManager** (analog from JPA)  
https://developer.jboss.org/wiki/SessionsAndTransactions  
**Session** provide interface for communication with Hibernate:
1. **persist** (make manageable), update, delete ... (next slide)
1. **make custom queries** (many ways to do it)

#HSLIDE
### Entity states
<img src="lecture07/presentation/assets/img/hibernate-states.png" alt="process" style="width: 700px;"/>

#HSLIDE
### Session is a scope
**Session** is a scope where hibernate provides guaranties:
1. automatic management
1. **equals** and **hashcode** for same object
1. **transaction** is valid within one session  
Objects from different sessions must be handled manually (**see Session.merge()**)  

#HSLIDE
### Session is not thread safe
So we must not use hibernate session from different threads. It is preferable to create separate session for every thread  
(will see how to do it)

#HSLIDE
### equals and hashcode
How should we implement **equals()** and **hashcode()** for entities?  
And should we?  
We **must if** we:
1. use **Set/Map** to store entity (as usual)
1. use entities **across** sessions
How to implement it? - **business key** (whatever we think define entity by business logic)

#HSLIDE
### Session lifespan is configurable
https://developer.jboss.org/wiki/SessionsAndTransactions  
There are a number of popular strategies:
- session-per-transaction
- session-per-request
This strategies are implemented by application or by framework  
We will use **session-per-thread** strategy (configurable by hibernate)

#HSLIDE
## Hibernate theory summary
- Hibernate provide **Session** object to manage Entities and to make queries. 
- Entity mapping is described by **annotations**
- **Session** object is configurable in **application.properties** (or hibernate.cfg.xml or with annotations)  
- All hibernate guaranties are valid within single session
Let's look how it works...

#HSLIDE
### Back to example

#HSLIDE
### Plug in hibernate
We plug in hibernate as library within **build.gradle** (as usual)

#HSLIDE
### Hibernate configuration
Persistence configuration contain two main parts:
1. Mapping definition (how **Classes** will be mapped to **Relations**)
1. Hibernate config (you can do it in **application.properties**)

#HSLIDE
### application.properties
application.properties must be placed in **CLASS_PATH** of project (for example in **resources** directory).
You can configure hibernate there 
  
Hibernate options, provided in **resources/application.properties** are essential to understand  
@see resources/application.properties

#HSLIDE
### Mapping definition (via annotations)
**JPA** suggests mapping via xml files, which is very verbose  
In **hibernate** we can describe mapping with Java annotations, which is pretty  
  
Let's look at basic mapping **annotations**:  
@see ru/atom/lecture07/server/model/User.java  
@see ru/atom/lecture07/server/model/Message.java  

#HSLIDE
### Inheritance
Hibernate allow entity classes to inherit plain java classes in any combination. There are different mapping strategies for inheritance  
  
For details see [docs about inheritance](http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#entity-inheritance)

#HSLIDE
### Hibernate query
There are multiple ways to execute queries in hibernate
1. Criteria API
1. HQL
1. Raw SQL
1. JPA API

#HSLIDE
### Hibernate criteria API
Hibernate has API for constructing queries in object-oriented manner  
  
Alas, elegant Criteria API is deprecated in hibernate 5.2+ in favour of verbose JPA Criteria API  
@see UserDao.getAll()

#HSLIDE
### Transactions
What is transaction?  
What is ACID?

#HSLIDE
### Hibernate transactions
Hibernate support **transactions**  
One can use any transaction implementation (JTA provider)  
or use **Transaction **interface available from **Session**  
We will use @Transactional from JTA
@see ru/atom/lecture07/server/service/ChatService.java

#HSLIDE
### References
[https://habrahabr.ru/post/265061/](https://habrahabr.ru/post/265061/)  
[http://hibernate.org/orm/](http://hibernate.org/orm/)  

#HSLIDE
## Agenda
1. Intro
1. What is ORM?
1. Hibernate example
1. Hibernate theory
1. Hibernate session
1. **[Hibernate practice]**

#HSLIDE
### Practice task
Implement chat server with persistence via **hibernate**

**Implement:**  
/chat/say
/chat/logout  
  
**@see ru/atom/lecture07/**
  

#HSLIDE
### Practice hints
1. update schema
```bash
> psql -h http://34.229.108.81/ -U atom0 -a -d atom0 -f lecture07/src/main/resources/sql/schema/chat-schema.sql
```
1. Change user and password in **application.properties**
1. Implement methods in **ChatService**, add new if necessary
1. Implement methods in **MessageDao** and **UserDao**, add new if necessary

#HSLIDE
### Hibernate

| +++                               | ---                          |
|-----------------------------------|------------------------------|
| Automatic management of entities  | Not obvious what SQL will be executed and when |           |
| Object-based query language       | Hardly controllable performance overhead
| Allow usage of pure SQL if needed | Not an easy tool             |
| Independent of underlying database|                              |

#HSLIDE
### Summary
1. **ORM** - is concept of mapping **Objects** to **Relations** in DB
1. **JPA** - API for **ORM** in Java
1. Hibernate - implementation of **JPA**
1. To make object manageable by hibernate (**entity**) you must annotate class (or describe mapping any other way, like xml)
1. To manage entity, you must use **Session** object
1. Hibernate configuration matters - understand what every line mean in **applicaiton.properties**
1. **Session is not thread safe** - one must use hibernate session from single thread
1. Session lifespan is configurable (by hibernate)
1. Hibernate support **transactions**
1. Transactions lifespan is configurable (by application)

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
