---
# Java
lecture 9
## Game architecture

---
## Отметьтесь на портале
https://sphere.mail.ru/

---
### get ready
https://github.com/rybalkinsd/atom
```bash
> git fetch upstream
> git checkout -b lecture09 upstream/lecture09
> cd lecture09
```
Refresh gradle project


---
## Agenda
0. Game architecture
0. Game client
0. Time model
0. Client-server protocol
0. Project task

---
## Architecture overview
<img src="lecture09/presentation/assets/img/Bomberman-arch.png" alt="exception" style="width: 900px;"/>

---
## Game Server
Game Server is a separate application that do in cycle:
0. get's input from multiple clients
0. play game mechanics
0. send game state to clients (replica)
  
**On the next picture green components are already developed**


---
<img src="lecture09/presentation/assets/img/GameServerArchitecture.png" alt="exception" style="width: 900px;"/>

---

### Multithreading
Game server is a multithreaded application  
For each game:  
1. Many threads - get user input
1. One thread - Game mechanics
1. Many threads broadcast replica
  
More details in the next lecture

---
## Agenda
0. Game architecture
0. **[Game client]**
0. Time model
0. Client-server protocol
0. Project task

---
## Game client
Game client is a separate HTML5 project (js+canvas)  
https://github.com/rybalkinsd/atom-bomberman-frontend  
Check it out

---
## Fork game client
<img src="lecture09/presentation/assets/img/client-actions.jpg" alt="exception" style="width: 800px;"/> 


---
### JS components
1. bootstrap - common purpose [link](http://getbootstrap.com/)
1. PreloadJS - assets upload [link](http://www.createjs.com/preloadjs)
1. EaselJS - canvas operating [link](http://www.createjs.com/easeljs)

---
### Canvas
<img src="lecture08/presentation/assets/img/canvas.png" alt="exception" style="width: 600px;"/> 

 
---
### Front instances
- Player
- Bomb
- Fire
- Tile
- Bonus

---
### Front infrastructure
- GameEngine - basic mechanics and render
- InputEngine - input handling 
- ClusterSettings - infrastructure settings

---
## Agenda
0. Game architecture
0. Game client
0. **[Time model]**
0. Client-server protocol
0. Project task
---
## Time model with variable tick time
> @see ru.atom.lecture09.tick.Ticker

In our model tick lasts until all messages from InputQueue are handled  
So every time tick time is different  
**Advantages/Disadvantages?**

---
## Time model with variable tick time
So game mechanics should take **elapsed** as a parameter and use it internally  

---
## Agenda
0. Game architecture
0. Game client
0. Time model
0. **[Client-server protocol]**
0. Project task


---
## Client-server communication
Client and server talk via **websocket**  
We use **JSON** for messages  
### Client sends to server:
- MOVE
- PLANT_BOMB

### Server sends to client:
- REPLICA
- POSSESS

---
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

---
## PLANT_BOMB
client -> server
```json
{
   "topic": "PLANT_BOMB",
   "data": {}
}
```


---
## POSSESS
server -> client  
(returns player pawn id, once on the start of game)
```json
{
   "topic": "POSSESS",
   "data": 123
}
```


---
## REPLICA
```json
{
   "topic": "REPLICA",
   "data":
   {
       "objects":[{"position":{"x":16.0,"y":12.0},"id":16,"type":"Wall"},{"position":{"x":32.0,"y":32.0},"id":213,"velocity":0.05,"maxBombs":1,"bombPower":1,"speedModifier":1.0,"type":"Pawn"},{"position":{"x":32.0,"y":352.0},"id":214,"velocity":0.05,"maxBombs":1,"bombPower":1,"speedModifier":1.0,"type":"Pawn"}],
       "gameOver":false
   }
}
```

---
## Network implementation ideas
> @see ru.atom.lecture09.network



---
## Agenda
0. Game architecture
0. Game client
0. Time model
0. Client-server protocol
0. **[Project task]**

---
## Create a branch for game server
Create the branch from branch lecture09, where you will develop the game server:
```bash
> git checkout -b game-server
```

---
## create a directory for game server
create a directory for game server  
Call it **game_server**  
In this directory you will create game server from scratch


---
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
