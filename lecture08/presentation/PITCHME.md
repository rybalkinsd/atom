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
<img src="lecture08/presentation/assets/img/services.png" alt="exception" style="width: 600px;"/>


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
<img src="lecture08/presentation/assets/img/osi2.png" alt="exception" style="width: 600px;"/>  

#HSLIDE
## WebSocket is application layer
<img src="lecture08/presentation/assets/img/osi.png" alt="exception" style="width: 600px;"/>  

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
<img src="lecture08/presentation/assets/img/websocket.png" alt="exception" style="width: 600px;"/>  

#HSLIDE
## WebSocket library
There are a number of **websocket** implementations. We will use jersey **websocket implementation**  
> **@see** build.gradle

#HSLIDE
## Websocket library interface
All communication happen via **Session** interface

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

#HSLIDE
## Agenda
1. Game architecture
1. WebSocket
1. **[Practice]**

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
