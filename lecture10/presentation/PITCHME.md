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
**Process** has dedicated resources (memory)

**Threads** share memory space

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
<img src="lecture05/presentation/assets/img/newthread.png" alt="exception" style="width: 750px;"/>

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
> @see races

#HSLIDE
## What does it mean that threads share memory?
> @see SharedMutableState TODO example with shared variable

#HSLIDE
### Concurrency vs parallelism
**Concurrency** - contention on shared resources  
**Parallelism** is possible without concurrency  

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
## Agenda
1. Threads and processes
1. Multiple threads in game
1. Parallelism and Concurrency
1. What can go wrong with concurrency?
1. What to do?
1. Practice

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
##File operations
*java.nio.file* contains modern API for file read/write  
> @see test/ru.atom.lecture09.nio.NioFileApi.java


#HSLIDE
## IO
API for input and output to
- files
- network streams
- internal memory buffers
- ...  
IO API is **blocking**  
[http://docs.oracle.com/javase/tutorial/essential/io/](http://docs.oracle.com/javase/tutorial/essential/io/)


#HSLIDE
##Byte Streams
#### InputStream (source -> InputStream)  
AudioInputStream, ByteArrayInputStream, FileInputStream, FilterInputStream, ObjectInputStream, PipedInputStream, SequenceInputStream, StringBufferInputStream
#### OutputStream (OutputStream -> target)  
ByteArrayOutputStream, FileOutputStream, FilterOutputStream, PrintStream, ObjectOutputStream, PipedOutputStream
  
IO API is **blocking**
> @see System.out / System.err (PrintStream)  
> @see ru.atom.lecture09.io.ByteStreams.java

#HSLIDE
##Character streams
#### Reader (source --> Reader)  
BufferedReader, CharArrayReader, FilterReader, InputStreamReader, PipedReader, StringReader
#### Writer (Writer --> target)  
BufferedWriter, CharArrayWriter, FilterWriter, OutputStreamWriter, PipedWriter, PrintWriter, StringWriter
  
IO API is **blocking**
> @see ru.atom.lecture09.io.CharacterStreams.java  

#HSLIDE
##NIO
Source -async-> Channel --> Buffer  
Buffer --> Channel -async-> Target  
  
NIO API is **non-blocking**  
**details:** [http://tutorials.jenkov.com/java-nio/index.html](http://tutorials.jenkov.com/java-nio/index.html)

#HSLIDE
## IO Summary
Now we can read from and write to any external data sources.  
For filesystem operations we use java.io.Path

#HSLIDE
## Agenda
1. IO/NIO
1. **[Serialization]**
1. Reflection
1. Collections revisited
1. Exceptions revisited

#HSLIDE
## What is serialization
Way to persist java object (**serialize**) from java program  
and to load persisted java object (**deserialize**) into java program  

#HSLIDE
# Why need serialization?

#HSLIDE
## Default Java serialization
### What we need for Serialization to work:
1. implement Serializable (marker interface)
1. have **default** constructor
1. add class version
   ```java
   private static final long serialVersionUID = ...L;
   ```
1. put java object to ObjectOutputStream(OutputStream); that is we can immediately save it into File or send it via network e.t.c.
1. Deserialize via ObjectInputStream(InputStream);
@see src/ru.atom.lecture09.serialization.SerializationDeserializationTest.java

#HSLIDE
## Serializable class example
```java
public class ToSerialize implements Serializable {
    private static final long serialVersionUID = 123123123123L;

    private SomeSerializableClass someField;//this will be serilized

    public ToSerialize() {
    }
}
```

#HSLIDE
## Serialization is recursive
Serialization is **recursive**  
  
that is, every object, referenced from serialized will be serialized.  
  
So **everything** in reference hierarchy (if not transient) must be **Serializable**  
Almost all common library classes are serializable (Strings, Numbers, Collection and Maps implementations)

#HSLIDE
## Serialization customization
1. **transient** - ignore this field during serialization and deserialization
1. Implement **Externalizable** instead of **Serializable**
```java
public interface Externalizable {
  //custom serialization logic here
  void writeExternal(ObjectOutput out) throws IOException;
  //custom serialization logic here
  void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
}
```
1. Use something beyond java serialization
(store to custom json/xml/binary via library)

#HSLIDE
#Task
<img src="lecture09/presentation/assets/img/task.png" alt="exception" style="width: 500px;"/>

> @see src/ru.atom.lecture09.serialization

Here we have server that accepts serialized object of type **Packet**  
Implement ObjectClient and send you name in serialized Packet to **wtfis.ru:12345**  
Use **Socket** and **OutputStream** to send serialized **Packet**


#HSLIDE
## sniff tcp traffic with tcpdump
[http://www.tcpdump.org/](http://www.tcpdump.org/)  
tcpdump - standard unix tool to for traffic analysis
```bash
> tcpdump -Aq -s0 -i lo0 'tcp port 8090'
```

#HSLIDE
## Another nice tools
**tcpflow**  
[tcpflow on github](https://github.com/simsong/tcpflow)  
**wireshark**  
[home page](https://www.wireshark.org/)

#HSLIDE
## Agenda
1. IO/NIO
1. Serialization
1. **[Reflection]**
1. Collections revisited
1. Exceptions revisited

#HSLIDE
##Reflection
Standard library API for accessing Type information at Runtime
- **instanceof**  
- class **Class<T>** (and all the class contents: fields, methods, Constructors ...)
- class **ClassLoader**
Official tutorial: [https://docs.oracle.com/javase/tutorial/reflect/](https://docs.oracle.com/javase/tutorial/reflect/)

#HSLIDE
##Why need reflection
- Annotation processing (widely used inside frameworks)
- Class loading at runtime
- Introspection
(for example for IDE or code generation toolchain)
> @see test/ru.atom.lecture09.reflection

#HSLIDE
##Reflection drawback
- performance overhead
reflection is actually fast, but it breaks some optimizations  
[https://shipilev.net/blog/archive/reflection/](https://shipilev.net/blog/archive/reflection/)
- security restrictions  
every reflective call goes through SecurityManager
[https://docs.oracle.com/javase/tutorial/essential/environment/security.html](https://docs.oracle.com/javase/tutorial/essential/environment/security.html)
- exposure of internals reflection breaks abstraction  
**One must use reflection Wisely!**  
(actually as part of specific design patterns)

#HSLIDE
##Reflection example
We can for example **configure application** by choosing interface interface implementation in parameters file
>￼@see test/ru.atom.lecture09.reflection.configuration

Actually any **framework** has it's own harness for configuration  
(recall for example **hibernate.cfg.xml**)  
It may actually work via reflection inside

#HSLIDE
## Agenda
1. IO/NIO
1. Serialization
1. Reflection
1. **[Collections revisited]**
1. Exceptions revisited

#HSLIDE
1. Нарисуйте иерархию классов коллекций
2. ArrayList - устройство и асимптотика
3. LinkedList - устройство и асимптотика
4. HashMap - устройство и асимптотика
5. какие требования предъявляются к объектам, помещаемым в hashmap
6. какие требования предъявляются к объектам, помещаемым в treemap

#HSLIDE
## Agenda
1. IO/NIO
1. Serialization
1. Reflection
1. Collections revisited
1. **[Exceptions revisited]**

#HSLIDE
1. Нарисуйте иерархию исключений
2. checked и unchecked exceptions
3. что будет с исключением, выкинутым из блока finally?
4. что такое try-with-resources?
5. что будет с исключением, выкинутым при закрытии ресурса?

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
