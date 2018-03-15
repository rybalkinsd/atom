#HSLIDE
# Java
lecture 3
## Generics & Collections


#HSLIDE
## Отметьтесь на портале
https://sphere.mail.ru/


#HSLIDE
### get ready
[https://github.com/rybalkinsd/atom](https://github.com/rybalkinsd/atom)
```bash
> git fetch upstream
> git checkout -b lecture03 upstream/lecture03
> cd lecture03
```

#HSLIDE
### Agenda
1. External libraries usage
1. Exceptions
1. Generics
1. Collections
1. Homework 2


#HSLIDE
### External libraries usage
1. **[External libraries usage]**
1. Exceptions
1. Generics
1. Collections
1. Homework 2


#HSLIDE
### adding library manually
All the class and jar files must be in [CLASSPATH](https://docs.oracle.com/javase/tutorial/essential/environment/paths.html)  
It is hard to control CLASSPATH manually, build tools may help (like **gradle**)

#HSLIDE
### gradle as dependency manager
External libraries (**dependencies**) are managed with **gradle**.  
  
That is: **gradle** downloads libraries from repository and adds them to **CLASSPATH**  
  
The most famous public one is **maven central**:  
[https://search.maven.org/](https://search.maven.org/)


#HSLIDE
### dependencies
**lecture03/build.gradle** :
```groovy
dependencies {
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    compile group: 'org.slf4j', name: 'slf4j-api', version: '1.7.25'
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-slf4j-impl
    compile group: 'org.apache.logging.log4j', name: 'log4j-slf4j-impl', version: '2.10.0'
    // https://mvnrepository.com/artifact/junit/junit
    testCompile group: 'junit', name: 'junit', version: '4.4'
}
```

#HSLIDE
### slf4j
[slf4j](https://www.slf4j.org/) provide logging API. Developer can choose one of several implementations.  
We only use **slf4j** API  
  
**slf4j** API usage example:
```java
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("LOGGER_NAME");

    void someMethod(){
        log.info("someMethod is called");
    }
```


#HSLIDE
### log4j2
For slf4j API implementation that we choose is **log4j2**  
To customize logging you must create **log4j2.properties** in resources folder (in CLASSPATH)



#HSLIDE
### Exceptions
1. External libraries usage
1. **[Exceptions]**
1. Generics
1. Collections
1. Homework 2


#HSLIDE
### Anything that can go wrong will go wrong

- lost connection
- inconsistent object state
- out of memory
- file not found


#HSLIDE
### Some problems could be fixed in runtime
Recovery:
- reconnect
- fix/rebuild object
- free some memory
- create new file

or
- cancel operations

#HSLIDE
### Exceptions
Java provide high-level mechanism for this situations called **Exceptions**

#HSLIDE
### Exceptions hierarchy
<img src="lecture03/presentation/assets/img/exception.png" alt="exception" style="width: 750px;"/>


#HSLIDE
## Checked exceptions must be handled
Compiler force you to check **checked exceptions**


#HSLIDE
### Throwable
Superclass of all errors and exceptions

```java
Throwable()
Throwable(String message)
Throwable(Throwable cause)
Throwable(String message, Throwable cause)

StackTraceElement getStackTrace()
Message getMessage()
Throwable getCause()
```

#HSLIDE
### Stacktrace
```java
Exception in thread "main" java.lang.NullPointerException: Ой всё
        at ru.atom.makejar.HelloWorld.getHelloWorld(HelloWorld.java:12)
        at ru.atom.makejar.HelloWorld.main(HelloWorld.java:8)

```

Build and run jar with
```java
package ru.atom.exception;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println(getHelloWorld());
    }

    public static String getHelloWorld() {
        throw new NullPointerException("Ой всё");
    }
}
```


#HSLIDE
### Exception handling 
### try-catch-finally
```java
try {
    statements
} catch (ExceptionType1 e) {
    statements
// more catches    
} catch (ExceptionType2 e) {
    statements
} finally {
    statements
}
```


#HSLIDE
### Exception handling example
```java
try {
    String possibleInt = "itsNotANumber";
    return Integer.parseInt(possibleInt);
} catch (ExceptionType e) {
    log.error("Exception in possibleInt parsing. {} is not an integer", possibleInt);
    return DEFAULT_INT_VALUE; 
} finally {
    if (dbConnection != null 
        && dbConnection.isOpened()) {
        
        dbConnection.close();
    }
}
```


#HSLIDE
### Exceptions
@See ru.atom.exception

1. try-catch-finally
1. catch or throws
1. multiple catches
1. try with resources


#HSLIDE
### Generics
1. External libraries usage
1. Exceptions
1. **[Generics]**
1. Collections
1. Homework 2

#HSLIDE
### non-generic

```java
public class Box {
    private Object object;

    public void set(Object object) { this.object = object; }
    public Object get() { return object; }
}
```

#HSLIDE
### Problem
In our code
```java
Box box = new Box();
String content = "Awesome content";
box.set(content);
```

In a galaxy far far away
```java
Object content = box.getContent();
// Nooooooooooooooo
// cast is not type safe
```

      
#HSLIDE
### Generic Solution
Definition
```java
/**
 * Generic version of the Box class.
 * @param <T> the type of the value being boxed
 */
public class Box<T> {
    // T stands for "Type"
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}
```

Usage
```java
Box<String> box = new Box<>();
String content = "Awesome content";
box.set(content);

String outgoing = box.getContent();

assertThat(outgoint, is(equalTo(content))); // <-- OK
```
      
      
#HSLIDE
### Generics in Java 

1. Way to implement general purpose algorithms
1. Way to avoid type casting
1. Provide type casting under the hood
1. Present and processed in **Compile-time**. Absent in runtime*       
      

#HSLIDE
### Generics Definition
Generic class
```java
class Clazz<T1, T2, T3> {
}

Clazz<Boolean, Integer, String> instance = new Clazz<>();
```

Generic method
```java
class Clazz {
    public <T> void foo(T t) {
        log.info("Generic method call with param {}", t);
    }
}

Clazz instance = new Clazz();
instance.foo("string value");
```
[Read more in official documentation](https://docs.oracle.com/javase/tutorial/java/generics/methods.html)


#HSLIDE
### Generics buzzwords
- Wildcards
- Type bounds
- Type erasure
- Bridge method
- Raw-type
      
#HSLIDE
### What about inheritance?
```java
class Clazz<T> { }
```
 
```
Clazz<Object> is not a superclass for Clazz<Integer>
```

#HSLIDE
### Bounds and restrictions
```java  
<T extends A & B & C>
<? extends A> - wildcard with restriction
<? super A> - wildcard with restriction
<?> - wildcard without restriction  
```
[Read more in official documentation](https://docs.oracle.com/javase/tutorial/java/generics/bounded.html)

#HSLIDE
### Type erasure


```java
public class Box {
    
    private Object t;

    public void set(Object t) { this.t = t; }
    public Object get() { return t; }
}
```

```java
Box box = new Box();
String content = "Awesome content";
box.set(content);

String outgoing = (String) box.getContent();
```


#HSLIDE
### Collections
1. External libraries usage
1. Exceptions
1. Generics
1. **[Collections]**
1. Homework 2


#HSLIDE
### Quiz  
```java
List<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);
list.addAll(Arrays.asList(2, 3, 4, 5, 6));
list.remove(new Integer(2));

assertThat(list.size(), is(equalTo( ??? )));
assertThat(list.get(2), is(equalTo( ??? )));
assertThat(list.contains(42), is( ??? ));
```

#HSLIDE
### ArrayList. Summary #1
- Auto resizable-array implementation of the `List` interface. i.e. dynamic array
- Place in hierarchy:
```java
    java.lang.Object
        java.util.AbstractCollection<E>
            java.util.AbstractList<E>
                java.util.ArrayList<E>
```

#HSLIDE
### ArrayList. Summary #2
- Providing interfaces
    - List 
    - RandomAccess
    - Serializable 
    - Cloneable
    - Collection
    - Iterable

[Read more (RU)](https://habrahabr.ru/post/128269/)


#HSLIDE
### ArrayList is a List
List is a Ordered Collection or sequence
```java
interface List<E> extends Collection<E> {
    E get(int index);
    E set(int index, E element);
    void add(int index, E element);
    E remove(int index);
    List<E> subList(int fromIndex, int toIndex);
    // ...
}
```


#HSLIDE
### ArrayList. Internals #1

```java
List<String> list = new ArrayList<>();
```
<img src="lecture03/presentation/assets/img/newarray.png" alt="exception" style="width: 600px;"/>

```java
list.add("0");
list.add("1");
```

<img src="lecture03/presentation/assets/img/array1.png" alt="exception" style="width: 600px;"/>


#HSLIDE
### ArrayList. Internals #2
```java
list.addAll(Arrays.asList("2", "3", "4", "5", "6", "7", "8"));
list.add("9");
```

<img src="lecture03/presentation/assets/img/array9.png" alt="exception" style="width: 600px;"/>


#HSLIDE
### ArrayList. Internals #3
```java
list.add("10");
```
Not enough capacity. Need (auto)resize.

<img src="lecture03/presentation/assets/img/arrayresized.png" alt="exception" style="width: 750px;"/>

<img src="lecture03/presentation/assets/img/array10.png" alt="exception" style="width: 750px;"/>

#HSLIDE
### Quiz
#### What is the difference between capacity and size in `ArrayList`?

#HSLIDE
### ArrayList. Complexity

|  contains  | add   | get   |  set  | remove | 
|:----------:|:-----:|:-----:|:-----:|:------:|
| O(n)       | O(1)* |  O(1) |  O(1) | O(n)   |


#HSLIDE
### ArrayList is a RandomAccess List

RandomAccess - marker interface.
Indicate that List support fast (generally constant time) random access.

RandomAccess helps generics algorithms.


#HSLIDE
### List is a Collection

Collection is a root interface of Collection Framework.

Collection represents a group of objects.

```java
interface Collection<E> extends Iterable<E> {
    int size();
    
    boolean contains(Object o);
    boolean add(E e);
    boolean remove(Object o);
    Iterator<E> iterator();
}
```


#HSLIDE
### Collection is an Iterable
```java
interface Iterable<T> {
    Iterator<T> iterator();
    default void forEach(Consumer<? super T> action) { ... }
}

interface Iterator<E> {
    boolean hasNext();
    E next();
}
```


#HSLIDE
### Single interface - multiple implementations #1
<img src="lecture03/presentation/assets/img/collectionimpl.png" alt="exception" style="width: 750px;"/>


#HSLIDE
### Single interface - multiple implementations #2
List implementations:
- ArrayList
- CopyOnWriteArrayList - thread-safe variant of ArrayList
- LinkedList
- Vector - synchronized list
- ...


#HSLIDE
### CopyOnWriteArrayList
A **thread-safe** variant of ArrayList in which all mutative
operations (**add**, **set**, and so on) are implemented by
making a fresh copy of the underlying array.

Complexity

|  contains  | add   | get   |  set  | remove | 
|:----------:|:-----:|:-----:|:-----:|:------:|
| O(n)       | O(n)  |  O(1) |  O(n) | O(n)   |


#HSLIDE
### LinkedList. Summary #1
- Doubly-linked list implementation of the **List** and **Deque**
interfaces.

- Place in hierarchy:
```java
java.lang.Object
    java.util.AbstractCollection<E>
        java.util.AbstractList<E>
            java.util.AbstractSequentialList<E>
                java.util.LinkedList<E>
```


#HSLIDE
### LinkedList. Summary #2
- Providing interfaces
    - List
    - Deque
    - Queue
    - Serializable 
    - Cloneable
    - Iterable 
    - Collection


#HSLIDE
### LinkedList internals #1
```java
List<String> list = new LinkedList<>();
```

<img src="lecture03/presentation/assets/img/linkednew.png" alt="exception" style="width: 400px;"/>


#HSLIDE
### LinkedList internals #2
```java
list.add("0");
```

Allocation

<img src="lecture03/presentation/assets/img/linked0.png" alt="exception" style="width: 400px;"/>

Linking

<img src="lecture03/presentation/assets/img/linked0linked.png" alt="exception" style="width: 400px;"/>


#HSLIDE
### LinkedList internals #3
```java
list.add("1");
```

Allocation

<img src="lecture03/presentation/assets/img/linked1.png" alt="exception" style="width: 400px;"/>

Linking

<img src="lecture03/presentation/assets/img/linked1linked.png" alt="exception" style="width: 400px;"/>


#HSLIDE
### LinkedList. Complexity

|  contains  | add   | get   |  set  | remove | 
|:----------:|:-----:|:-----:|:-----:|:------:|
| O(n)       | O(1)  |  O(n) |  O(n) | O(n)   |


#HSLIDE
### Interface Set
-  A collection that contains no duplicate elements.  More formally, sets
   contain no pair of elements **e1** and **e2** such that
    ```java
       e1.equals(e2);
    ```
    
- Implementations
    - HashSet 
    - TreeSet
    - EnumSet 
    - ConcurrentSkipListSet 
    - CopyOnWriteArraySet 
    - LinkedHashSet
    - ...


#HSLIDE
### HashSet
Set interface implementation, backed by a hash table (actually a HashMap instance).
It makes no guarantees as to the iteration order of the set.


#HSLIDE
### HashSet. Internals
<img src="lecture03/presentation/assets/img/hashset.png" alt="exception" style="width: 600px;"/> 
 

#HSLIDE
### General contract
For objects **a** and **b**:
```java
a.equals(b) => a.hashCode() == b.hashCode()

if a.hashCode() == b.hashCode() 
          a may be not equal b
          
a.hashcode() is the same during object lifetime
```


#HSLIDE
### HashSet. Complexity

|  contains  | add   | get   | remove | 
|:----------:|:-----:|:-----:|:------:|
| O(1)       | O(1)  |  O(1) |  O(1)  |


#HSLIDE
### TreeSet
The elements are ordered using their **Comparable** natural 
ordering, or by **Comparator** provided at set creation time, 
depending on which constructor is used.


#HSLIDE
### TreeSet. Internals
<img src="lecture03/presentation/assets/img/treeset.png" alt="exception" style="width: 600px;"/>

[Read more (RU)](https://habrahabr.ru/post/65617/)

#HSLIDE
### Functional interface Comparable<T>

```java
@Override
public int compareTo(T o) {
	return this.field – o.field;
}
```


#HSLIDE
### compareTo & equals
Any type of contract?
```java
a.equals(b) == true => a.compareTo(b) == 0
``` 

What about null?


#HSLIDE
### TreeSet. Complexity

|  contains  | add   | get   | remove | 
|:----------:|:-----:|:-----:|:------:|
| O(log(n))       | O(log(n))  |  O(log(n)) | O(log(n))  |


#HSLIDE
### Map 

```java
interface Map<K, V>
``` 

An object that maps keys to values.
Cannot contain duplicate keys.
Each key map to at most one value.
 

#HSLIDE
### Map methods
 ```java
boolean containsKey(Object key);
V get(Object key);
V put(K key, V value);
V remove(Object key);
```

#HSLIDE
### Why, Map?
Why Map is not a Collection?

#HSLIDE
### Why, Map?
From official FAQ:
> This was by design.
> We feel that mappings are not collections and collections are not mappings. 
> If a Map is a Collection, what are the elements? 


#HSLIDE
### Map implementations
- HashMap
- TreeMap
- LinkedHashMap
- EnumMap


#HSLIDE
### Notes
1. HashSet is a specific HashMap
1. TreeSet is a specific TreeMap 


#HSLIDE
### Complexity
HashMap

|  containsKey  | get   | put   | remove | 
|:----------:|:-----:|:-----:|:------:|
| O(1)       | O(1)  |  O(1) | O(1)  |


TreeMap

|  containsKey  | get   | put   | remove |
|:----------:|:-----:|:-----:|:------:|
| O(log(n))       | O(log(n))  |  O(log(n)) | O(log(n))  |

[Read more](http://infotechgems.blogspot.ru/2011/11/java-collections-performance-time.html)


#HSLIDE
### HashMap. Internals 
<img src="lecture03/presentation/assets/img/hashmap.png" alt="exception" style="width: 750px;"/>

#HSLIDE
### Practice
@See ru.atom.list

1. Implement ListNode class
1. Implement absent methods in CustomLinkedList
1. Implement Iterator for CustomLinkedList
1. Remove @Ignore in CustomLinkedListTest
1. All CustomLinkedListTest tests should pass

3 Points

#HSLIDE
### Summary
1. Generics are **compile-time**
1. Use **System.out.println()** for user interaction, do not use for logging
1. Use logger for logging
1. Use most appropriate data structure
1. Know your data structure's insides: ArrayList,LinkedList,HashSet,HashMap
1. General contract (hashCode/equals)


#HSLIDE
### Homework 2
1. External libraries usage
1. Exceptions
1. Generics
1. Collections
1. **[Homework 2]**


#HSLIDE
### Bulls and Cows
- Implement [Bulls and Cows game](https://en.wikipedia.org/wiki/Bulls_and_Cows)  
- make pull request to **homework2** branch  
- use dictionary from **[homeworks/HW2/dictionary.txt](https://github.com/rybalkinsd/atom/blob/homework2/homeworks/HW2/dictionary.txt)**  
- **deadline**: march 14  
- **mark**: 7 balls max  
  

#HSLIDE
### Game stages
1. Game prints **welcome text**
2. Game gets random word from **dictionary.txt** and prints **greeting with word length**
3. The game asks user for guess until user wins or **10 attempts** looses
4. If **win**, print congratulations; if **loose**, print loose text
5. Player is suggested to **start new game**


#HSLIDE
### Example game:
```text
Welcome to Bulls and Cows game!        //welcome text
I offered a 4-letter word, your guess? //greeting, hidden word is 'java'
> atom                                 //your guess
Bulls: 0   
Cows: 1
> lava
Bulls: 3
Cows: 0
> java
You won!                                // win text
Wanna play again? Y/N                   // suggest for new game
> N
```

#HSLIDE
### Implementation details
0. You need to develop a standalone java application  
0. Your application should work with console input/output  
0. Application should be able to run from console with
```bash
> java -jar BullsAndCows.jar  
```
0. You need to check all the exceptional situations (wrong input...)

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
