#HSLIDE
# Java
lecture 2
## Basics

#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/

#HSLIDE
# About me
<img src="https://avatars2.githubusercontent.com/u/710546?v=3&s=460" alt="me" style="width: 220px; float: left;"/>  

  yan.brikl@gmail.com 
  [https://github.com/rybalkinsd](https://github.com/rybalkinsd)  

  Java 5+ years

- Yandex, Allods Team (mail.ru group)
- Currently Senior Software Engineer at AliExpress.com

**Люблю зеленые билды**  

#HSLIDE
# Agenda
1. Classes and objects
1. Homework 2  

#HSLIDE
# Classes and objects 
1. **[Classes and objects]**  
1. Homework 2  

#HSLIDE 
# Flashback
- Java is **object-oriented**, **class-based**
- Java has static strong typization 


#HSLIDE
## Object oriented
- Everything is an object*
- No code outside object


#HSLIDE
## Static strong typization
 - Static == compile time
    + \+ fast runtime
    + \+ errors in compile time
    - \- more time on prototyping
 - strong typization - *no strict definition* example:
    ```java
    long num = 42; // <-- legal
    int mindTheGap = 42L; // <-- compilation error
    ```
 
#HSLIDE
### Definition
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
### Constructor
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

#HSLIDE
### Looks shitty
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
#### OK, now we have a constructor `Player(int id, String name)`

```java
Player simplePlayer = new Player();
Player customPlayer = new Player(10, "Niels Bohr");
```

#### Looks good?

#HSLIDE
#### Of cause *NO*

No-arguments constructor is *defined* in class 
if and only if no other constructor is defined&


#HSLIDE 
# 3. Basic syntax 
1. Course structure  
2. Language architecture  
3. **[Basic syntax]**    
4. Git  
5. Gradle
6. Homework 1  


#HSLIDE
#Expressions

```java
int value = 0;

int[] array = new int[10];
array[0] = 100;

System.out.println("Hello, world!");

if (value1 == value2)
    System.out.println("value1 == value2");

int commonVariable = 0;
if (commonVariable > -42) { // ← начало блока
    int innerVariable = commonVariable + 1;
    System.out.println(String.format(“Inner variable is %d“, innerVariable));
} // ← конец блока
/*
    а здесь innerVariable уже нет
*/
```
#HSLIDE
##if else
```java
if (18 == yourAge) {
    // у вас всё хорошо
} else if (yourAge > 18
           && yourAge <= 25) {
    // бывало и лучше
} else {
    // ¯\_(ツ)_/¯
}
```
#HSLIDE
## switch case
```java
switch (countOfApple) {
    case 1: // у нас есть 1 яблоко
        break;
    case 2: // у нас есть 2 яблока
        break;
    …
    default: 
        // прочие случаи
        break;
}

```

#HSLIDE
## loops
```
while (expression) statement

do { statement } while (expression)

for (initialization; termination; increment)
    statement
```
**Examples:**
```java
for (int i = 0; i < numberOfObjects; i++) {
    // iterates numberOfObjects times,
    // if numberOfObjects >= 0
}

int[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
for (int i : digits ) {
    System.out.println(“Digit: “ + i);
}

//Для хипстеров
IntStream.range(0, 10).forEach(digit -> System.out.println(digit));

IntStream.range(0, 10).forEach(System.out::println);

```

#HSLIDE 
# Methods
```java
public int getCountOfApples(List<Integer> boxes, Integer[] numberOfBoxes) 
        throws Throwable {

    Integer sumOfApples = 0;
    for (Integer i : numberOfBoxes) {
        sumOfApples += boxes.get(i);
    }
    return sumOfApples;
}
```
**Method signature** – method name + argument list

Access modifier **public**  
Return type **int**  
Method name **getCountOfApples**  
Parameter list **( … )**  
Exception list **throws Throwable**  
Method body **{ … }**  

#HSLIDE
# JDK Setup
1. Download [**JDK8**](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 

2. Look inside jdk directory
```
> ls jdk8/
```
```
> ls jdk8/bin/
```
java, javac, javap, jar

#HSLIDE
## JDK/JRE/JVM
**JDK** - Java Development Kit  
**JRE** - Java Runtime Environment  
**JVM** - Java Virtual Machine 
<img src="lecture01/presentation/assets/img/jdk-jre2.png" alt="me" style="width: 900px; float: left;"/>  

#HSLIDE
**JDK** - Java Development Kit  
**JRE** - Java Runtime Environment  
**JVM** - Java Virtual Machine   
<img src="lecture01/presentation/assets/img/jdk-jre.png" alt="me" style="width: 750px; float: left;"/>  
  
**JDK** =  
JRE + Tools  
  
**JRE** =  
JVM + Lang + Libs

#HSLIDE 
## From source to running program
<img src="lecture01/presentation/assets/img/codeflow.png" alt="me" style="width: 750px; float: left;"/>  

#HSLIDE 
## JDK setup *nix 
set **path** and **JAVA_HOME** environment variables  
**Linux:**
```bash
> echo "PATH='/path/to/jdk8/bin:$PATH'" >> ~/.bashrc
> echo "JAVA_HOME='/path/to/jdk8/'" >> ~/.bashrc
> source ~/.bashrc
> echo $PATH
...
> java -version
...
```
**macOS:** (possibly sudo)
```bash
> echo "PATH='/path/to/jdk8/bin:$PATH'" >> /etc/profile
> echo "JAVA_HOME='/path/to/jdk8/'" >> /etc/profile
> source /etc/profile
> echo $PATH
...
> java -version
...
```

#HSLIDE 
## JDK setup Windows
[только через настройки системы](https://docs.oracle.com/javase/tutorial/essential/environment/paths.html) :(  
Изменения подхватятся терминалом только после перезапуска терминала
```bat
> echo %PATH%
...
> java -version
...
```

#HSLIDE 
# Hello, World!
**HelloWorld.java**
```java
public class HelloWorld {
    public static void main(String[] args) { //entry point
        System.out.println("Hello, World!");
    }
}
```
1. ```public static void main(String[] args)``` is an entry point
2. All executable code must be inside **classes**
3. public class name **must** match file name
4. ```System.out.println("Hello, World!");``` - is a standard way to print to console
5. Every statement must end with **;**

#HSLIDE 
# compile and run

1. Compile program with **javac**
```bash
> javac HelloWorld.java
```

2. Run program with **java**
```bash
> java HelloWorld
Hello, World!
```

#HSLIDE
## Byte-code
Let's look inside HelloWorld.class
```bash
> javap -c HelloWorld.class
```
```
Compiled from "HelloWorld.java"
public class HelloWorld {
  public HelloWorld();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #3                  // String Hello, World!
       5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: return
}
```
#HSLIDE
## Java distribution
Multiple **.class** files are not handy for distribution
(what if our project is big and we want to use a number of libraries)
  
So they use java archives (**jar**) that contain all necessary class files and custom content

(Later in course)

#HSLIDE 
# 4. Git 
1. Course structure  
2. Language architecture  
3. Basic syntax  
4. **[Git]**  
5. Gradle  
6. Homework 1  

#HSLIDE 
# Git
Distributed **version control system**   
[Install git](https://git-scm.com/)

(We will just cover commands, necessary for our workflow  
To better understand **git** - get some course)

#HSLIDE
## Fork repository and set upstream
1. После форка в вашем github появится несинхронизованная копия (**fork**), **склонируем** ее и получим **рабочую копию** форка
```bash
> git clone https://github.com/YOUR_USERNAME/YOUR_FORK.git
```
2. Свяжем **рабочую копию вашего форка** с **репозиторием курса**, чтобы вы могли их синхронизировать и работать со свежей версией кода и проверим, что это сработало
```bash
> cd atom
> git remote add upstream https://github.com/rybalkinsd/atom.git
> git remote -v
origin  https://github.com/YOUR_USERNAME/YOUR_FORK.git (fetch)
origin  https://github.com/YOUR_USERNAME/YOUR_FORK.git (push)
upstream https://github.com/rybalkinsd/atom.git (fetch)
upstream https://github.com/rybalkinsd/atom.git (push)
```
Теперь ваш fork будет известен git-у как **origin** (по умолчанию)  
а репозиторий курса - как **upstream** (только что настроили)  

#HSLIDE
## Git branches
We will use git branches to communicate.  
we will do **homeworks** in branches (like **homeworkN**) and all the **class activity** in repository will be in branches (like **lectureN**)  

[[Guide on using branches by Atlassian]](https://www.atlassian.com/git/tutorials/using-branches)
<img src="lecture01/presentation/assets/img/branch.png" alt="me" style="width: 400px; float: left;"/>  

#HSLIDE
## Git practice
Now we have **course repository** known as **upstream**,  
**your fork**, known as **origin**  

We now practice to make changes in our **working copy** of **your fork**, then push them to **your fork** on github and **pull-request** them to **course repository**  

You will push only to **your fork** (**not** to **course repository**)  

[**fork local copy**] ==push==> [**fork repo**] ==pull request==> [**course repo**]


#HSLIDE
## Github workflow
1. **Fork** project **(only once)**
2. **clone** your fork
```bash
> git clone https://github.com/MY-GITHUB-NAME/atom
```
3. **checkout** branch which you want to contribute
```bash
> git fetch upstream
> git checkout -b lecture01 upstream/lecture01
```
4. make changes and **push** them to **your fork**
```bash
> git pull --rebase upstream lecture01
> git add MyFixedFile1.java MyFixedFile2.java
> git commit -m 'Fixed all bugs and added new'
> git push -u origin lecture01
```
5. make **pull-request**

#HSLIDE
## git branch commands
Посмотреть текущую ветку
```bash
> git branch
master
```
взять последние сведения о ветках из **вашего форка**
```bash
> git fetch origin
```
взять последние сведения о ветках из **репозитория курса**
```bash
> git fetch upstream
```
переключиться на ветку **lecture1**
```bash
> git checkout lecture1
```
Создать ветку **new-branch**
```bash
> git checkout -b new-branch
```

#HSLIDE
## git commit commands
посмотреть состояние **рабочей копии**
```bash
> git status
...
```
добавить файл к будущему коммиту (stage)
```bash
> git add changed_file
```
зафиксировать изменения в **локальном репозитории**
```bash
> git commit -m 'Сообщение с пояснением коммита'
```
послать изменения в **ваш форк** на github
```bash
> git push origin branch-to-push
```

#HSLIDE
## git update commands
взять новые изменения из **вашего форка**
```bash
> git pull --rebase origin master
```
взять новые изменения из **репозитория курса**
```bash
> git pull --rebase upstream master
```
**--rebase** заставляет **git** переносить ваши изменения поверх изменений других людей в этой ветке, которые они сделали, пока вы работали над этой веткой локально  
(возможны конфликты)


#HSLIDE 
# 5. Gradle 
1. Course structure  
2. Language architecture  
3. Basic syntax  
4. Git  
5. **[Gradle]**  
6. Homework 1  

#HSLIDE
# Gradle
https://gradle.org/  
**Gradle** - build automation system  

Like **maven** but more powerful  
do not need installation ([details](https://gradle.org/install)), just use:


**Windows:**
```bat
> gradlew.bat
```
**linux/macOS:**
```bash
> ./gradlew
```

#HSLIDE
## Why gradle?
- build/test/jar ... your project
- support custom build stages, configurable with **groovy**
- manage **dependencies** (automatacally download)
- manage project structure  
  
build configuration is contained in **build.gradle**  
gradle settings are defined in **gradle.settings**  

#HSLIDE
## Gradle workflow
https://guides.gradle.org/creating-java-applications/  
  
To build project from scratch, run tests and checkstyle:  
**linux/macOS:**
```
> ./gradlew clean build
```
**Windows:**
```
> gradlew.bat clean build
```

#HSLIDE
## Checkstyle
There exist verbose [styleguide](https://google.github.io/styleguide/javaguide.html) from google
  
To make it easier we added "checkstyle" to our **gradle** build
[https://docs.gradle.org/current/userguide/checkstyle_plugin.html](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)  
  
So your code will not build unless style is correct :)


#HSLIDE
# Travis  
Continuous Integration Tool  
[https://travis-ci.org/](https://travis-ci.org/)  

When you push to repository - Travis automatically runs gradle build on server  
It tracks all branches and pull requests  
[https://travis-ci.org/rybalkinsd/atom/pull_requests](https://travis-ci.org/rybalkinsd/atom/pull_requests)  

**check your pull requests there!**
**all tests must pass!**

#HSLIDE 
# 6. Homework 1 
1. Course structure  
2. Language architecture  
3. Basic syntax  
4. Git  
5. Gradle  
6. **[Homework 1]**  

#HSLIDE
# Homework 1
1. Fix tests in branch **homework1** and push it to **your fork**  
[[Github branch]](https://github.com/rybalkinsd/atom/tree/homework1)
[[Travis build]](https://travis-ci.org/rybalkinsd/atom/builds/204177834)
2. Make pull request to **course repository**
[https://github.com/rybalkinsd/atom](https://github.com/rybalkinsd/atom)
3. Make sure **tests** and **checkstyle** are passing in **Travis**  

**Deadline:** 1 March  
**Mark:** 5 points

#HSLIDE
## Литература
**Thinking in Java** (в русском переводе - философия Java)  
[https://www.amazon.com/Thinking-Java-4th-Bruce-Eckel/dp/0131872486](https://www.amazon.com/Thinking-Java-4th-Bruce-Eckel/dp/0131872486)  
  
Хорошая книга, придерживаться ее мы, конечно, не будем

#HSLIDE
## IDE
**Intellij IDEA**  
[https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)  
  
**Community edition** будет достаточно, но для студентов часто бесплатно предоставляют **professional edition**

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
