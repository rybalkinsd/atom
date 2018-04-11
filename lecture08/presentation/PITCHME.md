---
# Java
lecture 8
## WebSocket

---
## Отметьтесь на портале
https://sphere.mail.ru/

---
### get ready
https://github.com/rybalkinsd/atom
```bash
> git fetch upstream
> git checkout -b lecture08 upstream/lecture08
> cd lecture08
```
Refresh gradle project

---
## Agenda
1. WebSocket
1. Practice

---
## Agenda
1. **[WebSocket]**
1. Practice

---

## Architecture overview

---?image=lecture08/presentation/assets/img/Bomberman-arch.svg&size=auto 70% 


---
## Client-server communication
- Why is HTTP **bad** for client-server communication in real-time games?  
- What alternatives do we have?  

---
## WebSocket
- client-server
- full-duplex
- application-layer
- over single TCP connection
- protocol (**ws://** **wss://**)

Standartized in 2011 [https://tools.ietf.org/html/rfc6455](https://tools.ietf.org/html/rfc6455)  
Can be used for any extensive client-server communication  
Supported by most modern browsers

---
## WebSocket
### Client-server
client is the one who initializes session

### Full-duplex
exchange data in both directions

---
## OSI
<img src="lecture08/presentation/assets/img/osi2.png" alt="exception" style="width: 750px;"/>  

---
## WebSocket is application layer
<img src="lecture08/presentation/assets/img/osi.png" alt="exception" style="width: 750px;"/>  

---
## WebSocket
### Application layer
WebSocket works over TCP/IP (typically).  
WebSocket is initialized via **HTTP UPGRADE** to websocket

### Over single TCP connection
Single TCP connection is used for communication.
Then no handshakes or headers required. **Only raw data**

---
## WebSocket
<img src="lecture08/presentation/assets/img/websocket.png" alt="exception" style="width: 750px;"/>  

---
## WebSocket library
There are a number of **websocket** implementations. We will use spring boot **websocket implementation**  
> **@see** build.gradle

---
## Websocket library interface
All communication happen via **WebSocketSession** interface

---
## Notes
- **websocket** allow string data as well as binary data interchange
- **websocket** declares max message length (configurable)
- **websocket** session auto-closes when inactive (configurable)

---
## sniff websocket traffic with tcpdump
[http://www.tcpdump.org/](http://www.tcpdump.org/)  
tcpdump - standard unix tool to fo traffic analysis. It can inspect **websocket** too
```bash
> tcpdump -Aq -s0 -i lo0 'tcp port 8090'
```

---
## Another nice tools
**tcpflow**  
[tcpflow on github](https://github.com/simsong/tcpflow)  
**wireshark**  
[home page](https://www.wireshark.org/)

---
## WebSocket example
> **@see** lecture08/ru.atom.lecture08.websocket/

Of course, we can also send structured data (like **JSON**)

---
## Agenda
1. WebSocket
1. **[Practice]**

---
## Send simple message to wtfis.ru:8090
- Our EventClient already can say 'Hello'  
- In real world (and in game) want to send structured data (like JSON)
- our game will send all data as JSON. Lets emulate this feature

---
## Task 1
Look as Message class. It represents structured data and it can be converted to JSON
> Implement sending **Message** with topic **Hello** and your name as **data**
> Server adress: wtfis.ru:8090/events

---
## What we can do now
Ok, now we can send structured messages via **websocket** with arbitrary **data** and defined **type**


---
## Task 2
Implement chat with WebSocket

---
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
