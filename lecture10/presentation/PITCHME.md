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
1. Synchronization. Critical section
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
<img src="lecture10/presentation/assets/img/process.png" alt="process" style="width: 450px;"/>

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
1. Synchronization. Critical section
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
1. Synchronization. Critical section
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
## Agenda
1. Threads and processes
1. Multiple threads in game
1. Parallelism and Concurrency
1. **[What can go wrong with concurrency?]**
1. Synchronization. Critical section
1. Practice

#HSLIDE
## What if we just write concurrent program as single-threaded?
Many things will go wrong. It depends on **Memory Model** of language.  
First let's look at how MM is implemented in other languages and in Java.


#HSLIDE
## Concurrency in different languages
Many languages have no default concurrency support (does not have **Memory Model**)
- c (concurrency provided by **pthreads** library)
- c++ (before C++11)
Some just avoid concurrency
- Python (GIL)
- Ruby (GIL, Introduced Guild Locks)

#HSLIDE
## What about Memory Model in Java
Java Provide low-level powerful **Memory Model**, that allows threads freely communicate via shared mutable state.  
With JMM one can create highly-concurrent high-performance system.
The power is gained at cost of complexity.  
**JMM** sacrifices some basic guaranties: **guaranties** that are obvious in single-threaded environment **does not work** in multithreaded environment.

#HSLIDE
## Java Memory Model (JMM)
Formally this is defined by **Java Language Specification**  
Specifically by **Java Memory Model** (**JMM**)
Chapter 17. Threads and locks  
https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html  
(do not read! first look at https://shipilev.net/#jmm )  
JMM is tricky to understand and is hard to use directly.

#HSLIDE
## What JMM states?
JMM specifies what can be **read** by particular read action in program.  
More precisely it defines **guarantees** on read/write atomicity, write visibility and instruction ordering.

#HSLIDE
## JMM. Why so complex?
JVM is highly optimized. This is possible because of relatively weak guarantees of JMM.  
JMM was created as a trade-off between performance, complexity of JVM and abilities of hardware.  
JMM considered to be one of the most successful memory models.  
Recently Introduced C++ Memory Model is highly based on JMM.  

#HSLIDE
## Data race
**Data race** - when several processes communicate via **shared mutable state** and at least one is writing **without proper synchronization**   
> @see ru.atom.lecture10.dataraces

Is **Bomberman** prone to data races?  
  
There are 3 reasons for data races according to JMM. The following guaranties are **weaker** in multithreaded environment than in single-threaded:
- Atomicity
- Visibility
- Ordering

#HSLIDE
## Atomicity
Some operations that are expected to be atomic - are not:
- i++;
- double/long reads and writes on 32 bit systems
- check then act actions:
```java
if (!map.containsKey(key)) {
   map.put(key, value);
}
```
> @see ru.atom.lecture10.dataraces

#HSLIDE
## Visibility
Modern processors have multi-level caches. Thus threads running on different processors may not see changes made by other threads.  
It actually depends on cache [**coherence protocol**](https://en.wikipedia.org/wiki/Cache_coherence).  
> @see ru.atom.lecture10.volatileexample

#HSLIDE
## Visibility
<img src="lecture10/presentation/assets/img/visibility.png" alt="queue" style="width: 600px;"/>

#HSLIDE
## Ordering
In sake of performance **javac**, **jit** and **JVM** may change your code whenever it is accepted by **Java Memory Model**, that is it can reorder instructions.
After all, **processor** reorders instructions by himself.  
JMM restrict some reorderings.
> @see ru.atom.lecture10.ordering 

#HSLIDE
## Any other problems?
Many other problems, among them:
- deadlocks
- livelocks
- performance  
Reasoning about performance of concurrent programs is tricky
> @see https://shipilev.net/

#HSLIDE
## What to do?
- Declare critical sections via **synchronized** keyword
- Use java.util.concurrent (next lecture)
- use low-level concurrency (next lecture)

#HSLIDE
## Agenda
1. Threads and processes
1. Multiple threads in game
1. Parallelism and Concurrency
1. What can go wrong with concurrency?
1. **[1. Synchronization. Critical section]**
1. Practice

#HSLIDE
## Critical section
In Java we can declare code block as **synchronized** on some **object**.  
The object will protect the code block and allow only single thread to enter the **code block** simultaneously  
This is possible because in Java **every object** has **internal monitor**

#HSLIDE
## synchronized
synchronization on custom object
```java
public void some someMethod(Object someLock) {
    //...
    //this code is protected by someLock internal monitor
    synchronized(someLock){ 
        //...
    }
    //...
}
```
synchronization on **this**
```java
public void synchronized otherMethod(){
    ...
}
```
synchronization on **SomeClass.class** object
```java
public class SomeClass{
    public static void synchronized otherMethod(){
        //...
    }   
}
```

#HSLIDE
## Internal monitor
Every object has **internal monitor**  
Monitor consists of:
- mutex
- with ability to wait (block) for a certain condition to become true

#HSLIDE
## Internal monitor
<img src="lecture10/presentation/assets/img/monitor.png" alt="monitor" style="width: 500px;"/>

#HSLIDE
## wait()/notify()
Class **Object** has API for internal monitor:
```java
class java.lang.Object {
    public final native void wait(long timeout) throws InterruptedException;
    public final native void notify();
    public final native void notifyAll();
}
```

#HSLIDE
## Agenda
1. Threads and processes
1. Multiple threads in game
1. Parallelism and Concurrency
1. What can go wrong with concurrency?
1. What to do?
1. **[Practice]**

#HSLIDE
### Queue
Queue is a shared resource in a multithreaded environment.

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
## Reasoning about concurrent programs
Bugs in concurrent programs are hard to reproduce. Hopefully we have toolchain for analysis of multithreaded programs.  
**jcstress**
http://openjdk.java.net/projects/code-tools/jcstress/  
(requires JDK9)

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
- Java provide powerful Memory Model that allows to create high-performance concurrent applications.
- concurrency is not obvious
- To make concurrent program correct we **must** use proper synchronization
- Without proper synchronization we can get hardly-reproducible bugs.
- The reason for this bugs are weakened guaranties for atomicity, visibility and ordering in multithreaded environment
- Avoid concurrency if possible
- Avoid shared mutable state if possible
- Use final variables if possible
- Use ThreadLocal if possible
- If concurrency is inevitable, use java.util.concurrent
- To use low-level concurrency you must fully understand it

#HSLIDE
## References
[Java concurrency in practice (signature book for Java Developer)](https://www.amazon.com/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601)   
[Alexey Shipilev blog (JMM, concurrency, performance, benchmarks for people, JDK contributor)](https://shipilev.net/)  
[Doug Lea-s home page (java.util.concurrent father and famous spec in concurrency and allocators)](http://g.oswego.edu/)  
[Java Memory Model Pragmatics (best explanation of JMM - available in russian)](https://shipilev.net/#jmm)  
[JMM Under the hood (deep explanation of JMM)](http://gvsmirnov.ru/blog/tech/2014/02/10/jmm-under-the-hood.html)  
[What Every Dev Must Know About Multithreaded Apps (Common knowledge)](https://lyle.smu.edu/~coyle/cse8313/handouts.fall06/s04.msdn.multithreading.pdf)  
  

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
