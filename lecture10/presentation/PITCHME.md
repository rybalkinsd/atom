#HSLIDE
# Java
lecture 10
## Practical Concurrency

#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/

#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b lecture10 upstream/lecture10
```
Refresh gradle project


#HSLIDE
## Agenda
1. Threads and processes
1. Multiple threads in game
1. Parallelism and Concurrency
1. What can go wrong with concurrency?
1. What to do?
1. Practice

#HSLIDE
## Agenda
1. **[Threads and processes]**
1. Multiple threads in game
1. Parallelism and Concurrency
1. What can go wrong with concurrency?
1. What to do?
1. Practice

#HSLIDE
## Parallelism
Why need parallelism?

#HSLIDE
## How parallelism is possible?
- A cluster of machines
- Several processes within single machine
- Several Threads within single process

#HSLIDE
### Process vs Thread
- **Process** has dedicated resources (memory)
- **Threads** share memory space

#HSLIDE
### Process vs Thread
<img src="lecture05/presentation/assets/img/process.png" alt="process" style="width: 450px;"/>

#HSLIDE
## Threads and processes. OS Role
Operating System
- provides API (**system calls**) for creating and running threads and processes. Normally a **program** is represented by single **process**.
- schedules threads and processes (**context switch**)
- provides API for managing threads

Famous Threads and Processes API from UNIX world:
- [**fork**](https://linux.die.net/man/2/fork) (creates process)  
- [**clone**](http://man7.org/linux/man-pages/man2/clone.2.html) (creates thread)  
  
#HSLIDE
## Java Threads
The programming language provide it's own API for managing threads and processes, that use system calls under the hood

In **Java** we get **Thread** class and **Runnable** interface

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
<img src="lecture10/presentation/assets/img/newthread.png" alt="exception" style="width: 750px;"/>

#HSLIDE
## Multithreaded programs are racy
Behaviour of multithreaded program is (inter alia) dependent on **OS scheduling**
Multithreaded programs are **racy** by nature.

#HSLIDE
## Race condition
Race condition (состояние гони, гонка)
program behaviour where the output is dependent on the
sequence or timing of other uncontrollable events
Parallel programs are racy by nature, some races are erroneous.
> @see ru.atom.lecture10.races

#HSLIDE
## jstack
Util to observe java process stack state.
 
```bash
# show all java processes
> jcmd
# get report
> jstack <pid> > report.info
> less report.info
```

#HSLIDE
## Agenda
1. Threads and processes
1. **[Multiple threads in game]**
1. Parallelism and Concurrency
1. What can go wrong with concurrency?
1. What to do?
1. Practice

#HSLIDE
## Multuple threads in Bomberman
Our Bomberman is a **client-server** game.  
Every player establishes **Session** with Server over **WebSocket**.  
Every connection is processed in **dedicated thread** (Actually thread from some **ThreadPool**).  
That is players are in different threads.

#HSLIDE
## How different threads communicate?
As usual - they can communicate via public variables, via mutable objects.

#HSLIDE
## Agenda
1. Threads and processes
1. Multiple threads in game
1. **[Parallelism and Concurrency]**
1. What can go wrong with concurrency?
1. What to do?
1. Practice

#HSLIDE
## What does it mean that threads share memory?
They read and write to shared mutable variable (**shared mutable state**)
> @see ru.atom.lecture10.sharedmutablestate

#HSLIDE
## Concurrency vs parallelism
**Concurrency** - contention on shared resources (memory, shared state)  
**Parallelism** - is possible without concurrency  

#HSLIDE
## Examples?
Any examples of task that can be executed in parallel without concurrency?

#HSLIDE
## Bomberman server is concurrent
Different threads change and read game state  
What is game state in Bomberman?  
Is it **shared mutable state**?

#HSLIDE
## Concurrency in different languages
Many languages are have no default concurrency support (does not have **Memory Model**)
- c (concurrency provided by **p_threads** library)
- c++ (before C++11)
Some just avoid concurrency
- Python (GIL)
- Ruby (GIL, Introduced Guild Locks)

#HSLIDE
## Concurrency in Java
In Java concurrency is supported by language natively. It has Memory Model that is considered successful. C++ Memory Model roots from JMM.
//TODO reference to JMM

#HSLIDE

#HSLIDE
## Agenda
1. Threads and processes
1. Multiple threads in game
1. Parallelism and Concurrency
1. **[What can go wrong with concurrency?]**
1. What to do?
1. Practice

#HSLIDE
## Data race
When several processes communicate via **shared mutable state** and at least one is writing **without proper synchronization**  
Is **Bomberman** prone to data races?

#HSLIDE
### Queue
Queue is a shared resource in a multi-threaded environment.

We will use **BlockingQueue** implementation.

```java
interface BlockingQueue<E> implements java.util.Queue<E> {
    /** 
     * Inserts the specified element into this queue ...
     */
    void put(E e);
    
    /**
    * Retrieves and removes the head of this queue, waiting up to the
    * specified wait time if necessary for an element to become available.
    */
    E poll(long timeout, TimeUnit unit);   
}
```

#HSLIDE
### Queue
<img src="lecture10/presentation/assets/img/queue.png" alt="queue" style="width: 750px;"/>

#HSLIDE
### Back to the root

```java
class java.lang.Object {
    public final native void wait(long timeout) throws InterruptedException;
    public final native void notify();
    public final native void notifyAll();
}
```

#HSLIDE
### How-to
1. notify vs notifyAll
2. how to wait - while approach




#HSLIDE
### Monitors
<img src="lecture10/presentation/assets/img/monitor.png" alt="monitor" style="width: 400px;"/>


#HSLIDE
## Summary
- **InputStream** and **OutputStream** - basic **bytes** io
- **Reader** and **Writer** - basic **string** io
- **Serialization** is standard mechanism to store java object (for example to file)
- With **Reflection** we can use the information about program structure in runtime
- One must use **reflection** wisely
- **Collections** and **Exceptions** - are most popular topics on interviews

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
