#HSLIDE
# Java
lecture 9
## IO, Serialization, Reflection

#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/

#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b lecture09 upstream/lecture09
```
Refresh gradle project


#HSLIDE
## Agenda
1. IO/NIO
1. Serialization
1. Reflection
1. Collections revisited
1. Exceptions revisited


#HSLIDE
## Agenda
1. **[IO/NIO]**
1. Serialization
1. Reflection
1. Collections revisited
1. Exceptions revisited

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
