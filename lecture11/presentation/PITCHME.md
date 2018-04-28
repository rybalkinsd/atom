---
# Java
lecture 11
## Practical Concurrency

---
## Отметьтесь на портале
https://sphere.mail.ru/

---
### get ready
https://github.com/rybalkinsd/atom
```bash
> git fetch upstream
> git checkout -b lecture11 upstream/lecture11
> cd lecture11
```
Refresh gradle project


---
## Agenda
1. concurrency revisited
1. final and ThreadLocal
1. synchronized - critical section
1. volatile
1. java.util.concurrent
1. ConcurrentHashMap
1. Practice

---
## Agenda
1. **[concurrency revisited]**
1. final and ThreadLocal
1. synchronized - critical section
1. volatile
1. java.util.concurrent
1. ConcurrentHashMap
1. Practice

---
## Quiz
1. **thread-safety** - ?
1. **concurrency** - ?
1. **concurrency** example from bomberman?  
1. **shared mutable state** - ?  
1. How to avoid **concurrency**?  
1. **race** vs **data race**  
1. What **problems** will we have without proper synchronization?
1. What is **proper synchronization**?

---
## Thread-safety
A class is thread-safe if it behaves correctly when accessed from multiple threads, regardless of the scheduling or interleaving of the execution of those threads by the runtime environment, and with no additional synchronization or other coordination on the part of the calling code
(from JCiP)  
https://www.amazon.com/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601

---
## Proper synchronization
1. synchronized
1. reads and writes to volatile variables
1. java.util.concurrent

---
## Problems
- Data races (caused by weak atomicity, visibility and ordering guaranties of JMM)
- Deadlocks
- Livelocks
- Performance problems

---
## Agenda
1. concurrency revisited
1. **[final and ThreadLocal]**
1. synchronized - critical section
1. volatile
1. java.util.concurrent
1. ConcurrentHashMap
1. Practice

---
## No state
No state - no concurrency
> stateless objects are always thread-safe

---
## Final (immutable) state
immutable objects are thread-safe if properly constructed
```java
//Only reference to 'players' is immutable. Concurrent access to list still lead to data races
public final List<Player> players = new ArrayList<>();
```
> Make *final* as much shared variables as possible

---
## ThreadLocal (unshared) state
```java
public class SomeClass {
    ThreadLocal<Object> locals = new ThreadLocal<>();
    
    public void someMethod(){
        //this will return different objects for different threads
        locals.get();        
    }
}
```
As with **final** - ThreadLocal only guarantees, that the reference, that is accessed via **ThreadLocal** variable (‘locals’ in example) is thread local, no in-depth thread locality.  
> @see thread_local

---
## Avoid concurrency if possible
- be stateless
- use final
- use ThreadLocal

---
## Agenda
1. concurrency revisited
1. final and ThreadLocal
1. **[synchronized - critical section]**
1. volatile
1. java.util.concurrent
1. ConcurrentHashMap
1. Practice

---
## Critical section
In Java we can declare code block as **synchronized** on some **object**.  
The object will protect the code block and allow only single thread to enter the **code block** simultaneously  
This is possible because in Java **every object** has **internal monitor**


---
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

---
## Easiest solution - synchronized
With **synchronized** we **avoid concurrency** in a block of code (if choose lock properly)  
Concurrency is actually about **data**, not **code**  
We must protect data from concurrent access, not code blocks  
That is we must synchronize all accesses to **data**, else we have **data races**

---
## Every object has internal monitor
Every object has **internal monitor**  
Monitor consists of:
- mutex
- with ability to wait (block) for a certain condition to become true

---
## Internal monitor internals
<img src="lecture10/presentation/assets/img/monitor.png" alt="monitor" style="width: 500px;"/>

---
## wait()/notify()
Class **Object** has API for internal monitor:
```java
class java.lang.Object {
    public final native void wait(long timeout) throws InterruptedException;
    public final native void notify();
    public final native void notifyAll();
}
```

---
## Synchronize all accesses
> @see ru.atom.lecture11.volatileexample  

Fix VolatileExample with **synchronized**

---
## synchronized guarantees
```java
public Object l1; //Use as lock
```  

thread1 -> **[acq l1]** sync on l1 **[rel l1]** -------------------------------------------->  
thread2 -> |||||||||||||||||||||||||||||||||||||||||||**[acq l1]** sync on l1 **[rel l1]**-->  
  
[acquire l1] **publishes** everything before previous [release l1]  
they say [release l1] **synchronizes with** [acquire l1] 

---

## What does it mean?
To see changes in shared variable you **must**:
1. synchronize **both** reads and writes of variable
1. synchronize on **the same lock** 
  
**Once again:**
- Synchronizing only writes or only reads is **bullshit** (has no effects)
- Synchronizing on different locks is **bullshit** (has no effects)

**Other myths that are not true**:  
https://shipilev.net/blog/2016/close-encounters-of-jmm-kind/#_wishful_thinking_hold_my_beer_while_i_am

---

## Happens-before relation
More formally, this is defined with **synchronizes-with** and **happens-before** relations  
https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html  
<img src="lecture11/presentation/assets/img/JMM_happens_before.jpg" alt="monitor" style="width: 600px;"/> 

---
## Deadlock
<img src="lecture11/presentation/assets/img/deadlock.jpg" alt="monitor" style="width: 500px;"/>

**Deadlock** - a state in which each member of a group of actions, is waiting for some other member to release a lock

> @see ru.atom.lecture11.deadlock

---
## Detect deadlock with jstack
**jstack** prints Java stack traces of Java threads for a given Java process or core file or a remote debug server.  
http://docs.oracle.com/javase/7/docs/technotes/tools/share/jstack.html
```bash
> jstack <pid>
```

---
## Agenda
1. concurrency revisited
1. final and ThreadLocal
1. synchronized - critical section
1. **[volatile]**
1. java.util.concurrent
1. ConcurrentHashMap
1. Practice

---
## volatile
```java
volatile long money;
```
**volatile** provide a number of guaranties:
1. read/write **atomicity**
1. guaranteed **visibility** of writes
1. guaranteed **ordering** of **volatile read/writes** 
1. **happens-before** semantics

---
## volatile
Practically **volatile** means (first three points) that there will be **no data races** over given field.  
i.e. everything will be **OK** with this variable.  
and specific guaranties of publication of other variables  
> @see ru.atom.lecture11.volatileexample

Fix VolatileExample with volatile

---
## Only reference is volatile
```java
//only reference players is volatile. Writing to players from different threads still lead to data races
private volatile List<Player> players;
```

---
## Why not all variables volatile
In sake of **performance**  
https://shipilev.net/blog/2014/all-accesses-are-atomic/  
10x average speedup for Intel x64

---
## Latency numbers every programmer should know
https://gist.github.com/jboner/2841832

---
## Try fix data races with volatile
> @see ru.atom.lecture11.dataraces

---
## volatile guarantees
volatile int v1;//shared variable  
thread1 --> **[write v1 (v1=42)]**-------------------------------->    
thread2 --> ||||||||||||||||||||||||||||||**[read v1 (someVar=v1)]**->  
[read v1] **publishes** everything before previous [write v1]  
they say [write v1] **synchronizes with** [read v1]  

---
## Volatile r/w is like synchronized
**write** of volatile variable = **unlock** monitor  
**read** of volatile variable = **lock** monitor  
  
**Deep idea:**  
In this sense volatile r/w is the same as **synchronized** but **with** concurrency

---
## JMM is about [acquire] and [release] synchronization
Mostly

---
## High-level synchronization
Basic synchronization primitives in Java are **synchronized** and **volatile**.
JDK provide high-level API for concurrency
- Concurrent collections
- Atomics
- Synchronizers

---
## Agenda
1. concurrency revisited
1. final and ThreadLocal
1. synchronized - critical section
1. volatile
1. **[java.util.concurrent]**
1. ConcurrentHashMap
1. Practice

---
## Atomics
Atomics provide **non-blocking** operations on common objects. Also provides methods for atomic **‘check then act’** operations (compareAndSet, incrementAndGet)
> @see ru.atom.lecture11.dataraces/Stopper.java
> @see javaConcurrentAnimated.jar  
(AtomicInteger)

---
## Future
> @see javaConcurrentAnimated.jar  
(Future) 

---
## ForkJoinPool
> @see javaConcurrentAnimated.jar  
(Fork/Join)

---
## Synchronizers
> @see javaConcurrentAnimated.jar  
(CyclicBarrier, Phaser, BlinkingPhaser)


---
## Agenda
1. concurrency revisited
1. final and ThreadLocal
1. synchronized - critical section
1. volatile
1. java.util.concurrent
1. **[ConcurrentHashMap]**
1. Practice

---
## ConcurrentHashMap
A hash table supporting **full concurrency of retrievals** and **high expected concurrency for updates**.  
Separate locks on each part of map (**segment**)  
Iteration performed over copy of collection at some point  
https://habrahabr.ru/post/132884/  
https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html

---
## ConcurrentHashMap
<img src="lecture11/presentation/assets/img/concurrenthashmap.png" alt="monitor" style="width: 600px;"/>  

> @see javaConcurrentAnimated.jar  
(ConcurrentHashMap)

---
## Agenda
1. concurrency revisited
1. final and ThreadLocal
1. synchronized - critical section
1. volatile
1. java.util.concurrent
1. ConcurrentHashMap
1. Practice

---
## Agenda
1. concurrency revisited
1. final and ThreadLocal
1. synchronized - critical section
1. volatile
1. java.util.concurrent
1. ConcurrentHashMap
1. Atomics
1. **[Practice]**

---
## Practice
> @see ru.atom.lecture11.billing

In this example we have billing service that can send money from user to user

---
## Billing API
Create user with money
```bash
> curl -X POST localhost:8080/billing/addUser -d 'user=sergey&money=100000'
```
Show money for all users
```bash
> curl localhost:8080/billing/stat
```
Send money from one user to another
```$xslt
> curl -X POST 'localhost:8080/billing/sendMoney' -d 'from=sasha&to=sergey&money=1'
```
One-liner to reset default users
```bash
> curl -X POST localhost:8080/billing/addUser -d 'user=sergey&money=100000'; curl -X POST localhost:8080/billing/addUser -d 'user=sasha&money=100000'; curl localhost:8080/billing/stat
```

---
## Task
The implementation is **not thread safe**. Make it correct any way you like  
When you are done, ask for check and name your **IP address**  
I will use **JMeter** for load-testing your service  
http://jmeter.apache.org/  
(you can use it too, testing scenario is located at **lecture11/Test_billing.jmx**)


---
## References
[Java concurrency in practice (signature book for Java Developer)](https://www.amazon.com/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601)   
[Alexey Shipilev blog (JMM, concurrency, performance, benchmarks for people, JDK contributor)](https://shipilev.net/)  
[Doug Lea-s home page (java.util.concurrent father and famous spec in concurrency and allocators)](http://g.oswego.edu/)  
[Java Memory Model Pragmatics (best explanation of JMM - available in russian)](https://shipilev.net/#jmm)  
[JMM Under the hood (deep explanation of JMM)](http://gvsmirnov.ru/blog/tech/2014/02/10/jmm-under-the-hood.html)  
[What Every Dev Must Know About Multithreaded Apps (Common knowledge)](https://lyle.smu.edu/~coyle/cse8313/handouts.fall06/s04.msdn.multithreading.pdf)  
  

---
## Practice 2. Billing problem
> @see ru.atom.lecture11.billing

Need to solve a classic task about money transfer sync.

API:
```
curl -X POST 
     -H "Content-Type: application/x-www-form-urlencoded" 
     -d 'user=bob&money=42' 
 "http://localhost:8080/billing/addUser"
 
curl -X POST 
    -H "Content-Type: application/x-www-form-urlencoded" 
    -d 'from=alice&to=bob&money=12' 
 "http://localhost:8080/billing/sendMoney" 
 
curl -X GET 
 "http://localhost:8080/billing/stat" 
```

---
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
