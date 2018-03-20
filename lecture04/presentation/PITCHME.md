# Java
lecture 4
## Web basics

---

## Отметьтесь на портале
https://sphere.mail.ru/

---

### get ready
[https://github.com/rybalkinsd/atom](https://github.com/rybalkinsd/atom)
```bash
> git fetch upstream
> git checkout -b lecture04 upstream/lecture04
> cd lecture04
```

---

### Agenda
1. **[First part summary]**
1. Bomberman project
1. HTTP
1. cURL
1. REST API
1. Java HTTP Client (okhttp)
1. Java HTTP Server (with Spring)

---

## What we already know
Know how to write and build stand-alone single-threaded app
- git
- gradle
- java collections, exceptions, input/output  

Make sure you did [homework2](https://gitpitch.com/rybalkinsd/atom/lecture03?grs=github&t=white&p=lecture03%2Fpresentation#/74) to know this

---

## Quiz
0. equals() vs ==
0. Exceptions 
0. How ArrayList work/operations complexity?
0. How LinkedList work/operations complexity?
0. How HashMap work/operations complexity?
0. How HashSet work/operations complexity?
0. equals() hashCode() contract

---

### Agenda
1. First part summary
1. **[Bomberman project]**
1. HTTP
1. REST API
1. cURL
1. Java HTTP Client (okhttp)
1. Java HTTP Server (with Spring)

---

## Project - server for multi-player game
**Original project by Matt Scala:**  
https://github.com/MattSkala/html5-bombergirl  
http://bombergirl.matousskala.cz/  
please turn off the sound :)
  
**Adopted version for our project:**
https://github.com/rybalkinsd/atom/tree/master/bomberman/frontend

---

## Architecture overview
---?image=lecture04/presentation/assets/img/Bomberman-arch.svg&size=auto 70% 

---

## Bomberman stack
Bomberman is actually a **web application** with client-server architecture
- Front-end is HTML5 + js (is ready)
- Back-end is java

---

### Agenda
1. First part summary
1. Bomberman project
1. **[HTTP]**
1. cURL
1. REST API
1. Java HTTP Client (okhttp)
1. Java HTTP Server (with Spring)

---

## OSI model
---?image=lecture04/presentation/assets/img/osi2.png&size=auto 70% 

---

## HTTP - Application layer client-server protocol
---?image=lecture04/presentation/assets/img/HTTP.png&size=auto 70% 

---

## HTTP Basics
[https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html](https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html)
- **Resource** - any entity
- **URI** - (Universal Resource Identifiers)
- **Method** - what to do with **resource**

---

## HTTP Server
Aka **Web Server**.
Serves HTTP requests. (By default on **80 TCP port**)
- Apache
- NGINX
- embedded (as a library) - Jetty/Embedded Tomcat

Web servers have different functionality and can be extensible  
  
We can extend server functionality by custom logic and respond with dynamic content  
That's what we use to implement game

---

## HTTP Client
- web browser
- cURL
- libraries (e.g. **libcurl**)
- telnet

---

## HTTP via telnet
```bash
> telnet example.org 80
```
request:
```http
GET /index.html HTTP/1.1
host: example.com
```
response:
```
HTTP/1.1 200 OK
Cache-Control: max-age=604800
Content-Type: text/html
Date: Wed, 14 Mar 2018 11:27:16 GMT
Etag: "1541025663+ident"
Expires: Wed, 21 Mar 2018 11:27:16 GMT
Last-Modified: Fri, 09 Aug 2013 23:54:35 GMT
Server: ECS (dca/53DB)
Vary: Accept-Encoding
X-Cache: HIT
Content-Length: 1270

<!doctype html>
<html>
    <head>
    ...
```

---

## HTTP Request
**Request consists of**
1. Request header (starting with **method**)
1. Request body

**Methods:**
- **GET**
Request resource. **GET** must not change resource
- **POST**
Creates new resource
- **PUT**
Changes resource
- **DELETE**
removes resource
- ...

---

## HTTP Response
**Response consists of**
1. Status code
1. Response header
1. Response Body

[rfc2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)  
[wiki](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)

---

## HTTP via browser
When you enter address line in browser, in creates **GET** request  
So we can do previous example just by typing in browser:
> example.org

---

### When to use HTTP for inter-program communication?
**When we want:**
- simplicity (easy to develop and test)
- scalability (just use load balancer, i.e. **nginx**)

**under restrictions:**
- mediocre performance (not real time)
- client-server only - no push requests from server

---

### Agenda
1. First part summary
1. Bomberman project
1. HTTP
1. **[cURL]**
1. REST API
1. Java HTTP Client (okhttp)
1. Java HTTP Server (with Spring)

---

## cURL
Super popular command line tool for multiple protocols testing (including **HTTP**)   
[https://curl.haxx.se/](https://curl.haxx.se/)  
it wraps **libcurl** library, which is available for all major languages

---

## GET Example
Request from cURL:
```bash
> curl -i -X GET example.org
```
Response:
```http
HTTP/1.1 200 OK
Accept-Ranges: bytes
Cache-Control: max-age=604800
Content-Type: text/html
Date: Wed, 14 Mar 2018 11:30:27 GMT
Etag: "1541025663"
Expires: Wed, 21 Mar 2018 11:30:27 GMT
Last-Modified: Fri, 09 Aug 2013 23:54:35 GMT
Server: ECS (dca/24A0)
Vary: Accept-Encoding
X-Cache: HIT
Content-Length: 1270

<!doctype html>
<html>
  <head>
    ...
```

---

## POST Example
Raw HTTP:
```http
POST /chat/say HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Host: localhost:8080

msg=Hi everyone in this chat!
```
cURL:
```bash
> curl -X POST \
-d 'msg=Hi everyone in this chat!' \
http://localhost:8080/chat/say?name=MY_NAME
```
response:
```http
HTTP/1.1 200 OK
Date: Wed, 11 Oct 2017 14:17:11 GMT
Content-Length: 0
Server: Jetty(9.4.z-SNAPSHOT)

```

---

### Agenda
1. First part summary
1. Bomberman project
1. HTTP
1. cURL
1. **[REST API]**
1. Java HTTP Client (okhttp)
1. Java HTTP Server (with Spring)

---

## REST
**REST** (Representational State Transfer) architecture style, where services communicate over **HTTP**.  
There are also some restrictions on how services must use HTTP for communication

---

## REST API
REST API is a common way for services to publish their functionality for other services.  
### REST API Examples:
**Twitter:** [https://dev.twitter.com/rest/public](https://dev.twitter.com/rest/public)  
**Github:** [https://developer.github.com/v3/](https://developer.github.com/v3/)

---

## Chat REST API
We got a chat REST service open for you on **54.224.37.210**  
  
Further you have description of it's REST API

---

## Chat REST API. View Online
online:
```
    Protocol: HTTP
    Path: chat/online
    Method: GET
    Host: {IP}:8080
    Response:
      Success code: 200
```

---

## Chat REST API. Login
login:
```
    Protocol: HTTP
    Path: chat/login
    Method: POST
    QueryParam: name
    Host: {IP}:8080
    Response:
      Success code: 200
      Fail code:
        400 - Already logged in
        400 - Too long name (longer than 30 symbols)
```
---

## Chat REST API. View chat
chat:
```
    Protocol: HTTP
    Path: chat/chat
    Method: GET
    Host: {IP}:8080
    Response:
      Success code: 200
```

---

## Chat REST API. Say
say:
```
    Protocol: HTTP
    Path: chat/say
    Method: POST
    QueryParam: name
    Body:
      msg="my message"
    Host: {IP}:8080
    Response:
      Success code: 200
      Fail code:
        400 - User not online
        400 - Too long message (longer than 140 symbols)
```


---

### Agenda
1. First part summary
1. Bomberman project
1. HTTP
1. cURL
1. REST API
1. **[Java HTTP Client (okhttp)]**
1. Java HTTP Server (with Spring)

---

## OkHTTP
We use OkHTTP library as java HTTP Client
[http://square.github.io/okhttp/](http://square.github.io/okhttp/)
### @see ru.atom.chat.client

---

## GET example from Java
```java
  //GET host:port/chat/online
  public static Response viewOnline() throws IOException {
    Request request = new Request.Builder()
        .get()
        .url(PROTOCOL + HOST + PORT + "/chat/online")
        .addHeader("host", HOST + PORT)
        .build();

    return client.newCall(request).execute();
  }
```
---

## POST example from Java
```java
  //POST host:port/chat/login?name=my_name
  public static Response login(String name) throws IOException {
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    Request request = new Request.Builder()
        .post(RequestBody.create(mediaType, ""))
        .url(PROTOCOL + HOST + PORT + "/chat/login?name=" + name)
        .build();

    return client.newCall(request).execute();
  }
```

---

## HTTP Client Practice
Implement **chat client** for chat on 54.224.37.210 and login under your **family name**!  
@see **ru.atom.chat.client.ChatClient**  
@see **test.ru.atom.chat.client.ChatClientTest**
  
Mark: 2 points

---

### Agenda
1. First part summary
1. Bomberman project
1. HTTP
1. cURL
1. REST API
1. Java HTTP Client (okhttp)
1. **[Java HTTP Server (with Spring)]**

---

### Spring
<img src="lecture04/presentation/assets/img/spring-by-pivotal.png" alt="exception" style="width: 300px;"/>  
is a universal open-source framework, used to develop web applications  
https://spring.io/  
  
First version - **2002**

---

### Spring modules
It includes a number of modules for different functionality:
- Spring MVC for building Web Applications
- Working with Databases
- Messaging
- RPC
- Security
- Testing
  
Today we will build web application with **Spring MVC** module

---

### MVC
**MVC (Model-View-Controller)** - popular pattern used to build web apps
<img src="lecture04/presentation/assets/img/MVC-Introduction2.jpg" style="width: 600px;"/>


---

### Spring MVC
**Spring MVC** - Spring Module that make it easier to build MVC Applications (Like **Django**, **Rails**)
<img src="lecture04/presentation/assets/img/spring_mvc.png" alt="exception" style="width: 600px;"/>


---

### Spring Boot
Spring is a powerful tool and has a lot of configuration options.  
**Spring Boot** is a project, that makes working with Spring easier:
- embedded tomcat included with servlet container
- minimum configuration, sane defaults
- metrics, health checks and externalized configuration
https://projects.spring.io/spring-boot/  
  
First version: **2014**
  
**With Spring Boot our life is much easier :)**

---

## Example: Chat server with Spring Boot
Look at **ru.atom.chat.server** implementation of chat server
- ChatApplication - starter
- ChatController - handler of HTTP requests
  
All the magic behind **Spring** 'comes via' **Annotations**  
**We have deeper description of Spring in further lectures**

---

## HTTP Server Practice
Implement **chat server** for given REST API and make pull request!  
@see **ru.atom.chat.client.ChatController**  
  
Mark: 3 points

---

### Summary
0. Know java collections/exception
0. **HTTP** is popular client-server protocol for inter-program communication  
0. We use Spring MVC to develop web application (such as chat)

---

**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
