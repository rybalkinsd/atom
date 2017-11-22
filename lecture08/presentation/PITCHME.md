#HSLIDE
# Java
lecture 8
## WebSocket. The Game

#HSLIDE
## Отметьтесь на портале
https://atom.mail.ru/

#HSLIDE
### get ready
```bash
> git fetch upstream
> git checkout -b lecture08 upstream/lecture08
```
Refresh gradle project

#HSLIDE
## Agenda
1. Game architecture
1. WebSocket
1. Practice

#HSLIDE
## Agenda
1. **[Game architecture]**
1. WebSocket
1. Practice

#HSLIDE
### Game architecture
1. Logic - java
1. Render - js Canvas
1. Input - js onkeydown, onmouse*
1. Connection - webSocket
1. Assets - png, mp3, ...


#HSLIDE
### Logic
<img src="lecture08/presentation/assets/img/gameloop.png" alt="exception" style="width: 600px;"/>


#HSLIDE
### Logic
1. Many threads - get user input
1. One thread - Game mechanics
1. Many threads broadcast replica


#HSLIDE
### Services
<img src="lecture08/presentation/assets/img/Game-architecture.png" alt="exception" style="width: 600px;"/>


#HSLIDE
### JS components
1. bootstrap - common purpose [link](http://getbootstrap.com/)
1. PreloadJS - assets upload [link](http://www.createjs.com/preloadjs)
1. EaselJS - canvas operating [link](http://www.createjs.com/easeljs)
1. SoundJS - sound operation [link](http://www.createjs.com/soundjs)


#HSLIDE
### Canvas
<img src="lecture08/presentation/assets/img/canvas.png" alt="exception" style="width: 600px;"/> 

 
#HSLIDE
### Front instances
- Player
- Bomb
- Fire
- Tile

#HSLIDE
### Front infrastructure
- core and Entity - an approach to be OOP
- GameEngine - basic mechanics and render
- Input engine - input handling 

#HSLIDE
## Agenda
1. Game architecture
1. **[WebSocket]**
1. Practice

#HSLIDE
## Client-server communication
- Why is HTTP **bad** for client-server communication in real-time games?  
- What alternatives do we have?  

#HSLIDE
## WebSocket
- client-server
- full-duplex
- application-layer
- over single TCP connection
- protocol (**ws://** **wss://**)

Standartized in 2011 [https://tools.ietf.org/html/rfc6455](https://tools.ietf.org/html/rfc6455)  
Can be used for any extensive client-server communication  
Supported by most modern browsers

#HSLIDE
## WebSocket
### Client-server
client is the one who initializes session

### Full-duplex
exchange data in both directions

#HSLIDE
## OSI
<img src="lecture08/presentation/assets/img/osi2.png" alt="exception" style="width: 750px;"/>  

#HSLIDE
## WebSocket is application layer
<img src="lecture08/presentation/assets/img/osi.png" alt="exception" style="width: 750px;"/>  

#HSLIDE
## WebSocket
### Application layer
WebSocket works over TCP/IP (typically).  
WebSocket is initialized via **HTTP UPGRADE** to websocket

### Over single TCP connection
Single TCP connection is used for communication.
Then no handshakes or headers required. **Only raw data**

#HSLIDE
## WebSocket
<img src="lecture08/presentation/assets/img/websocket.png" alt="exception" style="width: 750px;"/>  

#HSLIDE
## WebSocket library
There are a number of **websocket** implementations. We will use spring boot **websocket implementation**  
> **@see** build.gradle

#HSLIDE
## Websocket library interface
All communication happen via **WebSocketSession** interface

#HSLIDE
## Notes
- **websocket** allow string data as well as binary data interchange
- **websocket** declares max message length (configurable)
- **websocket** session auto-closes when inactive (configurable)

#HSLIDE
## sniff websocket traffic with tcpdump
[http://www.tcpdump.org/](http://www.tcpdump.org/)  
tcpdump - standard unix tool to fo traffic analysis. It can inspect **websocket** too
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
## WebSocket example
> **@see** lecture08/ru.atom.lecture08.websocket/

Of course, we can also send structured data (like **JSON**)

#HSLIDE
## Agenda
1. Game architecture
1. WebSocket
1. **[Practice]**

#HSLIDE
## Send simple message to wtfis.ru:8090
- Our EventClient already can say 'Hello'  
- In real world (and in game) want to send structured data (like JSON)
- our game will send all data as JSON. Lets emulate this feature

#HSLIDE
## Task 1
Look as Message class. It represents structured data and it can be converted to JSON
> Implement sending **Message** with topic **Hello** and your name as **data**
> Server adress: wtfis.ru:8090/events

#HSLIDE
## What we can do now
Ok, now we can send structured messages via **websocket** with arbitrary **data** and defined **type**

#HSLIDE
## Task 2. Implement MOVE and PLANT_BOMB messages processing
### What we get
1. Game frontend is able to communicate via websocket with server
1. Frontend connects to 8090 port
1. Frontend sends commands **MOVE** and **PLANT_BOMB**

#HSLIDE
## MOVE
client -> server
```json
{
  "topic":"MOVE",
  "data":
    {
      "direction":"UP"
    }
}
```
direction values: UP/DOWN/RIGHT/LEFT

#HSLIDE
## PLANT_BOMB
client -> server
```json
{
   "topic": "PLANT_BOMB",
   "data": {}
}
```

#HSLIDE
## Networking task
**Broker** is able to send and receive messages.
1. connect Broker.receive() to **Handler**
1. Log receiving **MOVE** and **PLANT_BOMB** messages, fail if other message is sent. Here later you will add processing logic

#HSLIDE
## What is next?
At home you will merge your code with game model (from HW2) and networking from this lecture and implement logic for movement and bomb planting  

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
