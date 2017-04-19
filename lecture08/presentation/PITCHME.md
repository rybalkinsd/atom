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
1. Architecture
1. WebSocket

#HSLIDE
## Agenda
1. Architecture
1. **[WebSocket]**

#HSLIDE
## Agenda
1. Architecture
1. **[WebSocket]**

#HSLIDE
## WebSocket
- client-server
- full-duplex
- application-layer
- over single TCP connection
- protocol (**ws://** **wss://**)

Standartized in 2011  
[https://tools.ietf.org/html/rfc6455](https://tools.ietf.org/html/rfc6455)
Can be used for any extensive client-server communication  
Supported by most modern browsers

#HSLIDE
## WebSocket
### Client-server
client is session initializer

### Full-duplex
exchange data in both directions

#HSLIDE
## WebSocket
<img src="lecture08/presentation/assets/img/osi.png" alt="exception" style="width: 600px;"/>  

#HSLIDE
## WebSocket
### Application layer
handshake like HTTP

### Over single TCP connection
but then no handshakes or headers required. **Only raw data**

#HSLIDE
## WebSocket
<img src="lecture08/presentation/assets/img/websocket.png" alt="exception" style="width: 600px;"/>  

#HSLIDE
## tcpflow


#HSLIDE
## Practice
//TODO

#HSLIDE
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
