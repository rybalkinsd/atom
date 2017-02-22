#HSLIDE
# Java
lecture 1
## Basics

#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/

#HSLIDE
# About me
<img src="lecture01/presentation/assets/img/me.jpg" alt="me" style="width: 220px; float: left;"/>  

  alpieex@gmail.com  
  [https://github.com/Al-p-i](https://github.com/Al-p-i)  

  Java 5+ years

- Former software developer in Allods Team (Skyforge)
- Currently lead developer at hh.ru

**Люблю смотреть, как другие пишут тесты**  

#HSLIDE
# Цель курса
Практическое введение в разработку серверных web приложений на Java
Практическая часть - разработка сервера для мультиплеерной игры

**Мы научимся:**
- разрабатывать
- тестировать
- деплоить  
  
#HSLIDE
# Ключевые технологии
**Java SE 8.0**  
**Jersey** - web framework + **jetty** webserver  
**Hibernate** - общение с базами данных  
**WebSocket** - сетевое взаимодействие  
**Инструменты:** Git, Gradle, Docker

#HSLIDE
## Ссылки

**Страничка курса**  
[http://gojava.ru](http://gojava.ru) (currently redirects on github)  
[https://github.com/rybalkinsd/atom](https://github.com/rybalkinsd/atom)
  
**Блог в техноатоме**  
[https://atom.mail.ru/blog/view/99/](https://atom.mail.ru/blog/view/99/)
  
**!Чатик в Telegram!**  
[https://t.me/joinchat/AAAAAEF63F9PvqE4JDzYdQ](https://t.me/joinchat/AAAAAEF63F9PvqE4JDzYdQ)  

**JDK8**  
[http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  
  
**Официальная документация Java**  
[http://docs.oracle.com/javase/8/docs/api/](http://docs.oracle.com/javase/8/docs/api/)

#HSLIDE
# План курса
12 лекций/семинаров + 3 рубежных контроля + защита проектов
1. **Intro** - познаем основы языка, знакомимся с инструментарием
2. **Java WEB** - учимся писать web-сервисы
3. **Persisnence** - работаем с базами данных
4. **Client-server interaction** - общаемся по сети
5. **Game mechanics** - строим игру
6. **Final project** - защищаем групповой проект

#HSLIDE
# Оценка
### Структура оценки:
- 3 Рубежных контроля = 16 + 12 + 12  
- Домашние работы и тесты на занятиях = 30  
- Сдача итогового проекта = 30  
- \+ есть возможность получать дополнительные баллы за задания, которые мы даем прямо на семинарах  

**Сертификат:**  
нужно набрать 50 баллов и защитить проект

**Оценки:**  
**3**: 50-69  
**4**: 70-90  
**5**: 90+  

#HSLIDE
# Проект
На протяжении всего курса мы будем разрабатывать **проект** - сервер для клона **bomberman**  
[https://github.com/MattSkala/html5-bombergirl](https://github.com/MattSkala/html5-bombergirl)  
  
Проект имеет сервисную клиент-серверную архитектуру  
  
Мы подготовили для вас готовый клиент на javascript  
Группы будут сформированы после первого рубежного контроля  

**Защита проекта - обязательный критерий получения сертификата**

#HSLIDE
### Вопросы по организации курса?

#HSLIDE
# Agenda
1. Course structure  
2. Language architecture  
3. Basic syntax
4. Git    
5. Gradle  
6. Homework 1  

#HSLIDE 
# 2. Language architecture 
1. Course structure  
2. **[Language architecture]**  
3. Basic syntax
4. Git    
5. Gradle  	
6. Homework 1  

#HSLIDE
# Java domain
### Java dominates areas:
- Back-end for enterprise-scale solutions
- Android (specific area)  


### Where Java mostly does not work?
- Low level high performance software
- soft for specific hardware
- scripting

#HSLIDE
## Why Java?
- Java is **mature** (20+ years) => stable and conservative
- Java has active community (in Russia too)
- Java has rich choice of libraries and frameworks
- *Java is fast*  
- Java market is not saturated, **$$**

#HSLIDE
## Common Java facts
- Java is **crossplatform** - 'Write Once, Run Anywhere' **(WORA)**
- Java is compiled to **Byte Code** (not to machine codes), which is executed by **Java Virtual Machine (JVM)**
- automatic memory management **(GC)**
- Java is **object-oriented**, **class-based**
- static strong safe typisation
- concurrent

#HSLIDE 
# 3. Basic syntax 
1. Course structure  
2. Language architecture  
3. **[Basic syntax]**    
4. Git  
5. Gradle
6. Homework 1  

#HSLIDE 
# Basic types
| Type          | Size          | Range             |
| ------------- |:-------------:| -----------------:|
| boolean       | undefined     | true/false        |
| byte          | 1 byte        | -128-127          |
| char          | 2 bytes       | \u0000-\uffff     |
| short         | 2 bytes       | -32768 - 32767    |
| int           | 4 bytes       | -2^31 - (2^31)-1  |
| long          | 8 bytes       | -2^63 - (2^63)-1  |
| float         | 4 bytes       | IEEE 754          |
| double        | 8 bytes       | IEEE 754          |
| **reference** | system bitness|                   |

#HSLIDE 
# Operators
|Operator type  |Operator                   |
|---------------|---------------------------|
|Assignment     | =, +=, *= …^=             |
|Arithmetical   | +, -, *, /, %             |
|Relational     | <, >, <=, >=, ==, !=      |
|Logical        | &&, &#124;&#124;          |
|Bitwise        | &, &#124;, ^, >>, <<, >>> |
|Unary          | ++, --, +, -, !           |
|Relational2    | instanceof                |


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
if (commonVarialbe > -42) { // ← начало блока
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
    System.out.println(“Digit: “ + digit);
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
Install git  
[https://git-scm.com/](https://git-scm.com/)

How it is different from **svn**?

(We will just cover basic commands  
To better understand git - get some course)

#HSLIDE
## Fork repository and set upstream
1. После форка в вашем github появится копия репозитория
```bash
> git clone https://github.com/YOUR_USERNAME/YOUR_FORK.git
```
если вы его уже склонировали раньше, то обновите
```bash
> git pull --rebase
```
2. Настроим синхронизацию репозитория, чтобы вы работали со свежей версией кода
```bash
> cd atom
> git remote add upstream https://github.com/rybalkinsd/atom.git
```
3. Проверим, что синхронизация включена
```bash
> git remote -v
origin  https://github.com/YOUR_USERNAME/YOUR_FORK.git (fetch)
origin  https://github.com/YOUR_USERNAME/YOUR_FORK.git (push)
upstream https://github.com/rybalkinsd/atom.git (fetch)
upstream https://github.com/rybalkinsd/atom.git (push)
```

#HSLIDE
## Git branches
[[Guide on using branches by Atlassian]](https://www.atlassian.com/git/tutorials/using-branches)
<img src="lecture01/presentation/assets/img/branch.png" alt="me" style="width: 500px; float: left;"/>  

#HSLIDE
## git branch commands
Посмотреть текущую ветку
```bash
> git branch
master
```
взять последние сведени о ветках из удаленного репозитория
```bash
> git fetch origin
```
взять последние сведени о ветках из форкнутого репозитория
```bash
> git fetch upstream
```
выбрать ветку lecture1
```bash
> git checkout lecture1
```
Создать ветку new-branch
```bash
> git checkout -b new-branch
```

#HSLIDE
## git commit commands
посмотреть состояние рабочей копии
```bash
> git status
...
```
добавить файл к будущему коммиту (stage)
```bash
> git add changed_file
```
зафиксировать изменения в локальном репозитории
```bash
> git commit -m 'Сообщение с пояснением коммита'
```
послать изменения в удаленный (публичный) репозиторий
```bash
> git push
```

#HSLIDE
## git update commands
взять новые изменения из удаленного репозитория
```bash
> git pull origin master --rebase
```
взять новые изменения из репозитория, который вы отфоркали
```bash
> git pull upstream master --rebase
```
**--rebase** заставляет git переносить ваши изменения поверх новых  
(возможны конфликты)

#HSLIDE
## Git practice
We not practice to make changes in our fork and **pull-request** them to **course repository**  

#HSLIDE
## Github workflow
1. **Fork** project
2. **clone** forked project
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
> git pull upstream lecture01 --rebase
> git add MyFixedFile1.java MyFixedFile2.java
> git commit -m 'Fixed all bugs and added new'
> git push
```
5. make **pull-request**


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
- manage dependencies (automatacally download)
- manage dependencies between projects  
  
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
1. Fix tests in branch **homework1**
[Github branch](https://github.com/rybalkinsd/atom/tree/homework1)
[Travis build](https://travis-ci.org/rybalkinsd/atom/builds/204177834)
2. Make pull request to course repository
[https://github.com/rybalkinsd/atom](https://github.com/rybalkinsd/atom)
3. Make sure tests are passing in travis  

**Deadline:** 1 March
**Mark:** **5 points**

#HSLIDE
## Литература
**Thinking in Java** (в русском переводе - философия Java)  
[https://www.amazon.com/Thinking-Java-4th-Bruce-Eckel/dp/0131872486](https://www.amazon.com/Thinking-Java-4th-Bruce-Eckel/dp/0131872486)  
  
Хорошая книга, придерживаться ее мы, конечно не будем

#HSLIDE
## IDE
**Intellij IDEA**  
[https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)  
  
**Community edition** будет достаточно, но для студентов часто бесплатно предоставляют **professional edition**

#HSLIDE
## Ура! Праздники!
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**