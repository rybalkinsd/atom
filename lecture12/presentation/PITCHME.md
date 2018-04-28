---
# Java
lecture 12
## Garbage Collection

---
## Отметьтесь на портале
https://sphere.mail.ru/

---
### get ready
https://github.com/rybalkinsd/atom
```bash
> git fetch upstream
> git checkout -b lecture12 upstream/lecture12
> cd lecture12
```
Refresh gradle project


---
## Agenda
1. Heap
1. Object layout
1. GC Theory
1. Java Garbage Collection
1. Troubleshooting

---
## GC is JVM-specific
The following material is mostly **JVM-specific**  
We will look at [**HotSpot**](http://www.oracle.com/technetwork/articles/javase/index-jsp-136373.html) implementation, as it is De facto standard for servers  

---
## Agenda
1. **[Heap]**
1. Object layout
1. GC Theory
1. Java Garbage Collection
1. Troubleshooting

---
## Structure of java process
Global structure of java process is defined by Java Virtual Machine Specification (JVMS)
https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.5  
<img src="lecture12/presentation/assets/img/heap.png" alt="heap" style="width: 600px;"/>  


---
## Heap structure
Heap is automatically managed by Garbage Collector (GC).  
The structure of heap depend on chosen GC implementation.  
**Hotspot** provide several implementations. (proceed reading)

---
What happen when
```java
new Object();
```
1. Object is allocated on heap
1. Reference is allocated on stack
  
What happen when
```java
int i = 10;
```
int is allocated on stack

---
## Object allocation
￼... So objects are allocated on **heap**  
Q: Can objects (e.g. local for method) be allocated on stack?  
A: Not specified. But currently not. For curious: http://dev.cheremin.info/2016/02/stack-allocation-vs-scalar-replacement.html  
Q: Is that this heap? https://en.wikipedia.org/wiki/Heap_(data_structure)  
A: No! Heap has complex structure which depends on chosen **GC**   
Q: So, how is Object represented in heap? A: ...

---
## Object allocation example
https://docs.oracle.com/javase/tutorial/java/javaOO/objectcreation.html  
<img src="lecture12/presentation/assets/img/allocation.png" alt="allocation" style="width: 600px;"/>  

---
## Agenda
1. Heap
1. **[Object layout]**
1. GC Theory
1. Java Garbage Collection
1. Troubleshooting

---
## Game: guess footprint
**footprint** - total size of reference + shadow (retained) size (all the referenced objects and primitives recursively) 
What is the footprint of:
1. int x = 10;
2. new Integer(10);
3. new Long(10);
4. new Integer[1000];
5. new ArrayList<Integer>(1000);
6. new LinkedList<Integer>(1000);
7. new HashSet<Integer>(1000);
8. new HashMap<Integer>(1000);
9. new ConcurrentHashMap<Integer>(1000);

**Warning:** Not an easy game!

---
## Object layout
Layout - how objects are represented in heap  
“The Java Virtual Machine does not mandate any particular internal structure for objects”  
https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.7  
We will look at **HotSpot** implementation (Java 8)  

---
## Object layout details
Object layout is:  
- JVM specific
- bitness dependent
- tunable by JVM flags  

---
## Object layout in hotspot
Example for x64 system with [**-XX:+UseCompressedOops**](http://docs.oracle.com/javase/7/docs/technotes/guides/vm/performance-enhancements-7.html#compressedOop) for heaps less than 32GiB object reference is 32 bit else 64 bit  
<img src="lecture12/presentation/assets/img/layout.png" alt="layout" style="width: 600px;"/>  

---
## jol
Good tool for object layout analysis - **Java Object Layout** (jol)  
http://openjdk.java.net/projects/code-tools/jol/  
[mvnrepository](https://mvnrepository.com/artifact/org.openjdk.jol/jol-core)  
[samples](https://mvnrepository.com/artifact/org.openjdk.jol/jol-samples)  
> @see org.openjdk.jol.samples.JOLSample_01_Basic  
@see org.openjdk.jol.samples.JOLSample_02_Alignment  
@see org.openjdk.jol.samples.JOLSample_03_Packing  
@see ru.atom.lecture12.GuessSize  

---
## Agenda
1. Heap
1. Object layout
1. **[GC Theory]**
1. Java Garbage Collection
1. Troubleshooting

---
## GC theory
GC theory is mature. Same ideas are reused over different runtimes  
The most comprehensive and modern gc theory book:
http://gchandbook.org/

---
## GC is better then refcounter
1. Looping
2. Compacting/fragmentation

---
## Generations idea
Live fast die young
<img src="lecture12/presentation/assets/img/gen.png" alt="gen" style="width: 700px;"/>


---
## Stop-the-world
- sometimes we have to wait

---
## GC Tradeoff
Latency-Throughput-Footprint
<img src="lecture12/presentation/assets/img/triangle.png" alt="triangle" style="width: 700px;"/>


---
## Agenda
1. Heap
1. Object layout
1. GC Theory
1. **[Java Garbage Collection]**
1. Troubleshooting


---
# Java GC Basics
Q: Why we need Garbage Collector  
A: To remove garbage, obviously  
Q: What is garbage?  
A: Objects that are no longer referenced  
Q: Does cyclic references stall objects in heap forever  
A: No. Object is garbage if it can not be reached from GC Roots  
Q: What are GC Roots? A: ... (proceed reading)  


---
## GC Roots
<img src="lecture12/presentation/assets/img/gcroots.jpg" alt="gcroots" style="width: 700px;"/>  


---
## No manual memory management in Java
GC removes unused objects for you. No manual memory management is required.  
Moreover, GC **can not** be controlled directly.
```java
System.gc(); // this is just recomendation
Runtime.gc();// this is just recomendation
```
You still can manage memory via (Java Native Interface) **JNI** or via **misc.Unsafe** but one **never** encouraged to do so.  

---
## Memory leaks
While technically GC prevents memory leaks from errors during manual memory management (common in C++), you can still introduce memory leak by confusing GC from garbage removal  
- Unclosed resources of different kind  
use **try-with-resources**  
- ObjectInputStream and ObjectOutputStream  
call reset() to flush inner object caches  
- Threads (every thread has stack)  
use ThreadPools to control and reuse Threads  
- non-static inner classes  
instance of non-static inner class has reference to instance of enclosing class  
- thread-locals  
Thread-locals live while thread live unless explicitly cleared  

---
## Memory leaks examples
http://stackoverflow.com/questions/6470651/creating-a-memory-leak-with-java
https://habrahabr.ru/post/132500/
(except String.substring() - this is not the case anymore)

---
## GC is configurable
There are several implementations of GC in Hotspot. Each of them is configurable via **JVM parameters**.
<img src="lecture12/presentation/assets/img/gcs.png" alt="monitor" style="width: 700px;"/>  

---
## Do we need to configure our GC?
Default configuration is fine for simple applications. Bad configuration can be deadly for real projects.

---
## Is GC a silver bullet?
GC power comes at cost of **performance**:
1. GC is one or more threads working in background
2. All gc implementations in Hotspot are **stop the world** (sometimes during GC work all application threads stop running until gc done)

---
## Stop the world
All GCs in HotSpot are **Stop-the-world**
i.e. there are moments when all the application threads are stopped and GC is working.  
Different GCs implement different strategies to reduce pauses. Some even give guarantees of maximum pause time.  
There is an attempt to implement ‘ultra-low pause’ GC  
http://openjdk.java.net/projects/shenandoah/ (not production-ready)  
There are JVM implementations where GC is pauseless: https://www.azul.com/products/zing/ (proprietary)  


---
## Agenda
1. Heap
1. Object layout
1. GC Theory
1. Java Garbage Collection
1. **[Troubleshooting]**
    @see GarbageProducer

---
## Docker
[[Docker]](https://www.docker.com/)

**Docker** - toolchain for lightweight customizable deployment
(delivery of you code to production)

---
## Docker basics
**Image** - description of **minimal virtual machine** that will contain **application**, that we want to **deploy**.  
It is provisioned via **Dockerfile** :  
- what is our application  
- what we need for our application  
  
**Container** - instance of image, able to deploy 

---
## Why docker?
- lightweight
- solves problems with dependencies collision
- **development** environment is the same as **production**
- docker toolchain allows many cool features

---
## Docker practice

@see **lecture05/Dockerfile**



---
## Basic commands
If these commands work, then Docker installed properly  
  
1. show local images
    ```bash
    > docker images 
    ```

1. show running containers
    ```bash
    > docker ps
    ```

---
### Docker algorithm
1. Build docker image
1. Run docker container locally 
1. Publish docker image
1. Deploy docker container to container service


---
### 1) Build docker image
1. Build project with gradle
```bash
gradlew clean lecture05:build
```
1. Build docker **image**
```bash
docker build --tag helloboot:1.0 lecture05
```
1. Check image is built
```bash
docker images
```


---
### 2) Run docker container locally 
1. Run **container** locally
```bash
docker run -p 80:8080 YOUR_DOCKER_HUB_NAME/helloboot:1.0
```
1. Check container started
```bash
docker ps
```
1. Check application works


---
### 3) Publish docker image
1. login with your name on  
```bash
docker login
``` 
1. push image to repository
```bash
docker push YOUR_DOCKER_HUB_NAME/helloboot:1.0
```

---
### 4) Deploy docker container to AWS
1. Find your credentials  
https://docs.google.com/spreadsheets/d/1i-WoyUrpunxwmFYkUGTLqm6ue6J24V0Py4OkjCLCaVc/edit?usp=sharing
1. login  
https://302755701450.signin.aws.amazon.com/console  
1. go to  
Services -> EC2 Container Service
1. Deploy

---
## Docker full example
1. Build project
```
gradlew clean lecture05:build
```
1. Build docker **image**
```
docker build --tag YOUR_DOCKER_HUB_NAME/helloboot:1.0 lecture05
```
1. Run **container** from **image** locally
```
docker run -p 80:8080 YOUR_DOCKER_HUB_NAME/helloboot:1.0
```
1. login into docker hub (if not logged in already)
```
docker login
```
1. push image to docker hub
```
docker push YOUR_DOCKER_HUB_NAME/helloboot:1.0
```
1. go to **aws** web interface and deploy


---
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
