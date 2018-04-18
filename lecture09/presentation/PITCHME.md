---
# Java
lecture 9
## Game Server architecture.
## Client-server protocol

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
0. Time model
0. Client-server protocol

---
## Summary
0. **[Game architecture]**
0. Time model
0. Client-server protocol
0. Project task


---
## Game Server
Game Server is a separate application that do in cycle:
0. get's input from multiple clients
0. play game mechanics
0. send game state to clients (replica)
  
**On the next picture green components are already developed**


---
<img src="lecture09/presentation/assets/img/GameServerArchitecture.png" alt="exception" style="width: 800px;"/>


---
## Agenda
0. Game architecture
0. **[Time model]**
0. Client-server protocol

---
## Time model with variable tick time
> @see ru.atom.lecture09.tick.Ticker

In our model tick lasts until all messages from InoutQueue are handled  
So every time tick time is different  
**Advantages/Disadvantages?**

---
## Time model with variable tick time
So game mechanics should take **elapsed** as a parameter and use it internally  
We will discuss details when talk about game mechanics

---
## Agenda
0. Game architecture
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
## Game Server Hackaton
Implement the skeleton of the game server.
Everything on picture except:
- GameMechanics (Next Lectures)  
create stub for it
- ConnectionHandler (Homework)  
create stub for it
- Connect with MatchMaker  


---
**Оставьте обратную связь**
(вам на почту придет анкета)  

**Это важно!**
