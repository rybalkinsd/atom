# Java
lecture 4
## Web client

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
1. Client - server architecture
1. HTTP
1. cURL
1. REST API
1. Java HTTP Client

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
1. Client - server architecture
1. HTTP
1. REST API
1. cURL
1. Java HTTP Client

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
1. **[Client - server architecture]**
1. HTTP
1. REST API
1. cURL
1. Java HTTP Client

---

### Agenda
1. First part summary
1. Bomberman project
1. Client - server architecture
1. **[HTTP]**
1. cURL
1. REST API
1. Java HTTP Client

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
For example, one can extend server functionality by custom logic (e.g. for dynamic content) - see next lecture

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
Date: Fri, 10 Mar 2017 16:21:07 GMT
Etag: "359670651+ident"
Expires: Fri, 17 Mar 2017 16:21:07 GMT
Last-Modified: Fri, 09 Aug 2013 23:54:35 GMT
Server: ECS (phl/9D60)
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
1. [Request body]

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
1. [Response Body]

[rfc2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)  
[wiki](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)

---

## HTTP via browser
When you enter adress line in browser, in creates **GET** request  
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
1. Client - server architecture
1. HTTP
1. **[cURL]**
1. REST API
1. Java HTTP Client

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
Cache-Control: max-age=604800
Content-Type: text/html
Date: Wed, 11 Oct 2017 14:17:54 GMT
Etag: "359670651+ident"
Expires: Wed, 18 Oct 2017 14:17:54 GMT
Last-Modified: Fri, 09 Aug 2013 23:54:35 GMT
Server: ECS (dca/24D5)
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
1. Client - server architecture
1. HTTP
1. cURL
1. **[REST API]**
1. Java HTTP Client

---

## REST
**REST** (Representational State Transfer) architecture style, where services cmmunicate over **HTTP**.  
There are also some restrictions on how services must use HTTP for communication

---

## Bomberman architecture
Here client and account server communicate via **REST API**
<img src="lecture04/presentation/assets/img/bomberman-architecture.png" alt="exception" style="width: 750px;"/>

---

## REST API
REST API is a common way for services to publish their functionality for other services.  
### REST API Examples:
**Twitter:** [https://dev.twitter.com/rest/public](https://dev.twitter.com/rest/public)  
**Github:** [https://developer.github.com/v3/](https://developer.github.com/v3/)

---

## HTTP Client Pracrice
We got a chat REST service open for you on  
  
Implement **chat client** and enjoy!  
@see **test.ru.atom.http.ChatClient** and **test.ru.atom.http.ChatClientTest**

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
        400 - Already logined
        400 - Too long name (longer than 30 symbols)
```
---

## Chat REST API. View chat
online:
```
    Protocol: HTTP
    Path: chat/chat
    Method: GET
    Host: {IP}:8080
    Response:
      Success code: 200
```
> implement it in test.ru.atom.http.HttpClient and check in test.ru.atom.http.HttpClientTest

---

## Chat REST API. Say
login:
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
        401 - Not logined
        400 - Too long message (longer than 140 symbols)
```

> implement it in test.ru.atom.http.HttpClient and check in test.ru.atom.http.HttpClientTest

---

### Agenda
1. First part summary
1. Bomberman project
1. Client - server architecture
1. HTTP
1. cURL
1. REST API
1. **[Java HTTP Client]**

---

## OkHTTP
We use OkHTTP library as java HTTP Client
[http://square.github.io/okhttp/](http://square.github.io/okhttp/)
### @see ru.atom.http.client

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

### Summary
1. **HTTP** is popular client-server protocol for inter-program communication  
Learn it!

---

**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
