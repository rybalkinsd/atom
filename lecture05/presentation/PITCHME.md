#HSLIDE
# Java
lecture 5
## Web server


#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/


#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b lecture05 upstream/lecture05
```

Refresh gradle project


#HSLIDE
### Agenda
1. Threads
1. Servlets
1. HTTP Web Server
1. Annotations


#HSLIDE
### Why do we need parallel execution?


#HSLIDE
### Concurrency vs parallelism
**Concurrency** - contention on shared resources

**Parallelism** is possible without concurrency


#HSLIDE
### Process vs Thread
**Process** has dedicated resources (memory)

**Threads** share memory space


#HSLIDE
### Process vs Thread
<img src="lecture05/presentation/assets/img/process.png" alt="process" style="width: 450px;"/>


#HSLIDE
### Operating System role
1. Creates threads (clone syscall)
1. Schedules threads (context switch)
1. Provides api for Thread management

Behaviour of multithreaded program is (inter alia) dependent on OS scheduling

Consequences?


#HSLIDE
### interface Runnable
```java
@FunctionalInterface
interface Runnable {
    public abstract void run();
}
```


#HSLIDE
### class Thread
```java
class Thread implements Runnable {  
    void start() {}
    void interrupt() {} 
    void join() {}
    
    static sleep(long time) {}
    // ...
}
```


#HSLIDE
### Start and Run
```java
new Thread().start();

new Thread( runnable ).start();
```


#HSLIDE
### Start and Run
<img src="lecture05/presentation/assets/img/newthread.png" alt="exception" style="width: 750px;"/>

#HSLIDE
### Thread instantiation
@See ru.atom.thread.instantiation and tests

- instantiation
- Thread::start
- Thread::isInterrupted
- Thread::interrupt
- Thread::sleep


#HSLIDE
### Waiting for thread termination
@See ru.atom.thread.join and tests

- Thread::join
- Thread::interrupt


#HSLIDE
### jstack
Util to observe java process stack state.
 
```bash
# show all java processes
> jcmd
# get report
> jstack <pid> > report.info
> less report.info
```


#HSLIDE
### Practice #1
Our Bomberman is a client server game.

As a client server game we have Clients or **Connections**

Clients want to play. So, we have Games or **GameSessions** 
 

#HSLIDE
### Matchmaker
<img src="lecture05/presentation/assets/img/mm.png" alt="mm" style="width: 750px;"/>


#HSLIDE
### Matchmaking algorithm
<img src="lecture05/presentation/assets/img/mmalgo.png" alt="mmalgo" style="width: 750px;"/>


#HSLIDE
### Matchmaking algorithm
**Assume we have a queue storing connections**

Matchmaker is an infinity-loop algorithm with steps
1. **Poll connection** from queue
1. **Collect** polled connection to game GameSession candidates
1. **Check** if candidates count equals to PLAYERS_IN_GAME constant 
    - If **no** continue to step #1
    - If **yes**
        - Create and save GameSession
        - Clean GameSession candidates
        - Continue to step #1


#HSLIDE
### Connection producer
We do not have server to get connections for now. 

We need an instance to emulate client.  

**Connection producer** will put new requests to our **queue** time-to-time.

It is possible to have many producers.


#HSLIDE
### Queue
Queue is a shared resource in a multi-threaded environment.

We will use **BlockingQueue** implementation.

```java
interface BlockingQueue<E> implements java.util.Queue<E> {
    /** 
     * Inserts the specified element into this queue ...
     */
    boolean offer(E e);
    
    /**
    * Retrieves and removes the head of this queue, waiting up to the
    * specified wait time if necessary for an element to become available.
    */
    E poll(long timeout, TimeUnit unit);   
}
```


#HSLIDE
### Queue
<img src="lecture05/presentation/assets/img/queue.png" alt="queue" style="width: 750px;"/>


#HSLIDE
### And now
@See ru.atom.thread.mm and tests 


#HSLIDE
### Your turn
@See ru.atom.thread.practice in tests
 
We have
1. Event producers
1. ThreadSafe Event queue
1. Event processor

You want to
1. Fix `EventProcessorTest`
1. Remove @Ignore annotation
1. Implement missing methods


#HSLIDE
### Web server
Web server - is a system that processes request via HTTP.

Examples:
- Apache HTTP Server
- NGINX

Can be embedded into application
- Jetty **our choice**
- Tomcat

Plain web server is ok for static content. 


#HSLIDE
### Application server
Two types of solutions:
1. Old smelly JEE
    - Sun GlassFish
    - IBM WebSphere
    - RedHat JBoss
1. The other way


#HSLIDE
### Servlet
<img src="lecture05/presentation/assets/img/servlet.png" alt="servlet" style="width: 750px;"/>


#HSLIDE
### Jetty
Jetty provides a Web server and javax.servlet container

Supports
- HTTP/2
- WebSocket
- ...


#HSLIDE
### Server approximate behavior
1. Start
1. Initialize internal servlets
1. Create a "mapping" **(request, /path)** -> handling servlet
1. Apply mapping on incoming request
1. Process **single request in single thread** but in parallel*
1. Process routing of outgoing response


#HSLIDE
### HelloWorld servlet
@See ru.atom.servlet.hw

- Servlet class
- doGet / doPost
- jetty server init


#HSLIDE
### Practice #2
No more Connection Producers.

Now we can start a **jetty server**.


#HSLIDE
### API
Serving two types of request:
- Connect new player with **id** and **name**
```bash
GET /connect?id=1&amp;name=bomberman HTTP/1.1
Host: localhost:8080
```

- View all games list 
```bash
GET /games HTTP/1.1
Host: localhost:8080
```
    
@See ru.atom.servlet.mm


#HSLIDE
### +/- of plain Servlets
1. Is it convenient?
1. Could I write less code?
1. Is it as easy as you can imagine?


#HSLIDE
### Annotations
What annotations did you see before?


#HSLIDE
### Override
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {
}
```


#HSLIDE
### Reflection API
Reflection is an API to find information about classes/fields/methods 
in application runtime.

@See ru.atom.annotation and tests


#HSLIDE
### Jersey
[Jersey](https://jersey.java.net/) is
1. RESTful Web services framework
1. Serlvet-free from our point of view
1. Lightweight (low overhead) compare to servlets
1. Minimalistic syntax


#HSLIDE
### Jersey Hello World
@See ru.atom.jersey.hw

- @Path, @GET, @POST annotations
- jersey initialization


#HSLIDE
### Make MatchMaker great again
Goals
1. Migrate to jersey
1. Migrate connect method from GET to POST


#HSLIDE
### API
Serving two types of request

- Connect 

```bash
POST /connect HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded

id=1&name=bomberman
```

-View all games list
 
```bash
GET /games HTTP/1.1
Host: localhost:8080
```
    
@See ru.atom.jersey.mm
    

#HSLIDE
### Interceptors and filters
Sometimes you want to add some aspect to your method.

Authorization:

```bash
POST /connect HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded
Authorization: <auth token>

id=1&name=bomberman
```


#HSLIDE
### Authorized aspect
@See ru.atom.jersey.aspect

- Filter definition
- Adding filter in jetty context
- Applying filter to methods


#HSLIDE
### Summary
1. Threads are not difficult until concurrency comes
1. Jersey is lightweight and good with jetty
1. Annotations info can disappear in compile-time 
1. Keep learning **HTTP** 


#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
