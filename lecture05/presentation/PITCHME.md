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
### Поиграем в web-server
Any questions on HTTP?
  
**You must understand HTTP!** 


#HSLIDE
### Agenda
1. Threads
1. Annotations
1. Match-maker
1. HTTP Web Server
1. Spring


#HSLIDE
### Threads
1. **[Threads]**
1. Annotations
1. Match-maker
1. HTTP Web Server
1. Spring

#HSLIDE
### Threads intro
As we go into the land of servers, we face multi-threaded environment.  
  
Most of the hard part of multi-threading is covered with frameworks.  
So this gentle introduction only covers basics that are necessary so far.  
  
We will have deeper topics on concurrency further in the course.

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
> jstack <pid> report.info
> less report.info
```

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
### Your turn
@See ru.atom.thread.practice in tests
 
**We have**
1. Event producers
1. Event queue
1. Event processor
  
**You want to**
1. Fix `EventProcessorTest`
1. Remove @Ignore annotation
1. Implement missing methods


#HSLIDE
### Annotations
1. Threads
1. **[Annotations]**
1. HTTP Web Server
1. Spring
1. Match-maker


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
### HTTP Web Server
1. Threads
1. Annotations
1. **[HTTP Web Server]**
1. Spring
1. Match-maker


#HSLIDE
### Web server
**Web server** - is a program that processes HTTP Requests and provide HTTP responses.
  
**Web server can be a separate application, like:**
- Apache HTTP Server
- NGINX
  
**Can be embedded into application:**
- Jetty
- Embedded Tomcat (**our choice**)


#HSLIDE
### Alternative - application servers
Alternatively large projects can use **Application Servers** to manage web application:  
 - Sun GlassFish
 - IBM WebSphere
 - RedHat JBoss  
  
**We will not go this way**

#HSLIDE
### Servlet container
Basic function of web server - to serve static content (html, css, images)  
But most web servers provide some functionality to apply **custom logic on HTTP Request** and return **custom HTTP Response**.
  
This can be used to serve dynamic pages or for custom **web application** (that's how we will use it)
  
Custom server logic in java can be embedded into **servlet container** (part of web-server, that manages **Servlets**)


#HSLIDE
### Servlet
<img src="lecture05/presentation/assets/img/servlet.png" alt="servlet" style="width: 750px;"/>

#HSLIDE
### Servlet
**Servlet** - is class that handles HTTP Requests.  
Java provide low-level **Servlet API**

#HSLIDE
### Web Server approximate behavior
1. Start
1. Initialize internal servlets
1. Create a "mapping" **(request, /path)** -> handling servlet
1. Apply mapping on incoming request
1. Process **single request in single thread** but in parallel*
1. Process routing of outgoing response


#HSLIDE
### Modern way
**Servlet API** (a part of java API) - is low-level API  
People tend to use high-level frameworks to make web applications  
This frameworks use servlet API under the hood  
  
The most famous web framework is **Spring**


#HSLIDE
### Spring
1. Threads
1. Annotations
1. HTTP Web Server
1. **[Spring]**
1. Match-maker


#HSLIDE
### Spring
<img src="lecture05/presentation/assets/img/spring-by-pivotal.png" alt="exception" style="width: 300px;"/>  
is a universal open-source framework, used to develop web applications  
https://spring.io/  
  
First version - **2002**

#HSLIDE
### Spring modules
It includes a number of modules for different functionality:
- Spring MVC for building Web Applications
- Working with Databases
- Messaging
- RPC
- Security
- Testing
  
Today we will build web application with **Spring MVC** module

#HSLIDE
### MVC
**MVC (Model-View-Controller)** - popular pattern used to build web apps
<img src="lecture05/presentation/assets/img/MVC-Introduction2.jpg" style="width: 600px;"/>


#HSLIDE
### Spring MVC
**Spring MVC** - Spring Module that make it easier to build MVC Applications (Like **Django**, **Rails**)
<img src="lecture05/presentation/assets/img/spring_mvc.png" alt="exception" style="width: 600px;"/>


#HSLIDE
### Spring Boot
Spring is a powerful tool and has a lot of configuration options.  
**Spring Boot** is a project, that makes working with Spring easier:
- embedded tomcat included with servlet container
- minimum configuration, sane defaults
- metrics, health checks and externalized configuration
https://projects.spring.io/spring-boot/  
  
First version: **2014**
  
**With Spring Boot our life is much easier :)**


#HSLIDE
### Hello Spring Boot
**@See ru.atom.boot.hw**  
All the magic works via **annotations**

1. Application entry point (HelloSpringBoot)  
*@SpringBootApplication* auto-configures spring application
1. Request controller - handles HTTP connections  
*@Controller* - let Spring recognize this class  
*@RequestMapping("hello")* - this class handles **HTTP Requests** to **/hello** url  
*@RequestMapping("world")* - this method handles **HTTP Requests** to **/hello/world** url  
*@ResponseBody* method returns result will be the **HTTP response body** 


#HSLIDE
### Important notes
These notes are important to understand:
1. HelloController is **Singleton** (by default) - the same instance for all requests
1. Every request runs in **new thread** (actually backed by thread pool)  
   
Here comes **multi-threading** with **shared memory** (concurrency) - topic for further discussion

#HSLIDE
### Match-maker practice
@See ru.atom.thread.mm and tests 

#HSLIDE
### Match-maker
Our Bomberman is a client-server game.

As a client server game we have Clients or **Connections**

Clients want to play. So, we have Games or **GameSessions** 
 

#HSLIDE
### Match-maker
<img src="lecture05/presentation/assets/img/mm.png" alt="mm" style="width: 750px;"/>


#HSLIDE
### Match-making algorithm
<img src="lecture05/presentation/assets/img/mmalgo.png" alt="mmalgo" style="width: 750px;"/>


#HSLIDE
### Match-making algorithm
**Assume we have a queue storing connections**

Match-maker is an infinity-loop algorithm with steps
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
### Practice 2
#### We have
Math-maker service implementation
@see ru.atom.boot.mm  
  
#### Implement:
- ConnectionController::list()
  
#### Un-ignore and fix:
- ConnectionControllerIntegrationTest::list()
- GameControllerTest::list() 
- GameControllerTest::connect()
- GameControllerIntegrationTest::list()


#HSLIDE
### Summary
1. **Threads** are not difficult until concurrency comes
1. **Annotations** help to build meta-information about application and can be used in both compile-time and runtime
1. **Spring** is powerful universal framework
1. **Spring Boot** makes a lot of staff to keep Spring **simple** and work out of the box
1. **MVC** - methodology for building web application (learn it)
1. **Spring MVC** is Spring module that allows to build web application based on MVC pattern
1. Keep learning **HTTP** 


#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
