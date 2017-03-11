#HSLIDE
# Java
lecture 4
## Web client


#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/


#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b lecture04 upstream/lecture04
```

#HSLIDE
### Agenda
1. **[Collections]**
1. Client - server architecture
1. HTTP
1. cURL
1. REST API
1. Java HTTP Client

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
### TreeMap
The keys are ordered using their **Comparable** natural 
ordering, or by **Comparator** provided at set creation time, 
depending on which constructor is used.


#HSLIDE
### TreeMap. Internals
<img src="lecture03/presentation/assets/img/treeset.png" alt="exception" style="width: 500px;"/>

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
### TreeMap. Complexity

|  contains  | add   | get   | remove | 
|:----------:|:-----:|:-----:|:------:|
| O(log(n))       | O(log(n))  |  O(log(n)) | O(log(n))  |

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
Set interface implementation, backed by a **HashMap** (with set elements as keys and dummy Object)
It makes no guarantees as to the iteration order of the set.
 
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
Set interface implementation, backed by a **TreeMap** (with set elements as keys and dummy Object)
Complexity is similar to **TreeMap**

#HSLIDE
### Agenda
1. Collections
1. **[Client - server architecture]**
1. HTTP
1. REST API
1. cURL

1. Java HTTP Client

#HSLIDE
## Client - server architecture
<img src="lecture04/presentation/assets/img/Client-server-model.png" alt="exception" style="width: 600px;"/>  
Which protocol to use for client-server inreraction?

#HSLIDE
## Network communication
There exist numerous protocols for network communication. OSI:
<img src="lecture04/presentation/assets/img/osi2.png" alt="exception" style="width: 700px;"/>

#HSLIDE
The choice of protocol depends on **requirements**

#HSLIDE
## Bomberman architecture
<img src="lecture04/presentation/assets/img/bomberman-architecture.png" alt="exception" style="width: 750px;"/>

#HSLIDE
### Agenda
1. Collections
1. Client - server architecture
1. **[HTTP]**
1. cURL
1. REST API
1. Java HTTP Client

#HSLIDE
## HTTP
**Application layer client-server protocol**

<img src="lecture04/presentation/assets/img/HTTP.png" alt="exception" style="width: 750px;"/>

#HSLIDE
## HTTP Basics
[https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html](https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html)
- **Resource** - any entity
- **URI** - (Universal Resource Identifiers)
- **Method** - what to do with **resource**

#HSLIDE
## HTTP Server
Aka **Web Server**.
Serves HTTP requests. (By default on **80 TCP port**)
- Apache
- NGINX
- libraries (Jetty in Java)

Web servers have different functionality and can be extendible  
For example, one can extend server functionality by custom logic (e.g. for dynamic content) - see next lecture

#HSLIDE
## HTTP Client
- web browser
- cURL
- libraries (e.g. **libcurl**)
- telnet

#HSLIDE
## HTTP via telnet
```bash
> telnet example.org
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

#HSLIDE
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

#HSLIDE
## HTTP Response
**Responce consists of**
1. Status code
1. Response header
1. [Response Body]

[rfc2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)  
[wiki](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)

#HSLIDE
## When use HTTP for inter-program communication?
**When we want:**
- simplicity (easy to develop and test)
- scalability (just use load balancer, i.e. **nginx**)

**under restrictions:**
- mediocre performance (not real time)
- client-server only - no push requests from server

#HSLIDE
### Agenda
1. Collections
1. Client - server architecture
1. HTTP
1. **[cURL]**
1. REST API
1. Java HTTP Client

#HSLIDE
## cURL
Super popular command line tool for multiple protocols testing (including **HTTP**)   
[https://curl.haxx.se/](https://curl.haxx.se/)  
it wraps **libcurl** library, which is available for all major languages

#HSLIDE
## GET Example
Request:
```bash
> curl -i -X GET -H "Host: example.org" example.org
```
Response:
```http
HTTP/1.1 200 OK
Cache-Control: max-age=604800
Content-Type: text/html
Date: Sat, 11 Mar 2017 00:22:28 GMT
Etag: "359670651+ident"
Expires: Sat, 18 Mar 2017 00:22:28 GMT
Last-Modified: Fri, 09 Aug 2013 23:54:35 GMT
Server: ECS (phl/9D2C)
Vary: Accept-Encoding
X-Cache: HIT
Content-Length: 1270

<!doctype html>
<html>
<head>
```


#HSLIDE
## POST Example
Raw HTTP:
```http
POST /auth/login
HTTP/1.1
Content-Type: application/x-www-form-urlencoded Host: localhost:8080

login=user&password=password
```
cURL:
```bash
>curl -X POST \
-H "Content-Type: application/x-www-form-urlencoded" \
-H "Host: localhost:8080" \
-d 'login=admin&password=admin' \
http://localhost:8080/auth/login
```

#HSLIDE
### Agenda
1. Collections
1. Client - server architecture
1. HTTP
1. cURL
1. **[REST API]**
1. Java HTTP Client

#HSLIDE
REST (Representational State Transfer) architecture style, where services cmmunicate over **HTTP**.  
There are also some restrictions on how services must use HTTP for communication

#HSLIDE
## Bomberman architecture
Here client and account server communicate via **REST API**
<img src="lecture04/presentation/assets/img/bomberman-architecture.png" alt="exception" style="width: 750px;"/>

#HSLIDE
## REST API
REST API is a common way for services to publish their functionality for other services.  
### REST API Examples:
**Twitter:** [https://dev.twitter.com/rest/public](https://dev.twitter.com/rest/public)
**Github:** [https://developer.github.com/v3/](https://developer.github.com/v3/)

#HSLIDE
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

#HSLIDE
## Chat REST API. Login
login:
```
    Protocol: HTTP
    Path: chat/login
    Method: POST
    PathParam: name
    Host: {IP}:8080
    Response:
      Success code: 200
      Fail code:
        400 - Already logined
        400 - Too long name (longer than 30 symbols)
```
#HSLIDE
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
> implement it in test.ru.atom.http.HttpClient and check in test.ru.atom.http.HttpClientTest

#HSLIDE
## Chat REST API. Say
login:
```
    Protocol: HTTP
    Path: chat/say
    Method: POST
    PathParam: name
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

#HSLIDE
### Agenda
1. Collections
1. Client - server architecture
1. HTTP
1. cURL
1. REST API
1. **[Java HTTP Client]**

#HSLIDE
## OkHTTP
We use OkHTTP library as java HTTP Client
[http://square.github.io/okhttp/](http://square.github.io/okhttp/)
###@see ru.atom.http.client

#HSLIDE
```java
OkHttpClient client = new OkHttpClient();
MediaType mediaType = MediaType.parse("application/x-www-form-
urlencoded"); RequestBody body = RequestBody.create(mediaType,
"login=admin&password=admin"); Request request = new Request.Builder()
.url("http://localhost:8080/auth/login")
.post(body)
.addHeader("content-type", "application/x-www-form-urlencoded") .addHeader("host", "localhost:8080")
.build();
Response response = client.newCall(request).execute();
```

#HSLIDE
### Summary
1. Sets contain unique values
1. Maps contain pairs with unique keys
1. HTTP is popular client-server protocol for inter-program communication

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
