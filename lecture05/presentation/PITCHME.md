---
# Java
2018/lecture 5
## Spring, Threads, Annotations



---
## Отметьтесь на портале
https://sphere.mail.ru/


---
### get ready
[https://github.com/rybalkinsd/atom](https://github.com/rybalkinsd/atom)
```bash
> git fetch upstream
> git checkout -b lecture05 upstream/lecture05
> cd lecture05
```

Refresh gradle project


---
### Agenda
1. Threads
1. Annotations
1. Spring, Spring Boot
1. Inversion of Control, Dependency Injection
1. Beans, ApplicationContext
1. Match-maker

---
### Agenda
1. **[Threads]**
1. Annotations
1. Spring, Spring Boot
1. Inversion of Control, Dependency Injection
1. Beans, ApplicationContext
1. Match-maker

---
### Threads intro
As we go into the land of servers, we face multi-threaded environment.  
  
Most of the hard part of multi-threading is covered with frameworks.  
So this gentle introduction only covers basics that are necessary so far.  
  
We will have deeper topics on concurrency further in the course.

---
### Why do we need parallel execution?


---
### Concurrency vs parallelism
**Concurrency** - contention on shared resources

**Parallelism** is possible without concurrency


---
### Process vs Thread
**Process** has dedicated resources (memory)

**Threads** share memory space


---
### Process vs Thread
<img src="lecture05/presentation/assets/img/process.png" alt="process" style="width: 450px;"/>


---
### Operating System role
1. Creates threads (clone syscall)
1. Schedules threads (context switch)
1. Provides api for Thread management

Behaviour of multithreaded program is (inter alia) dependent on OS scheduling


---
### interface Runnable
```java
@FunctionalInterface
interface Runnable {
    public abstract void run();
}
```


---
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


---
### Start and Run
```java
new Thread().start();

new Thread( runnable ).start();
```


---
### Start and Run
<img src="lecture05/presentation/assets/img/newthread.png" alt="exception" style="width: 750px;"/>

---
### Thread instantiation
@See ru.atom.thread.instantiation and tests

- instantiation
- Thread::start
- Thread::isInterrupted
- Thread::interrupt
- Thread::sleep

---

### Thread interruption
An interrupt is an indication to a thread that it should stop what it is doing and do something else. It's up to the programmer to decide exactly how a thread responds to an interrupt, but it is very common for the thread to terminate.  
A thread sends an interrupt by invoking interrupt on the Thread object for the thread to be interrupted. For the interrupt mechanism to work correctly, the interrupted thread must support its own interruption.  
https://docs.oracle.com/javase/tutorial/essential/concurrency/interrupt.html

---

### Waiting for thread termination
@See ru.atom.thread.join and tests

- Thread::join
- Thread::interrupt


---
### jstack
Util to observe java process stack state.
 
```bash
# show all java processes
> jcmd
# get report
> jstack <pid> report.info
> less report.info
```

---
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


---
### Queue
<img src="lecture05/presentation/assets/img/queue.png" alt="queue" style="width: 750px;"/>


---
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


---
### Agenda
1. Threads
1. **[Annotations]**
1. Spring, Spring Boot
1. Inversion of Control, Dependency Injection
1. Beans, ApplicationContext
1. Match-maker


---
### Annotations
Which annotations did you see before?


---
### Override
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {
}
```

---
### Reflection API
Reflection is an API to find information about classes/fields/methods 
in application runtime.

@See ru.atom.annotation and tests

---
### Retention policy
Annotation has **Retention policy**, which indicated, whether info about the annotation will be available at runtime  
**RetentionPolicy.RUNTIME** guarantees that annotation will be available in **runtime**


---
### Agenda
1. Threads
1. Annotations
1. **[Spring, Spring Boot]**
1. Inversion of Control, Dependency Injection
1. Beans, ApplicationContext
1. Match-maker

---

### Matchmaker example

We will use MathMaker application to study basic concepts of Spring  
  
> @see MatchMakerApp

---
### Spring
<img src="lecture05/presentation/assets/img/spring-by-pivotal.png" alt="exception" style="width: 300px;"/>  
is a universal open-source framework, used to develop web applications  
https://spring.io/  
  
First version - **2002**

---
### Spring modules
It includes a number of modules for different functionality:
- Spring MVC for building Web Applications
- Working with Databases
- Messaging
- RPC
- Security
- Testing
  
Today we will build web application with **Spring MVC** module

---
### Spring Boot
Spring is a powerful tool and has a lot of configuration options.  
**Spring Boot** is a project, that makes working with Spring easier:
- embedded tomcat included with servlet container
- minimum configuration, sane defaults
- metrics, health checks and externalized configuration
https://projects.spring.io/spring-boot/  
  
First version: **2014**
  
**With Spring Boot our life is much easier :)**


---
### Spring boot distribution
```groovy
    // dependencies, necessary for building generic web applicaitons
    compile group: 'org.springframework.boot', name: 'spring-boot-starter-web', version: '2.0.0.RELEASE'
    // actuator
    compile group: 'org.springframework.boot', name: 'spring-boot-starter-actuator', version: '2.0.0.RELEASE'
```

---

### Spring boot actuator
Spring boot actuator - usefool dependency, providing web interface to meta data of application and even interact with it  
  
**Actuator endpoints:**
https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html  
By default most endpoints are disabled. To enable them we need to enable them in **application.properties**

---

### application.properties
The standard way to configure java application - **application.properties** should appear in classpath  
To enable actuator endpoints:
```properties
management.endpoints.web.exposure.include=*
```
We also can configure actuator and server ports there:
```properties
#server port:
server.port = 8080
#actuator port:
management.server.port = 7001
```

---
### Useful actuator endpoints
**/actuator/health**  
overall application status  
  
**/actuator/mappings**  
available mappings  
  
**/actuator/beans**  
all beans in context
---

### Agenda
1. Threads
1. Annotations
1. Spring, Spring Boot
1. **[Inversion of Control, Dependency Injection]**
1. Beans, ApplicationContext
1. Match-maker

---

### Inversion of Control
**Principle:** control flow is transferred to external framework  
**Why:** loose coupling, easier to develop, easier to test

---

### Dependency Injection
Objects lifecycle is managed by external framework (**IoC container**)
- instantiation
- wiring
- removal

---

### Spring provides IoC container
https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans  
Interface of **IoC Container** in Spring:  
**org.springframework.context.ApplicationContext**  
- methods for accessing application components. **ListableBeanFactory**
- methods to load file resources in a generic fashion. **ResourceLoader**
- methods to publish events to registered listeners. **ApplicationEventPublisher**
- methods to resolve messages, supporting internationalization. **MessageSource**

---

### Agenda
1. Threads
1. Annotations
1. Spring, Spring Boot
1. Inversion of Control, Dependency Injection
1. **[Beans, ApplicationContext]**
1. Match-maker

---

### Beans
https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-definition  
Beans are java objects, that are managed by **IoC Container**  
  
How to make **bean** out of **POJO** (Plain Old Java Object)?  
With bean definition configuration

---
### Spring configuration
There are several options for beans configuration:
- XML Description
- Groovy Description
- Annotations
  
We will use annotations as this is the cleanest one

---

### Beans Detection
For spring to create and manage beans, we must provide bean definitions  
**How to create bean definition with annotations:**
- mark class with **@Configuration**/**@Component**/**@Controller**/**@Service**/**@Repository** or annotations, inheriting their semantics
- mark any method inside such class with **@Bean** (config method)

---

### Beans autowiring
https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-autowired-annotation  
Once we have beans definitions, we can inject those beans with **@Autowired**  
Possible targets:
- constructor
- field
- setter method
- config method

---

## ByType and ByName autowiring
```java
@Service
public class MatchMaker implements Runnable {
    @Autowired //How do spring know which bean to inject?
    private ConnectionQueue connectionQueue;
}
```
- ByType: it will search the bean with type **ConnectionQueue** or implementation in ApplicationContext
- ByName: with **@Qualifier** annotation we can autowire bean by name  
https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-autowired-annotation-qualifiers
---

### Bean scopes
https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-factory-scopes
Beans can have different life span depending on requirements.  
  
**[Common scopes:]**  
- Singleton (default)
- Prototype: single bean definition to any number of object instances
- request: single bean definition to the lifecycle of a single HTTP reques
- websocket: single bean definition to the lifecycle of a WebSocket
...

---

### Spring: see documentation
Both basic concepts and details are fully covered in spring documentation.
https://docs.spring.io/spring/docs/current/spring-framework-reference/index.html

---

### Agenda
1. Threads
1. Annotations
1. Spring, Spring Boot
1. Inversion of Control, Dependency Injection
1. Beans, ApplicationContext
1. **[Match-maker]**

---
### Match-maker practice
@See ru.atom.mm and tests 

---
### Match-maker
Our Bomberman is a client-server game.

As a client server game we have Clients or **Connections**

Clients want to play. So, we have Games or **GameSessions** 
 

---
### Match-maker
<img src="lecture05/presentation/assets/img/mm.png" alt="mm" style="width: 750px;"/>


---
### Match-making algorithm
<img src="lecture05/presentation/assets/img/mmalgo.png" alt="mmalgo" style="width: 750px;"/>


---
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


---
### Connection producer
We do not have server to get connections for now. 
We need an instance to emulate client.  
  
**Connection producer** will put new requests to our **queue** time-to-time.
It is possible to have many producers.


---
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


---
### Summary
1. **Threads** are not difficult until concurrency comes
1. **Annotations** help to build meta-information about application and can be used in both compile-time and runtime
1. **Spring** is powerful universal framework
1. **Spring Boot** makes a lot of staff to keep Spring **simple** and work out of the box
1. **MVC** - methodology for building web application (learn it)
1. **Spring MVC** is Spring module that allows to build web application based on MVC pattern
1. Keep learning **HTTP** 


---
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
