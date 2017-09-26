#HSLIDE
# Java
lecture 2
## Basics

#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/

#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b lecture02 upstream/lecture02
```

#HSLIDE
### Agenda
1. Classes and objects
1. Inheritance
1. Interface and abstract class
1. Enum

#HSLIDE
### Classes and objects
1. **[Classes and objects]**  
1. Inheritance
1. Interface and Abstract class
1. Enum

#HSLIDE 
### Flashback
- Java is **object-oriented**, **class-based**
- Java has static strong typization 

#HSLIDE
### Static strong typization
- Static == compile time
    + \+ fast runtime
    + \+ errors in compile time
    - \- more time on prototyping
- strong typization - *no strict definition*, example:
    ```java
    long num = 42; // <-- legal
    int mindTheGap = 42L; // <-- compilation error
    ```
 
#HSLIDE
### Object oriented
- Everything is an object*
- No code outside class

#HSLIDE
### `class` Definition
```java
class Player {
    private int id;
    private String name;
}
``` 

#HSLIDE
### Instantiation
```java
Player myPlayer = new Player();
```

#HSLIDE
### Behind the scenes
<img src="lecture02/presentation/assets/img/allocation.png" alt="me" style="width: 750px;"/>
 
**pOne != pTwo**
 
**pTwo == winner**

#HSLIDE
### `null` keyword

`null` is a default value for reference type.

```java
String str = null;

Player player = null;

assertThat(player, is(not(instanceOf(Player.class)))); // <-- OK
assertFalse(player instanceOf Player); // <-- OK
assertThat(null, is(not(instanceOf(AnyClass.class)))); // <-- OK 
```

#HSLIDE
### quiz

```java
System.out.println(null == null);

// 1. `false` in output
// 2. `true`  in output
// 3. NullPointerException
```

[Read more about `null`](http://javarevisited.blogspot.ru/2014/12/9-things-about-null-in-java.html)

#HSLIDE
###Constructor
```java
class Player {
    private int id;
    private String name;
    
    public Player(int paramId, String paramName) {
        id = paramId;
        name = paramName;
    }
}
```

Looks shitty

#HSLIDE
### `this` keyword
```java
class Player {
    private int id;
    private String name;
    
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```
[Read more about `this`](https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html)

#HSLIDE
### OK, now we have a constructor
 
`Player(int id, String name)`

```java
Player simplePlayer = new Player();
Player customPlayer = new Player(10, "Niels Bohr");
```

#### Looks good?

#HSLIDE
### Default constructor

Of course *NO*.
```java
Player simplePlayer = new Player(); // <-- Compilation error
```

The default constructor is a no-argument constructor automatically generated **unless** you define any constructor in class.

[Read more in official docs](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html)

[Read more on Stackoverflow](http://stackoverflow.com/questions/4488716/java-default-constructor)


#HSLIDE
### Inheritance
1. Classes and objects  
1. **[Inheritance]**
1. Interface and Abstract class
1. Enum


#HSLIDE
### Inheritance
#### Is-a relation

```java
class Message { 
    private String content;
}

class TitledMessage extends Message {
    private String title;
}
```
Titled message **is a** Message

#### Single class – single superclass


#HSLIDE
# Java does not support multiple inheritance


#HSLIDE
### Access modifiers

1. **private** - only from class code
    ```java
    private Object topSecret; 
    ```
    
1. **default** (package private) - as private + within package
    ```java
    int number = 42;
    ```

1. **protected** - as default + from subclasses
    ```java
    protected Boolean секретик;
    ```

1. **public** - worldwide
    ```java
    public String getMe;
    ```

[Read more in official docs](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)


#HSLIDE
### `instanceof` operator, miss me?

```java
Message message = new Message();

assertTrue(message instanceof Message); // <-- OK
assertThat(message, is(instanceOf(Message.class))); // <-- OK
```

`instanceof` **is not** slow	


#HSLIDE
### `Object` class #1
Everything* is instance of `Object`.

```java
// Informally
class Message extends Object { 
}
```

```java
assertThat(message, is(instanceOf(Object.class))); // <-- OK
```

#HSLIDE
### Constructors and inheritance

I want:
```java
TitlesMessage message = new TitledMessage(title, content);
```

#HSLIDE
### Constructors and inheritance

```java
class Message {
    private String content;
    
    public Message(String content) {
        this.content = content;
    }
}
```

#HSLIDE
### Constructors and inheritance

```java
class TitledMessage extends Message {
    private String title;
    
    public TitledMessage(String title, String content) {
        // hmmmm
    }
}

class Message {
    private String content;
    
    public Message(String content) {
        this.content = content;
    }
}
```


#HSLIDE
### super

```java
class TitledMessage extends Message {
    private String title;
    
    public TitledMessage(String title, String content) {
       super(content);
       this.title = title;
    }
}

class Message {
    private String content;
    
    public Message(String content) {
        this.content = content;
    }
}
```


#HSLIDE
### What about init order?

@See ru.atom.instantiation


#HSLIDE
### Methods
Declaration
```java
class Message {
    private String content;

    public String getContent() {
        return content;
    }
    
    public Message(String content) {
        this.content = content;
    }
}
```

Usage
```java
Message message = new Message("my content");
message.getContent();

assertThat(message.getContent(), is(equalTo("my content"))); // <-- OK
```


#HSLIDE
### Methods, overloading

Lets add some "pagination"
```java
class Message {
    private static final int CHARS_PER_PAGE = 256;
    private String content;
    
    private String getContent() {
        return content;
    }
    
    private String getContent(int pageNum) {
        if (pageNum < 0) {
            throw new IllegalArgumentException(
                    "Page number should be >= 0, got " + pageNum);
        }
            
        return content.substring(pageNum * CHARS_PER_PAGE, 
            (pageNum + 1) * CHARS_PER_PAGE);
    }
    
    // ...
}
```


#HSLIDE
### `static` keyword

static - "common for all class instances"

Definition
```java
class Utils {
    public static final int DEFAULT_MAX = 0;
    public static int getMax(int[] values) {
        if (values == null || values.length == 0) {
            return DEFAULT_MAX;
        }
        
        return Arrays.stream(values)
                .max()
                .getAsInt();    
    }
}
```

Usage
```java
int max = Utils.getMax(new int[] {1, 2, 3});
System.out.println(Utils.DEFAULT_MAX);

```


#HSLIDE
### Methods, polymorphism
```java
class TitledMessage extends Message {
    private String title;
    
    @Override
    public String getContent() {
        return "Title: " + this.title + ".\n" + getContent();
    }
    // ...
}
```


#HSLIDE
### Override definition

Instance method in a subclass with the **same signature** (name, plus the number and the type of its parameters) 
and **return type** as an instance method in the superclass **overrides** the superclass's method.

[Read more in official docs](https://docs.oracle.com/javase/tutorial/java/IandI/override.html)

**Note:** `@Override` is **just an annotation to declare** your intentions to override method 


#HSLIDE
### `Object` class #2
```java
class Object {
    public boolean equals(Object obj)
    public int hashCode()
    public String toString()

    public final Class getClass()
    protected Object clone() throws CloneNotSupportedException
    protected void finalize() throws Throwable
    // ...
}
```


#HSLIDE
### So

```java
class Message {
    private String content;
    
    @Override
    public String toString() {
        return content;
    }
}
```


#HSLIDE
### Polymorphism, One more thing #1

```java
Message message = new TitledMessage("Awesome title", "Perfect content");

message instanceOf TitledMessage <-- It is true 
```

**Do not** disclose the details of implementation (without need).

Сonsequence - use "interface" wherever you can.


#HSLIDE
### Polymorphism, One more thing #2

Override resolves method in **runtime**

*Note:*
Overload resolves method in **compile-time**


#HSLIDE
### Interface and Abstract class
1. Classes and objects  
1. Inheritance
1. **[Interface and Abstract class]**
1. Enum


#HSLIDE
### `interface` definition

```java
interface Storable {
    void saveTo(File file); 
}
```


#HSLIDE
### `interface` usage

```java
class Message implements Storable {
    private String content;
    
    @Override
    public void saveTo(File file) {
        // some stuff to save Message to file
    }  
    
    // ...
} 
```

```java
Storable smthToSave = new Message("Perfect content");
smthToSave.saveTo(new File("path to file"));

assertThat(smthToSave, is(instanceOf(Message.class))); // <-- OK
assertThat(smthToSave, is(instanceOf(Storable.class))); // <-- OK
```

#HSLIDE
### Single class - multiple interfaces

```java
class Message implements Storable, Sendable, Readable {
}
```


#HSLIDE
### Interface inheritance

```java
interface FaultTolerantStorable extends Storable, Serializable {
    void handleStoreErrors();
    
    default boolean checkStored(File file) {
        return file != null && file.exists();
    }
    
}
```

#HSLIDE
### `abstract` class
```java
public abstract class AbstractHuman {
    protected String name;
    public abstract String sayHi();
}

public class Englishman extends AbstractHuman {
    @Override
    public String sayHi() {
        return "Hi, I'm" + name;
    }
}
```


#HSLIDE
### abstract class vs interface

|                   | Interface             | Abstract class                |
|:----------------- |:--------------------- | :-----------------------------|
| Inheritance       | implement many        | extend one                    |
| Fields            | public static only    | no limits                     |
| Access modifiers  | public only           | no abstract private methods   |
| Constructors      | no constructors       | no limits                     |


#HSLIDE
1. Classes and objects  
1. Inheritance
1. Interface and Abstract class
1. **[Enum]**


#HSLIDE
### Enum
```java
enum Gender {
    Male,
    Female,
    Other    
}
```

No inheritance for enums.

Interfaces are allowed.


#HSLIDE
### Enum

@See ru.atom.enums 


#HSLIDE
### All together now
```java
package ru.atom.model.object.actor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.object.GameObject;
import ru.atom.util.V;

public class Actor extends GameObject implements Tickable {
    private final static Logger log = LogManager.getLogger(Actor.class);
    private V velocity;

    @Override
    public void tick(long time) {
        V before = position;
        move(time);
        log.info("Moved: {} -> {}.", before, position);

    }

    private void move(long time) {
        position = position.move(velocity.times(time));
    }

    public final Actor setVelocity(V velocity) {
        this.velocity = velocity;
        return this;
    }
}
```


#HSLIDE
### packages and import
 **Package** is a grouping of related types providing access protection and name space management.
 
 - make types easier to find and use
 - avoid naming conflicts
 - control access
 
Fundamental classes are in java.lang

[Read more in official docs](https://docs.oracle.com/javase/tutorial/java/package/packages.html)


#HSLIDE
### `final` keyword

- constant declaration 
```java
class Utils {
    public final int DEFAULT_MAX = 0;
}
```
    
- final method (forbidden override)
```java
class Message {
    public final String getContent() { 
       return content; 
    } 
}
```
    
- final class (forbidden inheritance)
```java
final class Message {
}
```


#HSLIDE
### Practice
@See ru.atom.geometry

<img src="lecture02/presentation/assets/img/geometry.png" alt="geometry" style="width: 750px;"/>


#HSLIDE
### How to
1. Pull-request from your repo /lecture02 to course repo /lecture02
2. 3 Points
    - remove `@Ignore` from `PointPointCollisionTest`
    - all tests and checks should pass
3. 3 Points more
   - remove `@Ignore` from `BarBarCollisionTest` and `BarPointCollisionTest`
   - all tests and checks should pass
   
      
#HSLIDE
### Bonus - gradlew build
```bash
# build fat jar @see build.gradle in lecture02 submodule 
> ./gradlew :lecture02:jar
> blah-blah
> BUILD SUCCESSFUL

> java -jar lecture02/build/libs/lecture02-1.0-SNAPSHOT.jar 
> Hello, World!

# jar listing
> jar -tfv  lecture02/build/libs/lecture02-1.0-SNAPSHOT.jar
```


#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
